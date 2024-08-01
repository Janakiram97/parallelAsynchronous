package CompletableFuture;

import service.HelloWorldService;

import java.util.concurrent.CompletableFuture;

import static util.CommonTimeUtil.*;

public class CompletableFutureThenCombine {

    HelloWorldService helloWorldService = new HelloWorldService();
    public String thenCombine_2_asyncCalls()
    {
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> helloWorldService.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> helloWorldService.world());

        String combinedString = hello.thenCombine(world, (h, w) -> h + w)
                .thenApply(String::toUpperCase)
                .join();
        System.out.println(combinedString);

        timeTaken();
        return combinedString;
    }

    public String thenCombine_3_asyncMethods()
    {
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> helloWorldService.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> helloWorldService.world());
        CompletableFuture<String> thirdAsync = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi CompletableFuture!";
        });

        String combinedString = hello
                .thenCombine(world, (h, w) -> h + w)
                .thenCombine(thirdAsync, (previous,current) -> previous+current)
                .thenApply(String::toUpperCase)
                .join();
        System.out.println(combinedString);

        timeTaken();
        return combinedString;
    }

   public CompletableFuture<String> thenComposeMethod()
    {
        return CompletableFuture.supplyAsync(() ->helloWorldService.hello())
                .thenCompose( previous -> helloWorldService.worldFuture(previous))
                .thenApply(String::toUpperCase);
    }
}
