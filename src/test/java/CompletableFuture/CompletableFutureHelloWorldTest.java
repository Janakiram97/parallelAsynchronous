package CompletableFuture;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.HelloWorldService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class CompletableFutureHelloWorldTest {

    HelloWorldService helloWorldService=new HelloWorldService();
    CompletableFutureHelloWorld completableFutureHelloWorld = new CompletableFutureHelloWorld(helloWorldService);
    @Test
    void completableFutureWithThenApply() {

        //when
        CompletableFuture<String> stringCompletableFuture = completableFutureHelloWorld.completableFutureWithThenApply();

        //then
        stringCompletableFuture.thenAccept((result) ->{
            assertEquals("HELLO WORLD!1",result);
        })
                .join();
        //if not used join here in background it will run to stop main thread
        //and execute this we need to use join()
    }

    @Test
    @DisplayName("Hello World with size")
    void helloWorld_withSize() {
        //when
        CompletableFuture<String> helloWorld_withSize = completableFutureHelloWorld.helloWorld_withSize();

        //then
        helloWorld_withSize.thenAccept((result) ->{
            assertEquals("12 - Hello World!",result);
        })
                .join();
    }
}