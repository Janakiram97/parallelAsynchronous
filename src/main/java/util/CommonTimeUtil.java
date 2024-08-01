package util;

import org.springframework.util.StopWatch;

public class CommonTimeUtil {

    public static StopWatch stopWatch = new StopWatch();

    public static void delay(long delayMilliSeconds)
    {
        try {
            Thread.sleep(delayMilliSeconds);
        } catch (Exception e) {
            LoggerUtil.log("Exception is: "+e.getMessage());
        }
    }

    public static String transform(String s)
    {
        CommonTimeUtil.delay(500);
        return s.toUpperCase();
    }

    public static void startTimer()
    {
        stopWatch.start();
    }

    public static void timeTaken()
    {
        stopWatch.stop();
        LoggerUtil.log("Total Time Taken : "+stopWatch.getTotalTimeMillis());
    }

    /*public void stopWatchReset()
    {
        stopWatch.reset();
    }*/

    public static int noOfCores()
    {
        return Runtime.getRuntime().availableProcessors();
    }
   }
