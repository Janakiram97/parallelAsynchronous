package service;

import CollectAndReduce.CollectAndReduce;
import Dataset.Dataset;
import domain.checkout.Cart;
import domain.checkout.CartItem;
import domain.checkout.CheckoutResponse;
import domain.checkout.CheckoutStatus;
import util.LoggerUtil;

import java.util.List;
import java.util.stream.Collectors;

import static util.CommonTimeUtil.startTimer;
import static util.CommonTimeUtil.timeTaken;

public class CheckoutService {

    private final PriceValidatorService priceValidatorService;

    public CheckoutService(PriceValidatorService priceValidatorService) {
        this.priceValidatorService = priceValidatorService;
    }

    public CheckoutResponse checkoutParallelStreams(Cart cart)
    {
        startTimer();
        List<CartItem> cartItemsList = cart.getCartItemList()
                .parallelStream()
                .map(cartItem -> {
                    Boolean carItemInvalid = priceValidatorService.isCarItemInvalid(cartItem);
                    cartItem.setExpired(carItemInvalid);
                    return cartItem;
                })
                .filter(CartItem::isExpired)
                .collect(Collectors.toList());

          timeTaken();
        if(cartItemsList.size()>0)
        {
            return new CheckoutResponse(CheckoutStatus.FAILURE,cartItemsList);
        }
        //double value= CollectAndReduce.calculatePrice_Collect(cart);
        double value= CollectAndReduce.calculatePrice_Reduce(cart);
        LoggerUtil.log("Total Price : "+value);
        return new CheckoutResponse(CheckoutStatus.SUCCESS,value);
    }
    public CheckoutResponse checkoutStreams(Cart cart)
    {
        startTimer();
        List<CartItem> cartItemsList = cart.getCartItemList()
                .stream()
                .map(cartItem -> {
                    Boolean carItemInvalid = priceValidatorService.isCarItemInvalid(cartItem);
                    cartItem.setExpired(carItemInvalid);
                    return cartItem;
                })
                .filter(CartItem::isExpired)
                .collect(Collectors.toList());

        timeTaken();
        if(cartItemsList.size()>0)
        {
            return new CheckoutResponse(CheckoutStatus.FAILURE,cartItemsList);
        }

        double value= CollectAndReduce.calculatePrice_Collect(cart);
        LoggerUtil.log("Total Price : "+value);
        return new CheckoutResponse(CheckoutStatus.SUCCESS,value);
    }



    public static void main(String[] args) {

        CheckoutService checkoutService = new CheckoutService(new PriceValidatorService());
        Cart cart = Dataset.createCart(25);
        CheckoutResponse checkoutResponse = checkoutService.checkoutStreams(cart);
        LoggerUtil.log("checkoutResponse : "+checkoutResponse.getCheckoutStatus());

    }
}
