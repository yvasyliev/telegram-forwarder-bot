package io.github.yvasyliev.telegramforwarderbot.reddit.service;

import io.github.yvasyliev.telegramforwarderbot.reddit.entity.RedditInstantProperty;
import io.github.yvasyliev.telegramforwarderbot.reddit.repository.RedditInstantPropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class RedditInstantPropertyService {
    private final RedditInstantPropertyRepository repository;

    public Instant getLastCreated() {
        return repository.getValue(RedditInstantProperty.RedditInstantPropertyName.LAST_CREATED).orElse(Instant.EPOCH);
    }

    @Transactional
    public RedditInstantProperty saveLastCreated(Instant value) {
        var instantProperty = new RedditInstantProperty();
        instantProperty.setName(RedditInstantProperty.RedditInstantPropertyName.LAST_CREATED);
        instantProperty.setValue(value);
        return repository.save(instantProperty);
    }
}
