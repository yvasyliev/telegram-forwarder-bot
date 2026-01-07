package io.github.yvasyliev.forwarder.telegram.core.dto;

import lombok.experimental.Delegate;

import java.io.Closeable;
import java.io.InputStream;

/**
 * DTO representing a photo media input.
 *
 * @param mediaStream the input stream of the photo media
 * @param fileName    the name of the file
 * @param hasSpoiler  indicates if the photo has a spoiler
 */
public record InputMediaPhotoDTO(
        @Delegate(types = Closeable.class) InputStream mediaStream,
        String fileName,
        Boolean hasSpoiler
) implements InputMediaDTO {}
