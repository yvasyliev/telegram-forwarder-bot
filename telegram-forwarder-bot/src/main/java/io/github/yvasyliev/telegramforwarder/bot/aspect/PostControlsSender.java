package io.github.yvasyliev.telegramforwarder.bot.aspect;

import io.github.yvasyliev.telegramforwarder.bot.configuration.TelegramProperties;
import io.github.yvasyliev.telegramforwarder.bot.dto.MessageIdsCommandCallbackDataDTO;
import io.github.yvasyliev.telegramforwarder.bot.service.CallbackDataConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Aspect that sends a post control keyboard after sending a post or media group.
 * It builds the keyboard buttons based on the configured post controls and sends it to the chat.
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class PostControlsSender {
    private final TelegramProperties telegramProperties;
    private final CallbackDataConverter callbackDataConverter;
    private final TelegramClient telegramClient;

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
        var postControls = telegramProperties.postControls();
        var messageIds = messages.stream().map(Message::getMessageId).toList();
        var buttons = buildKeyboardButtons(postControls.buttons(), messageIds);
        var sendMessage = SendMessage.builder()
                .chatId(messages.getFirst().getChatId())
                .text(postControls.initialMessageText())
                .replyMarkup(new InlineKeyboardMarkup(buttons))
                .build();

        try {
            telegramClient.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Failed to send post controls", e);
        }
    }

    private List<InlineKeyboardRow> buildKeyboardButtons(
            Map<String, TelegramProperties.PostControls.Button> buttonsProperties,
            List<Integer> messageIds
    ) {
        var buttons = new ArrayList<InlineKeyboardRow>();

        for (var buttonPropertiesEntry : buttonsProperties.entrySet()) {
            var callbackDataDTO = new MessageIdsCommandCallbackDataDTO();

            callbackDataDTO.setCommand(buttonPropertiesEntry.getKey());
            callbackDataDTO.setMessageIds(messageIds);

            var callbackData = callbackDataConverter.toCallbackData(callbackDataDTO);
            var inlineKeyboardButton = InlineKeyboardButton.builder()
                    .text(buttonPropertiesEntry.getValue().buttonText())
                    .callbackData(callbackData)
                    .build();

            buttons.add(new InlineKeyboardRow(inlineKeyboardButton));
        }

        return buttons;
    }
}
