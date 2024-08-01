package service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static util.CommonTimeUtil.startTimer;
import static util.CommonTimeUtil.timeTaken;

public class LinkedListSplitIterator {

   public List<Integer> multiplyNumber(LinkedList<Integer> linkedList, int multiplyValue, boolean isParallel)
    {
        startTimer();
        Stream<Integer> integerStream = linkedList.stream();

        if(isParallel)
            integerStream.parallel();

        List<Integer> integerList = integerStream.map(number -> number * 2)
                .collect(Collectors.toList());

        timeTaken();
        return integerList;
    }

}
