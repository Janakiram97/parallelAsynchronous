package thread;

import domain.ProductInfo;
import lombok.Getter;
import service.ProductInfoService;

public class ProductInfoRunnable implements Runnable {
    @Getter
    private ProductInfo productInfo;
    private final String productId;
    public ProductInfoRunnable(String productId) {
        this.productId = productId;
    }



    @Override
    public void run() {
        ProductInfoService productInfoService=new ProductInfoService();
        productInfo=productInfoService.retriveProductInfo(productId);
    }
}
