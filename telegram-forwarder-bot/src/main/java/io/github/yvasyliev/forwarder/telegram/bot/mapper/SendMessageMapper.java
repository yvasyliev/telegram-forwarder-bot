package io.github.yvasyliev.forwarder.telegram.bot.mapper;

import io.github.yvasyliev.forwarder.telegram.bot.configuration.PostControlsSendMessageProperties;
import io.github.yvasyliev.forwarder.telegram.bot.configuration.UnauthorizedActionProperties;
import io.github.yvasyliev.forwarder.telegram.bot.util.TelegramBotTemplateProcessor;
import io.github.yvasyliev.forwarder.telegram.core.configuration.TelegramAdminProperties;
import io.github.yvasyliev.forwarder.telegram.core.dto.SendUrlDTO;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.List;

/**
 * {@link SendMessage} mapper.
 */
@Mapper(uses = {TelegramBotTemplateProcessor.class, ReplyKeyboardMapper.class, TemplateContextMapper.class})
public interface SendMessageMapper {
    /**
     * Maps {@link SendUrlDTO} and {@link TelegramAdminProperties} to {@link SendMessage}.
     *
     * @param sendUrlDTO      the send URL DTO
     * @param adminProperties the Telegram admin properties
     * @return the mapped {@link SendMessage}
     */
    @Mapping(target = "chatId", source = "adminProperties.id")
    @Mapping(target = "text", source = "sendUrlDTO", qualifiedByName = TelegramBotTemplateProcessor.NAME)
    SendMessage map(SendUrlDTO sendUrlDTO, TelegramAdminProperties adminProperties);

    /**
     * Maps {@link PostControlsSendMessageProperties} and a list of messages to {@link SendMessage}.
     *
     * @param postControlsSendMessageProperties the post controls send message properties
     * @param messages                          the list of messages
     * @return the mapped {@link SendMessage}
     */
    default SendMessage map(
            PostControlsSendMessageProperties postControlsSendMessageProperties,
            List<Message> messages
    ) {
        return map(postControlsSendMessageProperties, messages, messages);
    }

    /**
     * Maps {@link PostControlsSendMessageProperties} and a list of messages to {@link SendMessage} with context
     * messages.
     *
     * @param postControlsSendMessageProperties the post controls send message properties
     * @param messages                          the list of messages
     * @param contextMessages                   the context messages
     * @return the mapped {@link SendMessage}
     */
    @Mapping(target = "chatId", source = "messages.first.chatId")
    SendMessage map(
            PostControlsSendMessageProperties postControlsSendMessageProperties,
            List<Message> messages,
            @Context List<Message> contextMessages
    );

    /**
     * Maps {@link Message} and a template string to {@link SendMessage}.
     *
     * @param message  the original message
     * @param template the template string
     * @return the mapped {@link SendMessage}
     */
    @Mapping(target = "text", source = "template", qualifiedByName = TelegramBotTemplateProcessor.NAME)
    @Mapping(target = "parseMode", constant = ParseMode.HTML)
    @Mapping(target = "replyMarkup", ignore = true)
    @Mapping(target = "entities", ignore = true)
    @Mapping(target = "linkPreviewOptions", ignore = true)
    @Mapping(target = "businessConnectionId", ignore = true)
    @Mapping(target = "messageThreadId", ignore = true)
    SendMessage map(Message message, String template);

    /**
     * Maps {@link Message} and {@link UnauthorizedActionProperties} to {@link SendMessage}.
     *
     * @param message                      the original message
     * @param unauthorizedActionProperties the unauthorized action properties
     * @return the mapped {@link SendMessage}
     */
    @Mapping(target = "text", source = "unauthorizedActionProperties.text")
    @Mapping(target = "replyMarkup", ignore = true)
    @Mapping(target = "entities", ignore = true)
    @Mapping(target = "linkPreviewOptions", ignore = true)
    @Mapping(target = "businessConnectionId", ignore = true)
    @Mapping(target = "messageThreadId", ignore = true)
    SendMessage map(Message message, UnauthorizedActionProperties unauthorizedActionProperties);

    /**
     * Maps {@link TelegramAdminProperties} and a template string to {@link SendMessage}.
     *
     * @param adminProperties the Telegram admin properties
     * @param template        the template string
     * @return the mapped {@link SendMessage}
     */
    @Mapping(target = "chatId", source = "adminProperties.id")
    @Mapping(target = "text", source = "template", qualifiedByName = TelegramBotTemplateProcessor.NAME)
    @Mapping(target = "parseMode", constant = ParseMode.HTML)
    SendMessage map(TelegramAdminProperties adminProperties, String template);
}
