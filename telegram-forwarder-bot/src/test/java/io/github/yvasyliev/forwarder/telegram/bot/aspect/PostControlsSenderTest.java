package io.github.yvasyliev.forwarder.telegram.bot.aspect;

import io.github.yvasyliev.forwarder.telegram.bot.configuration.PostControlsSendMessageProperties;
import io.github.yvasyliev.forwarder.telegram.bot.mapper.SendMessageMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.FieldSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostControlsSenderTest {
    @SuppressWarnings("checkstyle:ConstantName")
    private static final Object[] shouldDoNothingWhenPostIsUnsupported = {null, new Object(), List.of()};

    @InjectMocks private PostControlsSender postControlsSender;
    @Mock private TelegramClient telegramClient;
    @Mock private SendMessageMapper sendMessageMapper;
    @Mock private PostControlsSendMessageProperties postControlsSendMessageProperties;

    @ParameterizedTest
    @FieldSource
    void shouldDoNothingWhenPostIsUnsupported(Object post) {
        assertDoesNotThrow(() -> postControlsSender.sendPostControlKeyboard(post));

        verifyNoInteractions(telegramClient, sendMessageMapper, postControlsSendMessageProperties);
    }

    @Test
    void shouldHandleTelegramApiException() throws TelegramApiException {
        var post = mock(Message.class);
        var sendMessage = mock(SendMessage.class);

        when(sendMessageMapper.map(postControlsSendMessageProperties, List.of(post))).thenReturn(sendMessage);
        when(telegramClient.execute(sendMessage)).thenThrow(TelegramApiException.class);

        assertDoesNotThrow(() -> postControlsSender.sendPostControlKeyboard(post));
    }

    @Test
    void shouldSendPostControlKeyboardForPost() throws TelegramApiException {
        var post = mock(Message.class);
        var sendMessage = mock(SendMessage.class);

        when(sendMessageMapper.map(postControlsSendMessageProperties, List.of(post))).thenReturn(sendMessage);

        postControlsSender.sendPostControlKeyboard(post);

        verify(telegramClient).execute(sendMessage);
    }

    @Test
    void shouldSendPostControlKeyboardForPosts() throws TelegramApiException {
        var posts = List.of(mock(Message.class));
        var sendMessage = mock(SendMessage.class);

        when(sendMessageMapper.map(postControlsSendMessageProperties, posts)).thenReturn(sendMessage);

        postControlsSender.sendPostControlKeyboard(posts);

        verify(telegramClient).execute(sendMessage);
    }
}
