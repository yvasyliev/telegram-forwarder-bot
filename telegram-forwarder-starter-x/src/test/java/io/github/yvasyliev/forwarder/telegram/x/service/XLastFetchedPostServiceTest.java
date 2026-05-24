package io.github.yvasyliev.forwarder.telegram.x.service;

import io.github.yvasyliev.forwarder.telegram.core.entity.LastFetchedPost;
import io.github.yvasyliev.forwarder.telegram.core.entity.LastFetchedPostId;
import io.github.yvasyliev.forwarder.telegram.core.service.LastFetchedPostService;
import io.github.yvasyliev.forwarder.telegram.x.mapper.XLastFetchedPostMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class XLastFetchedPostServiceTest {
    @InjectMocks private XLastFetchedPostService xLastFetchedPostService;
    @Mock private LastFetchedPostService lastFetchedPostService;
    @Mock private XLastFetchedPostMapper xLastFetchedPostMapper;

    @Test
    void testGetLastPublishedAt() {
        var profile = "someProfile";
        var id = mock(LastFetchedPostId.class);
        var now = Instant.now();

        when(xLastFetchedPostMapper.map(profile)).thenReturn(id);
        when(lastFetchedPostService.findPublishedAtById(id)).thenReturn(now);

        var actual = xLastFetchedPostService.getLastPublishedAt(profile);

        assertEquals(now, actual);
    }

    @Test
    void testSave() {
        var profile = "someProfile";
        var publishedAt = Instant.now();
        var expected = mock(LastFetchedPost.class);

        when(xLastFetchedPostMapper.map(profile, publishedAt)).thenReturn(expected);
        when(lastFetchedPostService.save(expected)).thenReturn(expected);

        var actual = xLastFetchedPostService.save(profile, publishedAt);

        assertEquals(expected, actual);
    }
}
