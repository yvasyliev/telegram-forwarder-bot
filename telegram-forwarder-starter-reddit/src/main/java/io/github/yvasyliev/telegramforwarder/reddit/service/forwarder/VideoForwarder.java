package io.github.yvasyliev.telegramforwarder.reddit.service.forwarder;

import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarder.core.service.PostSender;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.service.VideoDownloader;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * Service for forwarding video links to Telegram.
 */
@RequiredArgsConstructor
public class VideoForwarder implements LinkForwarder {
    private final PostSender<InputFileDTO, Message> videoSender;
    private final VideoDownloader videoDownloader;

    @Override
    public void forward(Link link) throws IOException, TelegramApiException {
        var video = new InputFileDTO(
                videoDownloader.getVideoDownloadUrl(link)::openStream,
                link.id() + ".mp4",
                link.isNsfw()
        );

        videoSender.send(video, link.title());
    }
}
