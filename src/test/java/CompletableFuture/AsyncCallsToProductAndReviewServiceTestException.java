package CompletableFuture;

import domain.Product;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.InventoryService;
import service.ProductInfoService;
import service.ReviewService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AsyncCallsToProductAndReviewServiceTestException {
    @Mock
    private ProductInfoService productInfoService;
    @Mock
    private ReviewService reviewService;
    @Mock
    private InventoryService inventoryService;
    @InjectMocks
    AsyncCallsToProductAndReviewServiceException asyncCallsToProductAndReviewService;

    @Test
    @DisplayName("Test for exception at review service")
    void asyncCallsToProductAndReviewServiceWithInventory_exceptionAtReviewService() {

        when(productInfoService.retriveProductInfo(any())).thenCallRealMethod();
        when(reviewService.retriveReview(any())).thenThrow(new RuntimeException("Exception in review service"));
        when(inventoryService.addInventory(any())).thenCallRealMethod();

        Product product = asyncCallsToProductAndReviewService.asyncCallsToProductAndReviewServiceWithInventory_ExceptionAtReview();

        assertNotNull(product);
        assertNotNull(product.getReview());
        assertEquals(0,product.getReview().getNoOfReviews());
    }
    @Test
    @DisplayName("Test for exception at product service")
    void asyncCallsToProductAndReviewServiceWithInventory_exceptionAtProductInfoService() {

        when(productInfoService.retriveProductInfo(any())).thenThrow(new RuntimeException("Exception in product info service"));
        when(reviewService.retriveReview(any())).thenCallRealMethod();

        assertThrows(RuntimeException.class,
                ()->asyncCallsToProductAndReviewService.asyncCallsToProductAndReviewServiceWithInventory_ExceptionAtProductInfo());

    }
    @Test
    @DisplayName("Test for exception at Inventory service")
    void asyncCallsToProductAndReviewServiceWithInventory_exceptionAtInventoryService() {

        when(productInfoService.retriveProductInfo(any())).thenCallRealMethod();
        when(reviewService.retriveReview(any())).thenCallRealMethod();
        when(inventoryService.addInventory(any())).thenThrow(new RuntimeException("Exception in inventory service"));

        assertThrows(RuntimeException.class,
                ()->asyncCallsToProductAndReviewService.asyncCallsToProductAndReviewServiceWithInventory_ExceptionAtInventory());

    }
}