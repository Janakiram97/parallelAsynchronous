package service;


import util.LoggerUtil;
import domain.Product;
import domain.ProductInfo;
import domain.Review;
import lombok.AllArgsConstructor;
import lombok.Data;

import static util.CommonTimeUtil.stopWatch;

@AllArgsConstructor
@Data
class ProductService {

    private final ProductInfoService productInfoService;
    private final ReviewService reviewService;

    public  Product getProduct(String productId)
    {
        stopWatch.start();
        ProductInfo productInfo = productInfoService.retriveProductInfo(productId);//blocking call
        Review review = reviewService.retriveReview(productId);//blocking call
        stopWatch.stop();
        LoggerUtil.log("Total Time Taken : "+ stopWatch.getTotalTimeMillis());
        return new Product(productId,productInfo,review);

    }

    public static void main(String[] args) {

        ProductInfoService productInfoService=new ProductInfoService();
        ReviewService reviewService1=new ReviewService();
        ProductService productService=new ProductService(productInfoService,reviewService1);
       String productId="ABC123";
        Product product=productService.getProduct(productId);
        System.out.println("Product is "+product);
    }
}
