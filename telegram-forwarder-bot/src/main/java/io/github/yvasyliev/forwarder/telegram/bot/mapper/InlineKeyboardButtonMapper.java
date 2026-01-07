package io.github.yvasyliev.forwarder.telegram.bot.mapper;

import io.github.yvasyliev.forwarder.telegram.bot.configuration.PostControlsSendMessageProperties;
import io.github.yvasyliev.forwarder.telegram.bot.util.CommandCallbackDataConverter;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

/**
 * {@link InlineKeyboardButton} mapper.
 */
@Mapper(uses = {CommandCallbackDataConverter.class, CommandCallbackDataMapper.class})
public interface InlineKeyboardButtonMapper {
    /**
     * Maps {@link PostControlsSendMessageProperties.InlineKeyboardMarkupProperties.InlineKeyboardButtonProperties} to
     * {@link InlineKeyboardButton}.
     *
     * @param ikbp     the inline keyboard button properties
     * @param messages the list of messages
     * @return the mapped inline keyboard button
     */
    @Mapping(target = "callbackData", source = ".")
    InlineKeyboardButton map(
            PostControlsSendMessageProperties.InlineKeyboardMarkupProperties.InlineKeyboardButtonProperties ikbp,
            @Context List<Message> messages
    );
}
