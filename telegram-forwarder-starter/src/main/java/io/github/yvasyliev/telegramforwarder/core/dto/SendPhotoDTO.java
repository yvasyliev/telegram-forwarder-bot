package io.github.yvasyliev.telegramforwarder.core.dto;

import lombok.experimental.Delegate;

import java.io.Closeable;

/**
 * DTO for sending a photo message.
 *
 * @param photo      the photo to send
 * @param caption    the caption of the photo
 * @param hasSpoiler whether the photo has a spoiler
 */
public record SendPhotoDTO(
        @Delegate(types = Closeable.class) InputFileDTO photo,
        String caption,
        Boolean hasSpoiler
) implements Closeable {}
