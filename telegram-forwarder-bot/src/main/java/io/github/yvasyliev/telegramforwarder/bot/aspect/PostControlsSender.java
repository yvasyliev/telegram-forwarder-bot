package io.github.yvasyliev.telegramforwarder.bot.aspect;

import io.github.yvasyliev.telegramforwarder.bot.configuration.PostControlsSendMessageProperties;
import io.github.yvasyliev.telegramforwarder.bot.mapper.SendMessageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.List;

/**
 * Aspect that sends a post control keyboard after sending a post or media group.
 * It builds the keyboard buttons based on the configured post controls and sends it to the chat.
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class PostControlsSender {
    private final TelegramClient telegramClient;
    private final SendMessageMapper sendMessageMapper;
    private final PostControlsSendMessageProperties postControlsSendMessageProperties;

    /**
     * After returning advice that sends a post control keyboard after sending a single post.
     *
     * @param message the {@link Message} that was sent
     */
    @AfterReturning(
            pointcut = "io.github.yvasyliev.telegramforwarder.bot.util.Pointcuts.sendPost()",
            returning = "message"
    )
    public void sendPostControlKeyboard(Message message) {
        sendPostControlKeyboard(List.of(message));
    }

    /**
     * After returning advice that sends a post control keyboard after sending a media group.
     *
     * @param messages the {@link List} of {@link Message} that were sent
     */
    @AfterReturning(
            pointcut = "io.github.yvasyliev.telegramforwarder.bot.util.Pointcuts.sendMediaGroup()",
            returning = "messages"
    )
    public void sendPostControlKeyboard(List<Message> messages) {
        var sendMessage = sendMessageMapper.map(postControlsSendMessageProperties, messages);

        try {
            telegramClient.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Failed to send post controls", e);
        }
    }
}
