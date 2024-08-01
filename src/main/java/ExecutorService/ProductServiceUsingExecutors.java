package ExecutorService;


import util.LoggerUtil;
import domain.Product;
import domain.ProductInfo;
import domain.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import service.ProductInfoService;
import service.ReviewService;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static util.CommonTimeUtil.stopWatch;

@AllArgsConstructor
@Data
class ProductServiceUsingExecutors {

    private final ProductInfoService productInfoService;
    private final ReviewService reviewService;

   private static final ExecutorService executorService=Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


    public Product getProduct(String productId) throws ExecutionException, InterruptedException {
        LoggerUtil.log("Available Processors : " + Runtime.getRuntime().availableProcessors());
        stopWatch.start();

        Future<ProductInfo> productInfoFuture = executorService.submit(() -> productInfoService.retriveProductInfo(productId));

        Future<Review> reviewFuture = executorService.submit(() -> reviewService.retriveReview(productId));



        // ProductInfo productInfo = productInfoService.retriveProductInfo(productId);//blocking call
        // Review review = reviewService.retriveReview(productId);//blocking call

        ProductInfo productInfo = productInfoFuture.get();
        Review review = reviewFuture.get();

        stopWatch.stop();
        LoggerUtil.log("Total Time Taken : " + stopWatch.getTotalTimeMillis());
        return new Product(productId, productInfo, review);

    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService1 = new ReviewService();
        ProductServiceUsingExecutors productService = new ProductServiceUsingExecutors(productInfoService, reviewService1);
        String productId = "ABC123";
        Product product = productService.getProduct(productId);
        System.out.println("Product is " + product);
        executorService.shutdown();
    }
}
