package service;

import Dataset.Dataset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArrayListSplitIteratorTest {

    ArrayListSplitIterator arrayListSplitIterator=new ArrayListSplitIterator();
    @RepeatedTest(5)
    @DisplayName("Test multiplyWithValue_Stream")
    void multiplyWithValue_Stream() {
       //given
        int maxSize=1000000;
        ArrayList<Integer> arrayList = Dataset.generateArrayList(maxSize);

        //when
        List<Integer> multiplyWithValueList = arrayListSplitIterator.multiplyWithValue(arrayList, 2, false);

        //then
        assertEquals(maxSize,multiplyWithValueList.size());
    }
    @RepeatedTest(5)
    @DisplayName("Test multiplyWithValue_parallel")
    void multiplyWithValue_parallel() {
        //given
        int maxSize=1000000;
        ArrayList<Integer> arrayList = Dataset.generateArrayList(maxSize);

        //when
        List<Integer> multiplyWithValueList = arrayListSplitIterator.multiplyWithValue(arrayList, 2, true);

        //then
        assertEquals(maxSize,multiplyWithValueList.size());
    }
}