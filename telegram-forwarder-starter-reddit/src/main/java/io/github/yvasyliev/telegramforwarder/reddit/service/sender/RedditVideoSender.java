package io.github.yvasyliev.telegramforwarder.reddit.service.sender;

import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarder.core.service.PostSender;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.service.RedditVideoDownloader;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * Implementation of {@link RedditPostSender} for sending Reddit video posts.
 */
@RequiredArgsConstructor
public class RedditVideoSender implements RedditPostSender {
    private final PostSender<InputFileDTO, Message> videoSender;
    private final RedditVideoDownloader redditVideoDownloader;

    @Override
    public void send(Link post) throws IOException, TelegramApiException {
        var video = new InputFileDTO(
                redditVideoDownloader.getVideoDownloadUrl(post)::openStream,
                post.id() + ".mp4",
                post.isNsfw()
        );

        videoSender.send(video, post.title());
    }
}
