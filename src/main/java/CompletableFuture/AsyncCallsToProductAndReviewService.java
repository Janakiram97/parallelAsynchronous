package CompletableFuture;

import domain.*;
import service.InventoryService;
import service.ProductInfoService;
import service.ReviewService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static util.CommonTimeUtil.startTimer;
import static util.CommonTimeUtil.timeTaken;
import static util.LoggerUtil.log;

public class AsyncCallsToProductAndReviewService {

    private ProductInfoService productInfoService;

    private ReviewService reviewService;

    private InventoryService inventoryService;

    public AsyncCallsToProductAndReviewService(ProductInfoService productInfoService, ReviewService reviewService, InventoryService inventoryService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
        this.inventoryService = inventoryService;
    }

    public Product  asyncCallsToProductAndReviewService()
       {
           startTimer();
           CompletableFuture<ProductInfo> productInfoCompletableFuture = CompletableFuture.supplyAsync(() ->
                   productInfoService.retriveProductInfo("121"));

           CompletableFuture<Review> reviewCompletableFuture = CompletableFuture.supplyAsync(() -> reviewService.retriveReview("121"));

           Product product = productInfoCompletableFuture.thenCombine(reviewCompletableFuture, (productInfo, review) -> new Product("121", productInfo, review))
                   .join();//block the thread

           timeTaken();
           return product;
       }
    public CompletableFuture<Product>  asyncCallsToProductAndReviewService_approach2()
    {
        CompletableFuture<ProductInfo> productInfoCompletableFuture = CompletableFuture.supplyAsync(() ->
                productInfoService.retriveProductInfo("121"));

        CompletableFuture<Review> reviewCompletableFuture = CompletableFuture.supplyAsync(() -> reviewService.retriveReview("121"));

        return productInfoCompletableFuture.thenCombine(reviewCompletableFuture, (productInfo, review) -> new Product("121", productInfo, review));

    }

    public Product asyncCallsToProductAndReviewServiceWithInventory()
    {
        startTimer();
        CompletableFuture<ProductInfo> productInfoCompletableFuture = CompletableFuture.supplyAsync(() ->
                productInfoService.retriveProductInfo("121"))
                .thenApply(productInfo -> {
                  productInfo.setProductionOptionsList(updateProductInfo(productInfo));
                  return productInfo;
                });

        CompletableFuture<Review> reviewCompletableFuture = CompletableFuture.supplyAsync(() -> reviewService.retriveReview("121"));

        Product product = productInfoCompletableFuture.thenCombine(reviewCompletableFuture, (productInfo, review) -> new Product("121", productInfo, review))
                .join();//block the thread

        timeTaken();
        return product;
    }

    private List<ProductOptions> updateProductInfo(ProductInfo productInfo) {
        return productInfo.getProductionOptionsList()
                .stream()
                .map( productOptions -> {
                    Inventory inventory = inventoryService.addInventory(productOptions);
                    productOptions.setInventory(inventory);
                    return productOptions;
                })
                .collect(Collectors.toList());
    }
    public Product asyncCallsToProductAndReviewServiceWithInventory_approach2()
    {
        startTimer();
        CompletableFuture<ProductInfo> productInfoCompletableFuture = CompletableFuture.supplyAsync(() ->
                        productInfoService.retriveProductInfo("121"))
                .thenApply(productInfo -> {
                    productInfo.setProductionOptionsList(updateProductInfo_Approach2(productInfo));
                    return productInfo;
                });

        CompletableFuture<Review> reviewCompletableFuture = CompletableFuture.supplyAsync(() -> reviewService.retriveReview("121"));


        Product product = productInfoCompletableFuture.thenCombine(reviewCompletableFuture, (productInfo, review) -> new Product("121", productInfo, review))
                .join();//block the thread

        timeTaken();
        return product;
    }

    private List<ProductOptions> updateProductInfo_Approach2(ProductInfo productInfo) {

        List<CompletableFuture<ProductOptions>> completableFutureStream = productInfo.getProductionOptionsList()
                .stream()
                .map((productOptions -> {
                    return CompletableFuture.supplyAsync(() ->
                         inventoryService.addInventory(productOptions))
                         .thenApply((inventory) ->{
                        productOptions.setInventory(inventory);
                        return productOptions;
                     });
                }))
                .collect(Collectors.toList());

       return completableFutureStream.stream()
                .map((CompletableFuture::join))
                .collect(Collectors.toList());

    }
}
