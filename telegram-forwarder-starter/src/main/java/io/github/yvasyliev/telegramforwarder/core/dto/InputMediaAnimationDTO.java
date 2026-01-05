package io.github.yvasyliev.telegramforwarder.core.dto;

import lombok.experimental.Delegate;

import java.io.Closeable;
import java.io.InputStream;

/**
 * Data Transfer Object representing an animation media input.
 *
 * @param mediaStream the input stream of the media
 * @param fileName    the name of the file
 * @param hasSpoiler  indicates if the animation has a spoiler
 */
public record InputMediaAnimationDTO(
        @Delegate(types = Closeable.class) InputStream mediaStream,
        String fileName,
        Boolean hasSpoiler
) implements InputMediaDTO {}
