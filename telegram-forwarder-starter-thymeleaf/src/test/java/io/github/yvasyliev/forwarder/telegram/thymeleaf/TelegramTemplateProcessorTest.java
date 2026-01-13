package io.github.yvasyliev.forwarder.telegram.thymeleaf;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.IContext;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TelegramTemplateProcessorTest {
    @InjectMocks private TelegramTemplateProcessor templateProcessor;
    @Mock private ITemplateEngine templateEngine;

    @Test
    void testProcess() {
        var template = "template";
        var context = mock(IContext.class);
        var templateContext = new TemplateContext(template, context);

        templateProcessor.process(templateContext);

        verify(templateEngine).process(template, context);
    }
}
