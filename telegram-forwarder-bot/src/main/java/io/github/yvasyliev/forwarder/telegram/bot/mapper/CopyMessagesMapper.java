package io.github.yvasyliev.forwarder.telegram.bot.mapper;

import io.github.yvasyliev.forwarder.telegram.bot.configuration.TelegramChannelProperties;
import io.github.yvasyliev.forwarder.telegram.bot.entity.ApprovedPost;
import io.github.yvasyliev.forwarder.telegram.core.configuration.TelegramAdminProperties;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.telegram.telegrambots.meta.api.methods.CopyMessages;

/**
 * {@link CopyMessages} mapper.
 */
@Mapper
public interface CopyMessagesMapper {
    /**
     * Maps the given properties and post to a {@link CopyMessages} object.
     *
     * @param channelProperties the Telegram channel properties
     * @param adminProperties   the Telegram admin properties
     * @param post              the approved post
     * @return the mapped {@link CopyMessages} object
     */
    @Mapping(target = "fromChatId", source = "adminProperties.id")
    CopyMessages map(
            TelegramChannelProperties channelProperties,
            TelegramAdminProperties adminProperties,
            ApprovedPost post
    );
}
