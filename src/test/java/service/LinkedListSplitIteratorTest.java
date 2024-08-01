package service;

import Dataset.Dataset;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListSplitIteratorTest {

    LinkedListSplitIterator linkedListSplitIterator=new LinkedListSplitIterator();
    @RepeatedTest(5)
    void multiplyNumber_streams() {
        //given
        int maxNo=1000000;
        LinkedList<Integer> linkedList = Dataset.generateLinkedList(maxNo);

        //when
        List<Integer> multipliedNumberList = linkedListSplitIterator.multiplyNumber(linkedList, 3, false);

        //then
        assertEquals(maxNo,multipliedNumberList.size());
    }
    @RepeatedTest(5)
    void multiplyNumber_parallel() {
        //given
        int maxNo=1000000;
        LinkedList<Integer> linkedList = Dataset.generateLinkedList(maxNo);

        //when
        List<Integer> multipliedNumberList = linkedListSplitIterator.multiplyNumber(linkedList, 3, true);

        //then
        assertEquals(maxNo,multipliedNumberList.size());
    }
}