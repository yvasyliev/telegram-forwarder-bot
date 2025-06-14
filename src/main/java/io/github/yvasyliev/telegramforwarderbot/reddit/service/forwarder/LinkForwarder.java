package io.github.yvasyliev.telegramforwarderbot.reddit.service.forwarder;

import io.github.yvasyliev.telegramforwarderbot.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarderbot.service.sender.UrlSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@RequiredArgsConstructor
public class LinkForwarder implements Forwarder {
    private final UrlSender urlSender;

    @Override
    public void forward(Link link) throws TelegramApiException {
        urlSender.sendUrl(link.url(), link.title());
    }
}
