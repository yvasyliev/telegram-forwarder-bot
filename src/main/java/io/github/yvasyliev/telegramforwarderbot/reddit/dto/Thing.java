package io.github.yvasyliev.telegramforwarderbot.reddit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Thing<T>(@JsonProperty("kind") Kind kind, @JsonProperty("data") T data) {
    public enum Kind {
        @JsonProperty("Listing") LISTING,
        @JsonProperty("t3") POST
    }
}
