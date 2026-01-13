package io.github.yvasyliev.forwarder.telegram.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import io.github.yvasyliev.forwarder.telegram.core.configuration.TelegramAdminProperties;
import io.github.yvasyliev.forwarder.telegram.logging.mapper.LoggingSendMessageMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.FieldSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.net.HttpURLConnection;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TelegramBotAppenderTest {
    @SuppressWarnings("checkstyle:ConstantName")
    private static final Supplier<Stream<TelegramApiException>> shouldHandleTelegramApiExceptions = () -> {
        var e = mock(TelegramApiRequestException.class);

        when(e.getErrorCode()).thenReturn(HttpURLConnection.HTTP_FORBIDDEN);

        return Stream.of(mock(TelegramApiException.class), mock(TelegramApiRequestException.class), e);
    };

    @InjectMocks private TelegramBotAppender telegramBotAppender;
    @Mock private TelegramAdminProperties adminProperties;
    @Mock private LoggingSendMessageMapper sendMessageMapper;
    @Mock private TelegramClient telegramClient;
    @Mock private ILoggingEvent event;
    @Mock private SendMessage sendMessage;

    @BeforeEach
    void setUp() {
        when(sendMessageMapper.map(adminProperties, event)).thenReturn(sendMessage);
    }

    @AfterEach
    void tearDown() throws TelegramApiException {
        verify(telegramClient).execute(sendMessage);
    }

    @Test
    void testAppend() {
        assertDoesNotThrow(() -> telegramBotAppender.append(event));
    }

    @ParameterizedTest
    @FieldSource
    void shouldHandleTelegramApiExceptions(TelegramApiException e) throws TelegramApiException {
        when(telegramClient.execute(sendMessage)).thenThrow(e);

        assertDoesNotThrow(() -> telegramBotAppender.append(event));
    }
}
