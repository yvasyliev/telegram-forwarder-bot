package io.github.yvasyliev.forwarder.telegram.logging.mapper;

import ch.qos.logback.classic.spi.ILoggingEvent;
import io.github.yvasyliev.forwarder.telegram.core.configuration.TelegramAdminProperties;
import io.github.yvasyliev.forwarder.telegram.thymeleaf.TelegramTemplateProcessor;
import io.github.yvasyliev.forwarder.telegram.thymeleaf.TemplateContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoggingSendMessageMapperTest {
    @InjectMocks private LoggingSendMessageMapperImpl loggingSendMessageMapper;
    @Mock private LoggingTemplateContextMapper loggingTemplateContextMapper;
    @Mock private TelegramTemplateProcessor telegramTemplateProcessor;

    @Test
    void testMapNull() {
        var expected = loggingSendMessageMapper.map(null, null);

        assertNull(expected);
    }

    @Test
    void testMap() {
        var id = 123456789L;
        var telegramAdminProperties = new TelegramAdminProperties(id);
        var event = mock(ILoggingEvent.class);
        var templateContext = mock(TemplateContext.class);
        var text = "processed text";
        var expected = SendMessage.builder().chatId(id).text(text).parseMode(ParseMode.HTML).build();

        when(loggingTemplateContextMapper.map(event)).thenReturn(templateContext);
        when(telegramTemplateProcessor.process(templateContext)).thenReturn(text);

        var actual = loggingSendMessageMapper.map(telegramAdminProperties, event);

        assertEquals(expected, actual);
    }
}
