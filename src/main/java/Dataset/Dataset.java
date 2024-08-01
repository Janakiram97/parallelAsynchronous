package Dataset;

import domain.checkout.Cart;
import domain.checkout.CartItem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Dataset {

    public static List<String> namesList()
    {
        return List.of("adam", "jim", "jenny","Bob");
    }

    public static Cart createCart(int noOfItems)
    {
        Cart cart=new Cart();
        List<CartItem> cartItemList=new ArrayList<>();
        IntStream.rangeClosed(1,noOfItems)
                .forEach((index) ->{
                    String itemName="cartItem -"+index;
                    CartItem cartItem=new CartItem(index,itemName,generateRandomPrice(),index,false);
                    cartItemList.add(cartItem);
                });
        cart.setCartId(121);
        cart.setCartItemList(cartItemList);
        return cart;
    }

    private static double generateRandomPrice() {

        return Math.random()*100;
    }

    public static ArrayList<Integer> generateArrayList(int maxNo)
    {
        ArrayList<Integer> arrayList=new ArrayList<>();
       IntStream.rangeClosed(1, maxNo)
                .boxed()
                .forEach(arrayList::add);
       return arrayList;
    }

    public static LinkedList<Integer> generateLinkedList(int maxNo)
    {
        LinkedList<Integer> linkedList=new LinkedList<>();
        IntStream.rangeClosed(1,maxNo)
                .boxed()
                //.forEach(number -> linkedList.add(number));
                .forEach(linkedList::add);
        return linkedList;
    }
    public static List<Integer> generateList(int maxNo)
    {

       return IntStream.rangeClosed(1, maxNo)
                .boxed()
                .collect(Collectors.toList());

    }
}
