package service;


import util.LoggerUtil;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static util.CommonTimeUtil.delay;

public class HelloWorldService {

    public String helloWorld()
    {
        delay(1000);
        LoggerUtil.log("Hello World");
        return "Hello World!";
    }


    public String hello()
    {
        delay(1000);
        LoggerUtil.log("Hello");
        return "Hello ";
    }

    public String world()
    {
        delay(1000);
        LoggerUtil.log("World");
        return "World";
    }

    public CompletableFuture<String> worldFuture(String input)
    {
        return CompletableFuture.supplyAsync(() -> {
            delay(1000);
            LoggerUtil.log("World");
            return input + " World";
        });

    }
}
