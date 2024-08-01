package thread;


import util.LoggerUtil;
import domain.Product;
import domain.ProductInfo;
import domain.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import service.ProductInfoService;
import service.ReviewService;

import static util.CommonTimeUtil.stopWatch;

@AllArgsConstructor
@Data
class ProductServiceWithThread {

    private final ProductInfoService productInfoService;
    private final ReviewService reviewService;

    public  Product getProduct(String productId) throws InterruptedException {
        stopWatch.start();

        ProductInfoRunnable productInfoRunnable=new ProductInfoRunnable(productId);
        Runnable reviewRunnable=new ReviewRunnable(productId);
       /* ProductInfo productInfo = productInfoService.retriveProductInfo(productId);//blocking call
        Review review = reviewService.retriveReview(productId);//blocking call*/
        Thread productInfoThread=new Thread(productInfoRunnable);
        productInfoThread.start();
        LoggerUtil.log("Product Info thread Name :"+ productInfoThread.getName());
        Thread reviewThread=new Thread(reviewRunnable);
        reviewThread.start();
        LoggerUtil.log("Review thread Name :"+ reviewThread.getName());
        productInfoThread.join();
        reviewThread.join();

        ProductInfo productInfo = productInfoRunnable.getProductInfo();
        //Review review = reviewRunnable.getReview();
        Review review = ((ReviewRunnable) reviewRunnable).getReview();
        stopWatch.stop();
        LoggerUtil.log("Total Time Taken : "+ stopWatch.getTotalTimeMillis());
        return new Product(productId,productInfo,review);

    }

    public static void main(String[] args) throws InterruptedException {

        ProductInfoService productInfoService=new ProductInfoService();
        ReviewService reviewService1=new ReviewService();
        ProductServiceWithThread productService=new ProductServiceWithThread(productInfoService,reviewService1);
        String productId="ABC123";
        LoggerUtil.log("Main thread Name :"+ Thread.currentThread().getName());
        LoggerUtil.log("Main Thread Execution Started");
        Product product=productService.getProduct(productId);
        System.out.println("Product is "+product);
    }
}
