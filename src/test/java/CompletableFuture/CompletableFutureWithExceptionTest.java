package CompletableFuture;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.HelloWorldService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompletableFutureWithExceptionTest {

    @Mock
    HelloWorldService hws;

    @InjectMocks
    CompletableFutureWithException completableFutureWithException;

    @Test
    @DisplayName("Then Combine 3 async methods with handle Hello() Exception")
    void thenCombine_3_asyncMethods_helloMethodException() {

        when(hws.hello()).thenThrow(new RuntimeException("Exception in hello method"));
        when(hws.world()).thenCallRealMethod();

        String thenCombine3AsyncMethods = completableFutureWithException.thenCombine_3_asyncMethodsWithHandleException();

        assertEquals("WORLD HI COMPLETABLEFUTURE!", thenCombine3AsyncMethods);
    }

    @Test
    @DisplayName("Then Combine 3 async methods with handle World() Exception")
    void thenCombine_3_asyncMethods_worldMethodException() {

        when(hws.hello()).thenCallRealMethod();
        when(hws.world()).thenThrow(new RuntimeException("Exception in world method"));

        String thenCombine3AsyncMethods = completableFutureWithException.thenCombine_3_asyncMethodsWithHandleException();

        assertEquals(" HI COMPLETABLEFUTURE!", thenCombine3AsyncMethods);
    }

    @Test
    @DisplayName("Handle Exception when exception is not there")
    void thenCombine_3_asyncMethods_whenBothCorrect() {

        when(hws.hello()).thenCallRealMethod();
        when(hws.world()).thenCallRealMethod();

        String thenCombine3AsyncMethods = completableFutureWithException.thenCombine_3_asyncMethodsWithHandleException();

        assertEquals("HELLO WORLD HI COMPLETABLEFUTURE!", thenCombine3AsyncMethods);
    }

    @Test
    @DisplayName("Using exceptionally method when exception is not there")
    void thenCombine_3_asyncMethods_whenBothCorrectExpectionallyScenario() {

        when(hws.hello()).thenCallRealMethod();
        when(hws.world()).thenCallRealMethod();

        String thenCombine3AsyncMethods = completableFutureWithException.thenCombine_3_asyncMethodsWithExceptionallyMethod();

        assertEquals("HELLO WORLD HI COMPLETABLEFUTURE!", thenCombine3AsyncMethods);
    }

    @Test
    @DisplayName("Using exceptionally method when exception is present in both methods")
    void thenCombine_3_asyncMethods_whenBothInCorrectExpectionallyScenario() {

        when(hws.hello()).thenThrow(new RuntimeException("Exception in hello method"));
        when(hws.world()).thenThrow(new RuntimeException("Exception in world method"));


        String thenCombine3AsyncMethods = completableFutureWithException.thenCombine_3_asyncMethodsWithExceptionallyMethod();

        assertEquals(" HI COMPLETABLEFUTURE!", thenCombine3AsyncMethods);
    }

}