package service;

import domain.ProductInfo;
import domain.ProductOptions;

import java.util.List;

import static util.CommonTimeUtil.delay;

public class ProductInfoService {

    public ProductInfo retriveProductInfo(String productId)
    {
        delay(1000);
        List<ProductOptions> productOptions=List.of(new ProductOptions(1,"64GB","Black",699.0)
                ,new ProductOptions(2,"128GB","Blue",846.0)
                ,new ProductOptions(3,"256GB","Blue",1255.0)
                ,new ProductOptions(4,"512GB","Blue",1655.0));
        return ProductInfo.builder()
                .productId(productId)
                .productionOptionsList(productOptions)
                .build();
    }
}
