package io.github.yvasyliev.forwarder.telegram.bot.dto;

import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;

import java.io.InputStream;

/**
 * An {@link InputMediaPhoto} implementation that uses an {@link InputStream} as the source of the photo data.
 */
public class InputStreamInputMediaPhoto extends InputMediaPhoto {
    public InputStreamInputMediaPhoto(InputStream mediaStream, String fileName) {
        super(mediaStream, fileName);
    }
}
