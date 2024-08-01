package com.example.parallelasynchronous;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class LowerCase {

    public List<String> transformToLower(List<String> nameList, boolean isParallel) {

        Stream<String> namesList = nameList.stream();

        if (isParallel)

            namesList.parallel();

        return namesList.map(String::toLowerCase).collect(Collectors.toList());

    }

    public static void main(String[] args) {

        List<String> namesList = List.of("Bob", "Jamie", "Jill", "Rick");

        List<String> list = namesList.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
        System.out.println(list);
    }

}