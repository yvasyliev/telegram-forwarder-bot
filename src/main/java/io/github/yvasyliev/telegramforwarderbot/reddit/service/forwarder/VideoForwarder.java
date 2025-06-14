package io.github.yvasyliev.telegramforwarderbot.reddit.service.forwarder;

import io.github.yvasyliev.telegramforwarderbot.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarderbot.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarderbot.reddit.service.VideoDownloader;
import io.github.yvasyliev.telegramforwarderbot.service.sender.VideoSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class VideoForwarder implements Forwarder {
    private final VideoSender videoSender;
    private final VideoDownloader videoDownloader;

    @Override
    public void forward(Link link) throws IOException, TelegramApiException {
        var video = new InputFileDTO(
                videoDownloader.getVideoDownloadUrl(link)::openStream,
                link.id() + ".mp4",
                link.isNsfw()
        );

        videoSender.sendVideo(video, link.title());
    }
}
