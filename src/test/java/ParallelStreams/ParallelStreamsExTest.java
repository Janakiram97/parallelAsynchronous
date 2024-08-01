package ParallelStreams;

import Dataset.Dataset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParallelStreamsExTest {

    private ParallelStreamsEx parallelStreamsEx;

    @BeforeEach
    void setUp() {
        parallelStreamsEx = new ParallelStreamsEx();
    }

    @Test
    void testTransformNamesWithParallelStreams() {
        System.out.println("testTransformNamesWithParallelStreams");
        // Given
        List<String> namesList = Dataset.namesList();

        // When
        List<String> resultList = parallelStreamsEx.transformNamesWithParallelStreams(namesList);

        // Then
        assertEquals(namesList.size(), resultList.size());

        resultList.stream()
                .forEach(name -> {
                    assertTrue(name.contains("-"));
                });
    }
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void testTransformNamesWithEitherParallelOrStream(boolean isParallel) {
        System.out.println("testTransformNamesWithEitherParallelOrStream");
        // Given
        List<String> namesList = Dataset.namesList();

        // When
        List<String> resultList = parallelStreamsEx.transformNamesWithFlag(namesList, isParallel);

        // Then
        assertEquals(namesList.size(), resultList.size());

        resultList.stream()
                .forEach(name -> {
                    assertTrue(name.contains("-"));
                });
    }
}