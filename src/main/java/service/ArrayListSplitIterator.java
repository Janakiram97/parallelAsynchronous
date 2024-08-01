package service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static util.CommonTimeUtil.startTimer;
import static util.CommonTimeUtil.timeTaken;

public class ArrayListSplitIterator {

    public List<Integer> multiplyWithValue(ArrayList<Integer> numbersList, int multiplyValue, boolean isParallel)
    {
        startTimer();
        Stream<Integer> integerStream = numbersList.stream();
        if(isParallel)
            integerStream.parallel();


        List<Integer> integerList= integerStream.map(number -> number * multiplyValue)
                .collect(Collectors.toList());

        timeTaken();
        return integerList;
    }

}
