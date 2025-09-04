package io.github.yvasyliev.telegramforwarder.thymeleaf;

import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thymeleaf.context.AbstractContext;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TelegramTemplateEngineInterceptorTest {
    @Mock private TemplateContextCustomizer contextCustomizer;
    @Mock private JoinPoint joinPoint;
    private TelegramTemplateEngineInterceptor telegramTemplateEngineInterceptor;

    @BeforeEach
    void setUp() {
        telegramTemplateEngineInterceptor = new TelegramTemplateEngineInterceptor(List.of(contextCustomizer));
    }

    @Test
    void shouldCustomizeContext() {
        var context = mock(AbstractContext.class);
        var args = new Object[]{new Object(), context, "string", 123};

        when(joinPoint.getArgs()).thenReturn(args);

        telegramTemplateEngineInterceptor.customizeContext(joinPoint);

        verify(contextCustomizer).accept(context);
    }

    @Test
    void shouldNotCustomizeContextWhenNoContext() {
        var args = new Object[]{"string", 123, new Object()};

        when(joinPoint.getArgs()).thenReturn(args);

        telegramTemplateEngineInterceptor.customizeContext(joinPoint);

        verifyNoInteractions(contextCustomizer);
    }
}