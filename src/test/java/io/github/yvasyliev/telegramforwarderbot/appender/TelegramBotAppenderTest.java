package io.github.yvasyliev.telegramforwarderbot.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.BasicStatusManager;
import ch.qos.logback.core.Layout;
import io.github.yvasyliev.telegramforwarderbot.service.TelegramTemplateEngine;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.FieldSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.ApiResponse;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TelegramBotAppenderTest {
    private static final long ADMIN_ID = 123;
    private static final String LOG_MESSAGE = "log message";
    private static final String RENDERED = "<code>log message<code>";
    private static final BotApiMethod<Message> SEND_MESSAGE = SendMessage.builder()
            .chatId(ADMIN_ID)
            .text(RENDERED)
            .parseMode(ParseMode.HTML)
            .build();
    private static final Supplier<Stream<TelegramApiException>> shouldLogError = () -> {
        var response = mock(ApiResponse.class);

        when(response.getErrorCode()).thenReturn(NumberUtils.INTEGER_MINUS_ONE);

        return Stream.of(new TelegramApiException(), new TelegramApiRequestException(null, response));
    };
    @Mock private TelegramClient telegramClient;
    @Mock private TelegramTemplateEngine templateEngine;
    @Mock private Layout<ILoggingEvent> patternLayout;
    @Mock private ILoggingEvent event;
    private TelegramBotAppender telegramBotAppender;

    @BeforeEach
    void setUp() {
        telegramBotAppender = new TelegramBotAppender(telegramClient, templateEngine, ADMIN_ID, patternLayout);
        var logbackContext = mock(ch.qos.logback.core.Context.class);
        var thymeleafContext = new org.thymeleaf.context.Context();

        telegramBotAppender.setContext(logbackContext);
        telegramBotAppender.setName("telegramBotAppender");
        thymeleafContext.setVariable("text", LOG_MESSAGE);

        when(patternLayout.doLayout(event)).thenReturn(LOG_MESSAGE);
        when(templateEngine.process(eq("error"), refEq(thymeleafContext))).thenReturn(RENDERED);
        when(logbackContext.getStatusManager()).thenReturn(new BasicStatusManager());
    }

    @AfterEach
    void tearDown() throws TelegramApiException {
        verify(telegramClient).execute(SEND_MESSAGE);
    }

    @Test
    void shouldSendMessage() {
        telegramBotAppender.append(event);

        assertThat(telegramBotAppender.getContext().getStatusManager().getCopyOfStatusList()).isEmpty();
    }

    @ParameterizedTest
    @FieldSource
    void shouldLogError(TelegramApiException e) throws TelegramApiException {
        when(telegramClient.execute(SEND_MESSAGE)).thenThrow(e);

        telegramBotAppender.append(event);

        assertThat(telegramBotAppender.getContext().getStatusManager().getCopyOfStatusList()).isNotEmpty();
    }

    @Test
    void shouldNotLogError() throws TelegramApiException {
        var response = mock(ApiResponse.class);

        when(response.getErrorCode()).thenReturn(HttpStatus.FORBIDDEN.value());
        doThrow(new TelegramApiRequestException(null, response)).when(telegramClient).execute(SEND_MESSAGE);

        telegramBotAppender.append(event);

        assertThat(telegramBotAppender.getContext().getStatusManager().getCopyOfStatusList()).isEmpty();
    }
}