package CompletableFuture;

import domain.Inventory;
import domain.Product;
import domain.Review;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.InventoryService;
import service.ProductInfoService;
import service.ReviewService;
import util.LoggerUtil;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static util.CommonTimeUtil.startTimer;
import static util.CommonTimeUtil.timeTaken;

class AsyncCallsToProductAndReviewServiceTest {

    ProductInfoService productInfoService=new ProductInfoService();
    ReviewService reviewService=new ReviewService();
    InventoryService inventoryService=new InventoryService();
    AsyncCallsToProductAndReviewService asyncCallsToProductAndReviewService=new AsyncCallsToProductAndReviewService(productInfoService,reviewService,inventoryService);
    @Test
    @DisplayName("Test asyncCallsToProductAndReviewService_client side")
    void asyncCallsToProductAndReviewService() {

        Product product = asyncCallsToProductAndReviewService.asyncCallsToProductAndReviewService();
        System.out.println(product);
        assertNotNull(product);
        assertNotNull(product.getReview());
        assertEquals("121",product.getProductId());
    }

    @Test
    @DisplayName("Test asyncCallsToProductAndReviewService_server side")
    void asyncCallsToProductAndReviewService_serverSide() {
        startTimer();
        CompletableFuture<Product> product = asyncCallsToProductAndReviewService.asyncCallsToProductAndReviewService_approach2();

        product.thenAccept((product1 -> {
            assertNotNull(product1);
            assertNotNull(product1.getReview());
        }));
        timeTaken();
    }

    @Test
    @DisplayName("Test asyncCallsToProductAndReviewService with inventory")
    void asyncCallsToProductAndReviewServiceWithInventory() {
        Product product = asyncCallsToProductAndReviewService.asyncCallsToProductAndReviewServiceWithInventory();

        System.out.println(product);
        assertNotNull(product);
        assertNotNull(product.getReview());

        product.getProductInfo().getProductionOptionsList()
                        .forEach((productOptions -> {
                            assertNotNull(productOptions.getInventory());
                        }));
    }
    @Test
    @DisplayName("Test asyncCallsToProductAndReviewService with inventory approach2")
    void asyncCallsToProductAndReviewServiceWithInventory_approach2() {
        Product product = asyncCallsToProductAndReviewService.asyncCallsToProductAndReviewServiceWithInventory_approach2();

        System.out.println(product);
        assertNotNull(product);
        assertNotNull(product.getReview());

        product.getProductInfo().getProductionOptionsList()
                .forEach((productOptions -> {
                    assertNotNull(productOptions.getInventory());
                }));
    }
}