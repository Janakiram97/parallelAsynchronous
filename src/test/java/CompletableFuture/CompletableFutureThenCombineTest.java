package CompletableFuture;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.LoggerUtil;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static util.CommonTimeUtil.startTimer;
import static util.CommonTimeUtil.timeTaken;

class CompletableFutureThenCombineTest {

    CompletableFutureThenCombine completableFutureThenCombine=new CompletableFutureThenCombine();
    @Test
    @DisplayName("CompletableFutureThenCombineTest")
    void thenCombineMethod() {

        String s = completableFutureThenCombine.thenCombine_2_asyncCalls();

        assertEquals("HELLO WORLD",s);

    }

    @Test
    @DisplayName("CompletableFutureThenCombine3 Asyncs Test")
    void thenCombine_3_asyncMethod() {

        String s = completableFutureThenCombine.thenCombine_3_asyncMethods();
        System.out.println(s);

        assertEquals("HELLO WORLD HI COMPLETABLEFUTURE!",s);

    }

    @Test
    @DisplayName("CompletableFutureThenCompose")
    void thenComposeMethod() {
        startTimer();
        CompletableFuture<String> stringCompletableFuture = completableFutureThenCombine.thenComposeMethod();

       stringCompletableFuture.thenAccept((result) ->{
           assertEquals("HELLO WORLD",result);
       });
        LoggerUtil.log("CompletableFutureThenCompose");
        timeTaken();
    }
}