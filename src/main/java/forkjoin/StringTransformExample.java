package forkjoin;

import Dataset.Dataset;
import util.LoggerUtil;

import java.util.ArrayList;
import java.util.List;

import static util.CommonTimeUtil.*;

public class StringTransformExample {

    public static void main(String[] args) {
        stopWatch.start();
        List<String> resultList = new ArrayList<>();
        List<String> namesList = Dataset.namesList();
        LoggerUtil.log("Names : "+namesList);

        namesList.stream().forEach( (name) -> {
            String nameLengthTransform = addNameLengthTransform(name);
            resultList.add(nameLengthTransform);
        });
        stopWatch.stop();
        LoggerUtil.log("Time Taken : "+stopWatch.getTotalTimeSeconds());
        LoggerUtil.log("Final Result : "+resultList);
        resultList.stream().forEach((name) -> LoggerUtil.log("Transformed Name : "+name));

    }

    public static String addNameLengthTransform(String name)
    {
        delay(500);
        return name.length() +" - " +name;
    }

}
