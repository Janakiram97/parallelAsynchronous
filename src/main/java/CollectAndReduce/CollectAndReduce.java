package CollectAndReduce;

import domain.checkout.Cart;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CollectAndReduce {

    public static double calculatePrice_Collect(Cart cart)
    {
        Double doubleValueCollect = cart.getCartItemList().parallelStream()
                .map(cartItem -> cartItem.getQuantity() * cartItem.getRate())
                .collect(Collectors.summingDouble(value -> value));
        System.out.println(doubleValueCollect);
        return doubleValueCollect;
    }

    public static double calculatePrice_Reduce(Cart cart)
    {
        Double  reduceDouble = cart.getCartItemList()
                .parallelStream()
                .map(cartItem -> cartItem.getQuantity() * cartItem.getRate())
                .reduce(0.0, (a, b) -> a + b);
        return reduceDouble;
    }
}
