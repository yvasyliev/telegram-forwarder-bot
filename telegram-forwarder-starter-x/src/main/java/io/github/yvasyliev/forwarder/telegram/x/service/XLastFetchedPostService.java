package io.github.yvasyliev.forwarder.telegram.x.service;

import io.github.yvasyliev.forwarder.telegram.core.entity.LastFetchedPost;
import io.github.yvasyliev.forwarder.telegram.core.service.LastFetchedPostService;
import io.github.yvasyliev.forwarder.telegram.x.mapper.XLastFetchedPostMapper;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

/**
 * Service for managing the last fetched post information for X profiles.
 */
@RequiredArgsConstructor
public class XLastFetchedPostService {
    private final LastFetchedPostService lastFetchedPostService;
    private final XLastFetchedPostMapper xLastFetchedPostMapper;

    /**
     * Retrieves the publication time of the last fetched post for the given X profile.
     *
     * @param profile the name of the X profile
     * @return the publication time of the last fetched post
     */
    public Instant getLastPublishedAt(String profile) {
        return lastFetchedPostService.findPublishedAtById(xLastFetchedPostMapper.map(profile));
    }

    /**
     * Saves the publication time of the last fetched post for the given X profile.
     *
     * @param profile     the name of the X profile
     * @param publishedAt the publication time of the last fetched post
     * @return the saved {@link LastFetchedPost} entity
     */
    public LastFetchedPost save(String profile, Instant publishedAt) {
        return lastFetchedPostService.save(xLastFetchedPostMapper.map(profile, publishedAt));
    }
}
