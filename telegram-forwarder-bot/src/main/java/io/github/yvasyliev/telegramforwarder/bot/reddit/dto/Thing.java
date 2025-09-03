package io.github.yvasyliev.telegramforwarder.bot.reddit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A generic container for Reddit API responses.
 *
 * @param kind the type of the response, indicating whether it's a listing or a post
 * @param data the actual data contained in the response, which can be of any type
 * @param <T>  the type of the data contained in the response
 */
public record Thing<T>(@JsonProperty("kind") Kind kind, @JsonProperty("data") T data) {
    /**
     * Enum representing the possible kinds of Reddit API responses.
     * It can either be a listing of posts or a single post.
     */
    public enum Kind {
        /**
         * Represents a listing of posts, typically returned when fetching multiple posts
         * from a subreddit.
         */
        @JsonProperty("Listing") LISTING,

        /**
         * Represents a single post, typically returned when fetching a specific post by ID.
         */
        @JsonProperty("t3") POST
    }
}
