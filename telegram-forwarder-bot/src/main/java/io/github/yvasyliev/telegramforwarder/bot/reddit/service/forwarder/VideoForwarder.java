package io.github.yvasyliev.telegramforwarder.bot.reddit.service.forwarder;

import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarder.bot.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.bot.reddit.service.VideoDownloader;
import io.github.yvasyliev.telegramforwarder.core.service.PostSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * Service for forwarding video links to Telegram.
 */
@Service
@RequiredArgsConstructor
public class VideoForwarder implements Forwarder {
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
