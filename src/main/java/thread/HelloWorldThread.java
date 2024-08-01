package thread;


import util.LoggerUtil;

import static util.CommonTimeUtil.delay;

public class HelloWorldThread{

    private static String result="";

    private static void hello()
    {
        delay(700);
        result = result.concat("Hello ");
        LoggerUtil.log(result);
    }
    private static void world()
    {
        delay(600);
        result=result.concat("World");
        LoggerUtil.log(result);
    }

    public static void main(String[] args) throws InterruptedException {
        HelloWorldThread t=new HelloWorldThread();

        //Thread thread1=new Thread( () -> hello());
        Thread thread1=new Thread(HelloWorldThread::hello);
        thread1.start();
        thread1.join();
        Thread thread2=new Thread(new Runnable() {
            @Override
            public void run() {
                world();
            }
        });
        thread2.start();
        thread2.join();

        LoggerUtil.log("Result is : "+result);

    }

}
