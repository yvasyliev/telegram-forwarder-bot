package io.github.yvasyliev.telegramforwarder.bot.util;

import io.github.yvasyliev.telegramforwarder.thymeleaf.TemplateContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.IContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TelegramBotTemplateProcessorTest {
    @InjectMocks private TelegramBotTemplateProcessor telegramBotTemplateProcessor;
    @Mock private ITemplateEngine templateEngine;

    @Test
    void testProcess() {
        var template = "template";
        var context = mock(IContext.class);
        var expected = "processedTemplate";

        when(templateEngine.process(template, context)).thenReturn(expected);

        var actual = telegramBotTemplateProcessor.process(new TemplateContext(template, context));

        assertEquals(expected, actual);
    }
}
