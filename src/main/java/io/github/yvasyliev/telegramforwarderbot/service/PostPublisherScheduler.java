package io.github.yvasyliev.telegramforwarderbot.service;

import io.github.yvasyliev.telegramforwarderbot.configuration.TelegramProperties;
import io.github.yvasyliev.telegramforwarderbot.entity.ApprovedPost;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.CopyMessages;
import org.telegram.telegrambots.meta.generics.TelegramClient;

/**
 * Scheduler that publishes approved posts to a Telegram channel at a specified time.
 * It polls the {@link ApprovedPostService} for {@link ApprovedPost}s and sends them to the channel.
 * The schedule is defined by the cron expression and can be configured via {@code scheduler.post-publisher.cron}
 * property (default: {@code 0 30 8-21 * * *}).
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PostPublisherScheduler {
    private final ApprovedPostService postService;
    private final TelegramProperties telegramProperties;
    private final TelegramClient telegramClient;

    /**
     * Scheduled method that publishes approved posts to the Telegram channel.
     * It runs according to the cron expression defined in {@code scheduler.post-publisher.cron} property (default:
     * {@code 0 30 8-21 * * *}).
     * If no posts are available, it does nothing.
     */
    @Scheduled(cron = "${scheduler.post-publisher.cron:0 30 8-21 * * *}")
    public void publishPost() {
        try {
            var post = postService.poll().orElse(null);

            if (post != null) {
                var copyMessages = CopyMessages.builder()
                        .chatId(telegramProperties.channelUsername())
                        .fromChatId(telegramProperties.adminId())
                        .messageIds(post.getMessageIds())
                        .removeCaption(post.getRemoveCaption())
                        .build();

                telegramClient.execute(copyMessages);
            }
        } catch (Exception e) {
            log.error("Failed to publish post", e);
        }
    }
}
