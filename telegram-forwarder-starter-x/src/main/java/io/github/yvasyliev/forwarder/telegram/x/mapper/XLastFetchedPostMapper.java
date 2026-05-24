package io.github.yvasyliev.forwarder.telegram.x.mapper;

import io.github.yvasyliev.forwarder.telegram.core.entity.LastFetchedPost;
import io.github.yvasyliev.forwarder.telegram.core.entity.LastFetchedPostId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;

/**
 * {@link LastFetchedPost} mapper.
 */
@Mapper
public interface XLastFetchedPostMapper {
    /**
     * Maps the given ID and {@code publishedAt} to a {@link LastFetchedPost}.
     *
     * @param id          the ID of the post
     * @param publishedAt the publication time of the post
     * @return the mapped {@link LastFetchedPost}
     */
    LastFetchedPost map(String id, Instant publishedAt);

    /**
     * Maps the given source name to a {@link LastFetchedPostId}.
     *
     * @param sourceName the name of the source
     * @return the mapped {@link LastFetchedPostId}
     */
    @Mapping(target = "sourceType", constant = "X")
    LastFetchedPostId map(String sourceName);
}
