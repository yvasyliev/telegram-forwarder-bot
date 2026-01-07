package io.github.yvasyliev.forwarder.telegram.bot.mapper;

import io.github.yvasyliev.forwarder.telegram.bot.configuration.PostControlsSendMessageProperties;
import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.List;

/**
 * {@link ReplyKeyboard} mapper.
 */
@Mapper(uses = InlineKeyboardRowMapper.class)
public interface ReplyKeyboardMapper {
    /**
     * Maps {@link PostControlsSendMessageProperties.InlineKeyboardMarkupProperties} to {@link InlineKeyboardMarkup}.
     *
     * @param inlineKeyboardMarkupProperties the inline keyboard markup properties
     * @param messages                       the list of messages
     * @return the mapped inline keyboard markup
     */
    @BeanMapping(resultType = InlineKeyboardMarkup.class)
    ReplyKeyboard map(
            PostControlsSendMessageProperties.InlineKeyboardMarkupProperties inlineKeyboardMarkupProperties,
            @Context List<Message> messages
    );
}
