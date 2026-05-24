package io.github.yvasyliev.forwarder.telegram.x.integration.metadata;

import io.github.yvasyliev.forwarder.telegram.x.service.XLastFetchedPostService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.integration.metadata.MetadataStore;

import java.time.Instant;

/**
 * An implementation of {@link MetadataStore} that uses {@link XLastFetchedPostService} to store and retrieve metadata
 * about the last fetched post for each profile.
 */
@RequiredArgsConstructor
public class XMetadataStore implements MetadataStore {
    private final XLastFetchedPostService xLastFetchedPostService;

    /**
     * {@inheritDoc}
     *
     * @param profile     {@inheritDoc}
     * @param publishedAt {@inheritDoc}
     */
    @Override
    public void put(String profile, String publishedAt) {
        xLastFetchedPostService.save(profile, Instant.ofEpochMilli(Long.parseLong(publishedAt)));
    }

    /**
     * {@inheritDoc}
     *
     * @param profile {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public @Nullable String get(String profile) {
        return String.valueOf(xLastFetchedPostService.getLastPublishedAt(profile).toEpochMilli());
    }

    /**
     * Removal is intentionally unsupported because {@link XLastFetchedPostService} only provides
     * storage and retrieval of the last fetched post timestamp.
     *
     * @param profile the profile whose metadata would be removed
     * @return never returns normally
     * @throws UnsupportedOperationException always, because this metadata store does not support deletion
     */
    @Override
    public @Nullable String remove(String profile) {
        throw new UnsupportedOperationException("XMetadataStore does not support remove(profile)");
    }
}
