package io.github.yvasyliev.telegramforwarder.thymeleaf;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thymeleaf.context.AbstractContext;
import org.thymeleaf.context.IContext;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class TelegramTemplateProcessorInterceptorTest {
    @Mock private TemplateContextCustomizer contextCustomizer;
    private TelegramTemplateProcessorInterceptor templateProcessorInterceptor;

    @BeforeEach
    void setUp() {
        templateProcessorInterceptor = new TelegramTemplateProcessorInterceptor(List.of(contextCustomizer));
    }

    @Test
    void shouldCustomizeContext() {
        var context = mock(AbstractContext.class);
        var templateContext = new TemplateContext("template", context);

        templateProcessorInterceptor.customizeContext(templateContext);

        verify(contextCustomizer).customize(context);
    }

    @Test
    void shouldDoNothing() {
        var templateContext = new TemplateContext("template", mock(IContext.class));

        templateProcessorInterceptor.customizeContext(templateContext);

        verifyNoInteractions(contextCustomizer);
    }
}
