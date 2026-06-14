package com.kvfuncref;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FunctionTest {
    @Test
    @SuppressWarnings("unchecked")
    void runReturnsKeyVaultSecretEnvironmentValues() {
        Function function = new Function();
        HttpRequestMessage<Optional<String>> request = mock(HttpRequestMessage.class);
        HttpResponseMessage.Builder responseBuilder = mock(HttpResponseMessage.Builder.class);
        HttpResponseMessage response = mock(HttpResponseMessage.class);
        ExecutionContext context = mock(ExecutionContext.class);

        when(context.getLogger()).thenReturn(Logger.getLogger(FunctionTest.class.getName()));
        when(request.createResponseBuilder(HttpStatus.OK)).thenReturn(responseBuilder);
        when(responseBuilder.body(expectedBody())).thenReturn(responseBuilder);
        when(responseBuilder.build()).thenReturn(response);

        HttpResponseMessage actualResponse = function.run(request, context);

        assertSame(response, actualResponse);
        verify(request).createResponseBuilder(HttpStatus.OK);
        verify(responseBuilder).body(expectedBody());
        verify(responseBuilder).build();
    }

    private static String expectedBody() {
        return "SECRET1=" + System.getenv("KEYVAULT_SECRET1")
                + " / SECRET2=" + System.getenv("KEYVAULT_SECRET2");
    }
}