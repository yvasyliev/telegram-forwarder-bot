package io.github.yvasyliev.telegramforwarder.core.dto;

import lombok.experimental.Delegate;

import java.io.Closeable;
import java.io.InputStream;

/**
 * DTO representing a video media input.
 *
 * @param mediaStream the input stream of the video media
 * @param fileName    the name of the video file
 * @param hasSpoiler  indicates if the video has a spoiler
 */
public record InputMediaVideoDTO(
        @Delegate(types = Closeable.class) InputStream mediaStream,
        String fileName,
        Boolean hasSpoiler
) implements InputMediaDTO {}
