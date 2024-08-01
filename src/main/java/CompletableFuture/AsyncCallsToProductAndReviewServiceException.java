package CompletableFuture;

import domain.*;
import lombok.AllArgsConstructor;
import service.InventoryService;
import service.ProductInfoService;
import service.ReviewService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static util.CommonTimeUtil.startTimer;
import static util.CommonTimeUtil.timeTaken;
import static util.LoggerUtil.log;
@AllArgsConstructor
public class AsyncCallsToProductAndReviewServiceException {

    private ProductInfoService productInfoService;
    private InventoryService inventoryService;
    private ReviewService reviewService;

    public Product asyncCallsToProductAndReviewServiceWithInventory_ExceptionAtReview()
    {
        startTimer();
        CompletableFuture<ProductInfo> productInfoCompletableFuture = CompletableFuture.supplyAsync(() ->
                        productInfoService.retriveProductInfo("121"))
                .thenApply(productInfo -> {
                    productInfo.setProductionOptionsList(updateProductInfo_Approach2(productInfo));
                    return productInfo;
                });

        CompletableFuture<Review> reviewCompletableFuture = CompletableFuture.supplyAsync(() -> reviewService.retriveReview("121"))
                .exceptionally((e) ->{
                    log("Exception in review service: "+e.getMessage());
                    return Review.builder()
                            .noOfReviews(0)
                            .overAllRating(0.0)
                            .build();
                });

        Product product = productInfoCompletableFuture.thenCombine(reviewCompletableFuture, (productInfo, review) -> new Product("121", productInfo, review))
                .join();//block the thread

        timeTaken();
        return product;
    }
    public Product asyncCallsToProductAndReviewServiceWithInventory_ExceptionAtProductInfo()
    {
        startTimer();
        CompletableFuture<ProductInfo> productInfoCompletableFuture = CompletableFuture.supplyAsync(() ->
                        productInfoService.retriveProductInfo("121"))
                .thenApply(productInfo -> {
                    productInfo.setProductionOptionsList(updateProductInfo_Approach2(productInfo));
                    return productInfo;
                });

        CompletableFuture<Review> reviewCompletableFuture = CompletableFuture.supplyAsync(()
                -> reviewService.retriveReview("121"));


        Product product = productInfoCompletableFuture.
                thenCombine(reviewCompletableFuture, (productInfo, review) -> new Product("121", productInfo, review))
                .whenComplete((product1,e) ->{
                    log("Exception in product info service: "+e.getMessage());
                })
                .join();//block the thread

        timeTaken();
        return product;
    }
    public Product asyncCallsToProductAndReviewServiceWithInventory_ExceptionAtInventory()
    {
        startTimer();
        CompletableFuture<ProductInfo> productInfoCompletableFuture = CompletableFuture.supplyAsync(() ->
                        productInfoService.retriveProductInfo("121"))
                .thenApply(productInfo -> {
                    productInfo.setProductionOptionsList(updateProductInfo_exceptionInventory(productInfo));
                    return productInfo;
                });

        CompletableFuture<Review> reviewCompletableFuture = CompletableFuture.supplyAsync(()
                -> reviewService.retriveReview("121"));


        Product product = productInfoCompletableFuture.
                thenCombine(reviewCompletableFuture, (productInfo, review) -> new Product("121", productInfo, review))
                .whenComplete((product1,e) ->{
                    log("Exception in product info service: "+e.getMessage());
                })
                .join();//block the thread

        timeTaken();
        return product;
    }
    private List<ProductOptions> updateProductInfo_exceptionInventory(ProductInfo productInfo) {

        List<CompletableFuture<ProductOptions>> completableFutureStream = productInfo.getProductionOptionsList()
                .stream()
                .map((productOptions -> {
                    return CompletableFuture.supplyAsync(() ->
                                    inventoryService.addInventory(productOptions))
                            .exceptionally((e) -> {
                                log("Exception occured at Inventory Service" + e.getMessage());
                                return Inventory.builder()
                                        .count(1)
                                        .build();
                            })
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
