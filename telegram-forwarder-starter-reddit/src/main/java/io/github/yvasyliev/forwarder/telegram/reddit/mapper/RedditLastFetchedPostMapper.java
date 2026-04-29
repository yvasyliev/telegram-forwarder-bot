package io.github.yvasyliev.forwarder.telegram.reddit.mapper;

import io.github.yvasyliev.forwarder.telegram.core.entity.LastFetchedPost;
import io.github.yvasyliev.forwarder.telegram.core.entity.LastFetchedPostId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;

/**
 * A Reddit mapper for {@link LastFetchedPost}.
 */
@Mapper
public interface RedditLastFetchedPostMapper {
    /**
     * Maps a subreddit and a publication timestamp to a {@link LastFetchedPost}.
     *
     * @param id          the subreddit name
     * @param publishedAt the timestamp of the last published post
     * @return a {@link LastFetchedPost}.
     */
    LastFetchedPost map(String id, Instant publishedAt);

    /**
     * Maps a subreddit name to {@link LastFetchedPostId}.
     *
     * @param sourceName the subreddit name
     * @return a {@link LastFetchedPostId}.
     */
    @Mapping(target = "sourceType", constant = "REDDIT")
    LastFetchedPostId map(String sourceName);
}
