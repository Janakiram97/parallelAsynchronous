package ParallelStreamPerformance;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParallelStreamPerformanceTest {

    ParallelStreamPerformance parallelStreamPerformance=new ParallelStreamPerformance();
    @Test
    void sum_using_inputStream() {
        int summedUsingInputStream = parallelStreamPerformance.sum_using_inputStream(10, false);
        System.out.println(summedUsingInputStream);
        assertEquals(55,summedUsingInputStream);
    }

    @Test
    void sum_using_list() {
    }

    @Test
    void sum_using_iterator() {
    }
}