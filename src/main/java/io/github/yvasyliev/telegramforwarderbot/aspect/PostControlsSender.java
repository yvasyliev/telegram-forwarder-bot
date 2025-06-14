package io.github.yvasyliev.telegramforwarderbot.aspect;

import io.github.yvasyliev.telegramforwarderbot.configuration.TelegramProperties;
import io.github.yvasyliev.telegramforwarderbot.dto.MessageIdsCommandCallbackDataDTO;
import io.github.yvasyliev.telegramforwarderbot.service.CallbackDataConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
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

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class PostControlsSender {
    private final TelegramProperties telegramProperties;
    private final CallbackDataConverter callbackDataConverter;
    private final TelegramClient telegramClient;

    @AfterReturning(
            pointcut = "io.github.yvasyliev.telegramforwarderbot.util.Pointcuts.sendPost()",
            returning = "message"
    )
    public void sendPostControlKeyboard(Message message) {
        sendPostControlKeyboard(List.of(message));
    }

    @AfterReturning(
            pointcut = "io.github.yvasyliev.telegramforwarderbot.util.Pointcuts.sendMediaGroup()",
            returning = "messages"
    )
    public void sendPostControlKeyboard(List<Message> messages) {
        var postControls = telegramProperties.postControls();
        var messageIds = CollectionUtils.collect(messages, Message::getMessageId, new ArrayList<>());
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
