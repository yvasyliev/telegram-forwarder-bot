package io.github.yvasyliev.telegramforwarder.bot.dto;

import org.telegram.telegrambots.meta.api.objects.media.InputMediaVideo;

import java.io.InputStream;

/**
 * An {@link InputMediaVideo} implementation that uses an {@link InputStream} as the source of the video data.
 */
public class InputStreamInputMediaVideo extends InputMediaVideo {
    public InputStreamInputMediaVideo(InputStream mediaStream, String fileName) {
        super(mediaStream, fileName);
    }
}
