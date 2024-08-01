package service;

import Dataset.Dataset;
import domain.checkout.Cart;
import domain.checkout.CartItem;
import domain.checkout.CheckoutResponse;
import domain.checkout.CheckoutStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutServiceTest {


    private final PriceValidatorService priceValidatorService = new PriceValidatorService();
    private final CheckoutService checkoutService = new CheckoutService(priceValidatorService);

    @Test
    void checkout() {
        Cart cart=new Cart(12,List.of(new CartItem(7,"apple",23.0,2,false)));
        CheckoutResponse checkoutResponse = checkoutService.checkoutParallelStreams(cart);
        System.out.println(checkoutResponse.getCheckoutStatus());
        assertEquals(CheckoutStatus.FAILURE,checkoutResponse.getCheckoutStatus());

    }
    @Test
    void checkout_6_items_ParallelStreams()
    {
        Cart cart = Dataset.createCart(6);
        CheckoutResponse checkoutResponse = checkoutService.checkoutParallelStreams(cart);
        assertEquals(CheckoutStatus.SUCCESS,checkoutResponse.getCheckoutStatus());

    }
    @Test
    void checkout_16_items_ParallelStreams()
    {
        System.out.println(Runtime.getRuntime().availableProcessors());
        Cart cart = Dataset.createCart(16);
        CheckoutResponse checkoutResponse = checkoutService.checkoutParallelStreams(cart);
        assertEquals(CheckoutStatus.FAILURE,checkoutResponse.getCheckoutStatus());
    }
    @Test
    void checkout_25_items_ParallelStreams()
    {
        System.out.println(Runtime.getRuntime().availableProcessors());
        Cart cart = Dataset.createCart(25);
        CheckoutResponse checkoutResponse = checkoutService.checkoutParallelStreams(cart);
        assertEquals(CheckoutStatus.FAILURE,checkoutResponse.getCheckoutStatus());
    }
    @Test
    void checkout_6_items_Streams()
    {
        Cart cart = Dataset.createCart(6);
        CheckoutResponse checkoutResponse = checkoutService.checkoutStreams(cart);
        assertEquals(CheckoutStatus.SUCCESS,checkoutResponse.getCheckoutStatus());
    }

    @Test
    void checkout_modify_ParallelStreams()
    {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism","50");
        System.out.println(Runtime.getRuntime().availableProcessors());
        Cart cart = Dataset.createCart(100);
        CheckoutResponse checkoutResponse = checkoutService.checkoutParallelStreams(cart);
        assertEquals(CheckoutStatus.FAILURE,checkoutResponse.getCheckoutStatus());
    }
}