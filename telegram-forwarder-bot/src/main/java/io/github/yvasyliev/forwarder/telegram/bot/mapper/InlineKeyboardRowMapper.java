package io.github.yvasyliev.forwarder.telegram.bot.mapper;

import io.github.yvasyliev.forwarder.telegram.bot.configuration.PostControlsSendMessageProperties;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.List;

/**
 * {@link InlineKeyboardRow} mapper.
 */
@Mapper(uses = InlineKeyboardButtonMapper.class)
public interface InlineKeyboardRowMapper {
    /**
     * Maps inline keyboard button properties and associated messages to {@link InlineKeyboardRow}.
     *
     * @param ikbps    the list of inline keyboard button properties
     * @param messages the list of messages associated with the buttons
     * @return the mapped {@link InlineKeyboardRow}
     */
    InlineKeyboardRow map(
            List<PostControlsSendMessageProperties.InlineKeyboardMarkupProperties.InlineKeyboardButtonProperties> ikbps,
            @Context List<Message> messages
    );
}
