package ParallelStreams;

import Dataset.Dataset;
import util.LoggerUtil;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static util.CommonTimeUtil.*;

class ParallelStreamsEx {

    public List<String> transformNamesWithStreams(List<String> names) {
        return names.stream()
                .map(this::addNameLengthTransform)
                .collect(Collectors.toList());
    }

    public List<String> transformNamesWithParallelStreams(List<String> names) {
        return names.parallelStream()
                .map(this::addNameLengthTransform)
                .collect(Collectors.toList());
    }
    public List<String> transformNamesWithFlag(List<String> names,boolean isParallel) {

        Stream<String> nameList = names.stream();
        if(isParallel) {
            nameList.parallel();
        }
        return nameList
                .map(this::addNameLengthTransform)
                .collect(Collectors.toList());
    }
    public static void main(String[] args) {

        List<String> namesList = Dataset.namesList();
        startTimer();
        ParallelStreamsEx parallelStreamsEx = new ParallelStreamsEx();
        List<String> names = parallelStreamsEx.transformNamesWithParallelStreams(namesList);
        timeTaken();
        LoggerUtil.log("Names : " + names);
    }

    public String addNameLengthTransform(String name) {
        delay(500);
        return name.length() + " -" + name;
    }
}
