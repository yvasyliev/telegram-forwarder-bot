package io.github.yvasyliev.forwarder.telegram.reddit.util;

import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GalleryMetadataPartitionerTest {
    private static final int PARTITION_SIZE = 2;
    private static final GalleryMetadataPartitioner GALLERY_METADATA_PARTITIONER = new GalleryMetadataPartitioner(
            PARTITION_SIZE
    );
    @Mock private Link post;

    @Test
    void shouldReturnOnePartitionForOneMetadata() {
        var mediaId1 = "mediaId1";
        var galleryData = new Link.GalleryData(List.of(new Link.GalleryData.Item(mediaId1, null)));
        var metadata1 = mock(Link.Metadata.class);
        var expected = List.of(List.of(metadata1));

        when(post.galleryData()).thenReturn(galleryData);
        when(post.mediaMetadata()).thenReturn(Map.of(mediaId1, metadata1));

        var actual = GALLERY_METADATA_PARTITIONER.partition(post);

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnOnePartitionForTwoMetadata() {
        var mediaId1 = "mediaId1";
        var mediaId2 = "mediaId2";
        var galleryData = new Link.GalleryData(List.of(
                new Link.GalleryData.Item(mediaId1, null),
                new Link.GalleryData.Item(mediaId2, null)
        ));
        var metadata1 = mock(Link.Metadata.class);
        var metadata2 = mock(Link.Metadata.class);
        var expected = List.of(List.of(metadata1, metadata2));

        when(post.galleryData()).thenReturn(galleryData);
        when(post.mediaMetadata()).thenReturn(Map.of(
                mediaId1, metadata1,
                mediaId2, metadata2
        ));

        var actual = GALLERY_METADATA_PARTITIONER.partition(post);

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnTwoPartitionsForThreeMetadata() {
        var mediaId1 = "mediaId1";
        var mediaId2 = "mediaId2";
        var mediaId3 = "mediaId3";
        var galleryData = new Link.GalleryData(List.of(
                new Link.GalleryData.Item(mediaId1, null),
                new Link.GalleryData.Item(mediaId2, null),
                new Link.GalleryData.Item(mediaId3, null)
        ));
        var metadata1 = mock(Link.Metadata.class);
        var metadata2 = mock(Link.Metadata.class);
        var metadata3 = mock(Link.Metadata.class);
        var expected = List.of(
                List.of(metadata1, metadata2),
                List.of(metadata3)
        );

        when(post.galleryData()).thenReturn(galleryData);
        when(post.mediaMetadata()).thenReturn(Map.of(
                mediaId1, metadata1,
                mediaId2, metadata2,
                mediaId3, metadata3
        ));

        var actual = GALLERY_METADATA_PARTITIONER.partition(post);

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnTwoPartitionsForFourMetadata() {
        var mediaId1 = "mediaId1";
        var mediaId2 = "mediaId2";
        var mediaId3 = "mediaId3";
        var mediaId4 = "mediaId4";
        var galleryData = new Link.GalleryData(List.of(
                new Link.GalleryData.Item(mediaId1, null),
                new Link.GalleryData.Item(mediaId2, null),
                new Link.GalleryData.Item(mediaId3, null),
                new Link.GalleryData.Item(mediaId4, null)
        ));
        var metadata1 = mock(Link.Metadata.class);
        var metadata2 = mock(Link.Metadata.class);
        var metadata3 = mock(Link.Metadata.class);
        var metadata4 = mock(Link.Metadata.class);
        var expected = List.of(
                List.of(metadata1, metadata2),
                List.of(metadata3, metadata4)
        );

        when(post.galleryData()).thenReturn(galleryData);
        when(post.mediaMetadata()).thenReturn(Map.of(
                mediaId1, metadata1,
                mediaId2, metadata2,
                mediaId3, metadata3,
                mediaId4, metadata4
        ));

        var actual = GALLERY_METADATA_PARTITIONER.partition(post);

        assertEquals(expected, actual);
    }
}
