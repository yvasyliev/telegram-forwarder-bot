package io.github.yvasyliev.telegramforwarder.logging.mapper;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import io.github.yvasyliev.telegramforwarder.thymeleaf.TemplateContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thymeleaf.context.IContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoggingTemplateContextMapperTest {
    @InjectMocks private LoggingTemplateContextMapperImpl templateContextMapper;
    @Mock private LoggingContextMapper loggingContextMapper;
    @Mock private PatternLayout patternLayout;

    @Test
    void testMapNull() {
        var expected = templateContextMapper.map(null);

        assertNull(expected);
    }

    @Test
    void testMap() {
        var text = "formatted log message";
        var context = mock(IContext.class);
        var event = mock(ILoggingEvent.class);
        var expected = new TemplateContext("error", context);

        when(patternLayout.doLayout(event)).thenReturn(text);
        when(loggingContextMapper.map(text)).thenReturn(context);

        var actual = templateContextMapper.map(event);

        assertEquals(expected, actual);
    }
}
