package io.github.yvasyliev.forwarder.telegram.x.integration.metadata;

import io.github.yvasyliev.forwarder.telegram.x.service.XLastFetchedPostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class XMetadataStoreTest {
    @InjectMocks private XMetadataStore metadataStore;
    @Mock private XLastFetchedPostService xLastFetchedPostService;

    @Test
    void testPut() {
        var profile = "testProfile";
        var publishedAt = System.currentTimeMillis();

        metadataStore.put(profile, String.valueOf(publishedAt));

        verify(xLastFetchedPostService).save(profile, Instant.ofEpochMilli(publishedAt));
    }

    @Test
    void testGet() {
        var profile = "testProfile";
        var publishedAt = Instant.now();
        var expected = String.valueOf(publishedAt.toEpochMilli());

        when(xLastFetchedPostService.getLastPublishedAt(profile)).thenReturn(publishedAt);

        var actual = metadataStore.get(profile);

        assertEquals(expected, actual);
    }

    @Test
    void testRemove() {
        var actual = metadataStore.remove("testProfile");

        assertNull(actual);
    }
}
