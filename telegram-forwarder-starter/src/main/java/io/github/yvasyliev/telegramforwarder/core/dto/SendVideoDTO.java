package io.github.yvasyliev.telegramforwarder.core.dto;

import lombok.experimental.Delegate;

import java.io.Closeable;

/**
 * DTO for sending video files.
 *
 * @param video      the video file to send
 * @param caption    the caption for the video
 * @param hasSpoiler indicates if the video has a spoiler
 */
public record SendVideoDTO(
        @Delegate(types = Closeable.class) InputFileDTO video,
        String caption,
        Boolean hasSpoiler
) implements Closeable {}
