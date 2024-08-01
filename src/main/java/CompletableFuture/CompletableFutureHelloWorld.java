package CompletableFuture;

import service.HelloWorldService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static util.CommonTimeUtil.delay;
import static util.LoggerUtil.log;

public class CompletableFutureHelloWorld {

    private HelloWorldService hws;

    public CompletableFutureHelloWorld(HelloWorldService hws) {
        this.hws = hws;
    }

    public CompletableFuture<String> completableFutureWithThenApply()
    {
        System.out.println("completableFutureWithThenApply");
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> hws.helloWorld())
                .thenApply(String::toUpperCase);
        delay(2000);
        return completableFuture;

    }

    public CompletableFuture<String> helloWorld_withSize()
    {
        return CompletableFuture.supplyAsync(() -> hws.helloWorld())
                .thenApply(result -> result.length() + " - " + result);
    }


   /* public static void main(String[] args) throws ExecutionException, InterruptedException {
        HelloWorldService helloWorldService=new HelloWorldService();
        CompletableFuture.supplyAsync(() ->helloWorldService.hello())
                .thenAccept((result) -> log("Result: "+result))
                .join();
        //Kept delay of 1 sec in helloworld method
        //when supplyAsync called releases main thread and supplyAsync thread runs in background
        //now trying delay with 2sec in main thread
        // delay(2000);
        // we can try with join() method to block main thread or wait for result
        log("This is the main thread");
        *//*CompletableFuture<String> stringCompletableFuture = CompletableFutureHelloWorld.completableFutureWithThenApply();
        String s = stringCompletableFuture.get();
        log(s);*//*
    }*/

}
