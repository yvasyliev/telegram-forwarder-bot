package io.github.yvasyliev.telegramforwarder.bot.mapper;

import io.github.yvasyliev.telegramforwarder.bot.configuration.PostControlsEditMessageTextProperties;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.message.Message;

/**
 * {@link EditMessageText} mapper.
 */
@Mapper
public interface EditMessageTextMapper {
    /**
     * Maps {@link Message} and {@link PostControlsEditMessageTextProperties.EditMessageTextProperties} to
     * {@link EditMessageText}.
     *
     * @param message the original message
     * @param emtp    the edit message text properties
     * @return the mapped {@link EditMessageText}
     */
    @Mapping(target = "text", source = "emtp.text")
    @Mapping(target = "entities", ignore = true)
    @Mapping(target = "linkPreviewOptions", ignore = true)
    @Mapping(target = "businessConnectionId", ignore = true)
    EditMessageText map(Message message, PostControlsEditMessageTextProperties.EditMessageTextProperties emtp);
}
