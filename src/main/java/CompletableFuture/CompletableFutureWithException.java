package CompletableFuture;

import service.HelloWorldService;

import java.util.concurrent.CompletableFuture;

import static util.CommonTimeUtil.*;
import static util.LoggerUtil.log;

public class CompletableFutureWithException {

    private HelloWorldService hws;

    public CompletableFutureWithException(HelloWorldService hws) {
        this.hws = hws;
    }

    public String thenCombine_3_asyncMethodsWithHandleException()
    {
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> hws.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> hws.world());
        CompletableFuture<String> thirdAsync = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi CompletableFuture!";
        });

        String combinedString = hello
                .handle((res,e) ->{
                    if(e!=null){
                    log("Exception is "+e.getMessage());
                    log("result is : "+res);
                    return "";
                    }
                    return res;
                })
                .thenCombine(world, (h, w) -> h + w)// "world"
                .handle((res,e) ->{
                    if(e!=null){
                        log("Exception is "+e.getMessage());
                        log("result is : "+res);
                        return "";
                    }
                    return res;

                })
                .thenCombine(thirdAsync, (previous,current) -> previous+current)//" world Hi CompletableFuture!"
                .thenApply(String::toUpperCase)//"WORLD HI COMPLETABLEFUTURE!"
                .join();
        log(combinedString);
        timeTaken();
        return combinedString;
    }

    public String thenCombine_3_asyncMethodsWithExceptionallyMethod()
    {
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> hws.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> hws.world());
        CompletableFuture<String> thirdAsync = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi CompletableFuture!";
        });

        String combinedString = hello
                .exceptionally(e->{
                    log("Exception is "+e.getMessage());
                    return "";

                })
                .thenCombine(world, (h, w) -> h + w)// "world"
                .exceptionally(e->{
                    log("Exception is "+e.getMessage());
                    return "";

                })
                .thenCombine(thirdAsync, (previous,current) -> previous+current)//" world Hi CompletableFuture!"
                .thenApply(String::toUpperCase)//"WORLD HI COMPLETABLEFUTURE!"
                .join();
        log(combinedString);
        timeTaken();
        return combinedString;
    }
}
