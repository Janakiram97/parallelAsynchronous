package ParallelStreamPerformance;

import Dataset.Dataset;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static util.CommonTimeUtil.startTimer;
import static util.CommonTimeUtil.timeTaken;

public class ParallelStreamPerformance {

    public int sum_using_inputStream(int maxNo,boolean isParallel)
    {
         startTimer();
        IntStream intStream = IntStream.rangeClosed(1, maxNo);
        if(isParallel)
            intStream.parallel();

        int sum = intStream.sum();
        System.out.println(sum);
        timeTaken();
        return sum;
    }

    public int sum_using_list(List<Integer> listNumbers,boolean isParallel)
    {
        startTimer();
        Stream<Integer> integerStream = listNumbers.stream();

        if(isParallel)
            integerStream.parallel();

        int sum = integerStream.mapToInt(Integer::intValue).sum();//unboxing -> converting Integer to int
        timeTaken();
        return sum;
    }

    public int sum_using_iterator(int no,boolean isParallel)
    {
        startTimer();

        Stream<Integer> iterate = Stream.iterate(0, i -> i + 1).limit(no+1);

        if(isParallel)
            iterate.parallel();

        Integer integer = iterate.reduce(0, Integer::sum);
        System.out.println(integer);
        timeTaken();
        return integer;
    }

    public static void main(String[] args) {
        ParallelStreamPerformance parallelStreamPerformance=new ParallelStreamPerformance();
        int summedUsingInputStream = parallelStreamPerformance.sum_using_inputStream(1000000, false);
        System.out.println("Summed using inputstream: "+summedUsingInputStream);
        int summedUsingInputStreamParallel = parallelStreamPerformance.sum_using_inputStream(1000000, true);
        System.out.println("Summed using inputstreamParallel: "+summedUsingInputStreamParallel);

        //List
        List<Integer> integers = Dataset.generateList(1000000);
        int sumUsingList = parallelStreamPerformance.sum_using_list(integers, false);
        System.out.println("Summed using list : "+sumUsingList);
        int sumUsingListParallel = parallelStreamPerformance.sum_using_list(integers, true);
        System.out.println("Summed using list in Parallel : "+sumUsingListParallel);

        //iterator

        int sumUsingIterator = parallelStreamPerformance.sum_using_iterator(1000000, false);
        System.out.println("Summed using iterator : "+sumUsingIterator);
        int sumUsingIteratorParallel = parallelStreamPerformance.sum_using_iterator(1000000, true);
        System.out.println("Summed using iterator : "+sumUsingIteratorParallel);
    }
}
