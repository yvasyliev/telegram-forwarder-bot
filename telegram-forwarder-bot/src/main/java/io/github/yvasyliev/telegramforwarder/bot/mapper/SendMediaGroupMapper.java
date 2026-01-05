package io.github.yvasyliev.telegramforwarder.bot.mapper;

import io.github.yvasyliev.telegramforwarder.core.configuration.TelegramAdminProperties;
import io.github.yvasyliev.telegramforwarder.core.dto.SendMediaGroupDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;

/**
 * {@link SendMediaGroup} mapper.
 */
@Mapper(uses = InputMediaMapper.class)
public interface SendMediaGroupMapper {
    /**
     * Maps the given {@link SendMediaGroupDTO} and {@link TelegramAdminProperties} to a {@link SendMediaGroup}.
     *
     * @param sendMediaGroupDTO the send media group DTO
     * @param adminProperties   the Telegram admin properties
     * @return the mapped {@link SendMediaGroup}
     */
    @Mapping(target = "chatId", source = "adminProperties.id")
    SendMediaGroup map(SendMediaGroupDTO sendMediaGroupDTO, TelegramAdminProperties adminProperties);

    /**
     * Sets the caption for the first media in the {@link SendMediaGroup} after mapping.
     *
     * @param sendMediaGroup    the mapped {@link SendMediaGroup}
     * @param sendMediaGroupDTO the source {@link SendMediaGroupDTO}
     */
    @AfterMapping
    default void setCaption(@MappingTarget SendMediaGroup sendMediaGroup, SendMediaGroupDTO sendMediaGroupDTO) {
        sendMediaGroup.getMedias().getFirst().setCaption(sendMediaGroupDTO.caption());
    }
}
