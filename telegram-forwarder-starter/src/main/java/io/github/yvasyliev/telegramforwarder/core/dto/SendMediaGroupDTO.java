package io.github.yvasyliev.telegramforwarder.core.dto;

import io.github.yvasyliev.telegramforwarder.core.util.CloseableArrayList;
import lombok.experimental.Delegate;

import java.io.Closeable;

/**
 * DTO for sending a media group.
 *
 * @param medias  the list of media to send
 * @param caption the caption for the media group
 */
public record SendMediaGroupDTO(
        @Delegate(types = Closeable.class) CloseableArrayList<InputMediaDTO> medias,
        String caption
) implements Closeable {}
