package io.github.yvasyliev.telegramforwarderbot.service.sender;

import io.github.yvasyliev.telegramforwarderbot.configuration.TelegramProperties;
import io.github.yvasyliev.telegramforwarderbot.dto.InputFileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.IOException;

/**
 * Service for sending video files to a Telegram chat.
 */
@Service
@RequiredArgsConstructor
public class VideoSender {
    private final TelegramProperties telegramProperties;
    private final TelegramClient telegramClient;

    /**
     * Sends a video file to the admin chat.
     *
     * @param video   the video file to send
     * @param caption the caption for the video
     * @return the sent message
     * @throws IOException          if an error occurs while reading the video file
     * @throws TelegramApiException if an error occurs while sending the video
     */
    public Message sendVideo(InputFileDTO video, String caption) throws IOException, TelegramApiException {
        try (var inputStream = video.fileSupplier().get()) {
            var sendVideo = SendVideo.builder()
                    .chatId(telegramProperties.adminId())
                    .video(new InputFile(inputStream, video.filename()))
                    .supportsStreaming(true)
                    .caption(caption)
                    .hasSpoiler(video.hasSpoiler())
                    .build();
            return telegramClient.execute(sendVideo);
        }
    }
}
