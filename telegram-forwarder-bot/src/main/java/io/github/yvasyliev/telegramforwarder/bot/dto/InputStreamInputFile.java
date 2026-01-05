package io.github.yvasyliev.telegramforwarder.bot.dto;

import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.InputStream;

/**
 * An InputFile implementation that uses an InputStream as the source of the file data.
 */
public class InputStreamInputFile extends InputFile {
    public InputStreamInputFile(InputStream mediaStream, String fileName) {
        super(mediaStream, fileName);
    }
}
