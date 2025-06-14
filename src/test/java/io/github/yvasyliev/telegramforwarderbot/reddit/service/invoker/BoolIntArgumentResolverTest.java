package io.github.yvasyliev.telegramforwarderbot.reddit.service.invoker;

import io.github.yvasyliev.telegramforwarderbot.reddit.annotation.BoolInt;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.invoker.HttpRequestValues;
import org.springframework.web.service.invoker.HttpServiceArgumentResolver;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BoolIntArgumentResolverTest {
    private static final HttpServiceArgumentResolver RESOLVER = new BoolIntArgumentResolver();
    @Mock private MethodParameter parameter;
    @Mock private HttpRequestValues.Builder requestValues;

    @Test
    void shouldNotAddRequestParameterWhenArgumentIsNotBoolean() {
        shouldNotAddRequestParameter("not a boolean");
    }

    @Test
    void shouldNotAddRequestParameterWhenRequestParamIsNull() {
        shouldNotAddRequestParameter(true);
    }

    @Test
    void shouldNotAddRequestParameterWhenParameterHasNoBoolIntAnnotation() {
        when(parameter.getParameterAnnotation(RequestParam.class)).thenReturn(mock(RequestParam.class));

        shouldNotAddRequestParameter(true);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            true, 1
            false, 0""")
    void shouldAddRequestParameter(boolean argument, String boolInt) {
        var requestParam = mock(RequestParam.class);
        var name = "testParam";

        when(parameter.getParameterAnnotation(RequestParam.class)).thenReturn(requestParam);
        when(parameter.hasParameterAnnotation(BoolInt.class)).thenReturn(true);
        when(requestParam.name()).thenReturn(name);

        boolean actual = RESOLVER.resolve(argument, parameter, requestValues);

        assertTrue(actual);
        verify(requestValues).addRequestParameter(name, boolInt);
    }

    private void shouldNotAddRequestParameter(Object argument) {
        boolean actual = RESOLVER.resolve(argument, parameter, requestValues);

        assertFalse(actual);
        verifyNoInteractions(requestValues);
    }
}