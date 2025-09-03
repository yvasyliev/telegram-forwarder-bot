package io.github.yvasyliev.telegramforwarder.bot.reddit.service.forwarder;

import io.github.yvasyliev.telegramforwarder.core.configuration.TelegramMediaProperties;
import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarder.core.dto.InputMediaDTO;
import io.github.yvasyliev.telegramforwarder.bot.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.bot.reddit.util.PhotoUtils;
import io.github.yvasyliev.telegramforwarder.core.service.PostSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MediaGroupForwarderTest {
    private static final boolean HAS_SPOILER = true;
    private static final String CAPTION = "Test Caption";
    private static final int MEDIA_GROUP_MAX_SIZE = 3;
    private static final int MAX_PHOTO_DIMENSIONS = 10_000;
    private final Map<String, Link.Metadata> mediaMetadata = new HashMap<>();
    private final List<Link.GalleryData.Item> items = new LinkedList<>();
    private MediaGroupForwarder forwarder;
    @Mock private TelegramMediaProperties mediaProperties;
    @Mock private MetadataForwarder animationMetadataForwarder;
    @Mock private MetadataForwarder photoMetadataForwarder;
    @Mock private PostSender<List<InputMediaDTO>, List<Message>> mediaGroupSender;
    @Mock private Link link;
    @Mock private URL url;

    @BeforeEach
    void setUp() {
        forwarder = new MediaGroupForwarder(
                mediaProperties,
                animationMetadataForwarder,
                photoMetadataForwarder,
                mediaGroupSender
        );

        when(link.isNsfw()).thenReturn(HAS_SPOILER);
        when(link.title()).thenReturn(CAPTION);
        when(link.galleryData()).thenReturn(new Link.GalleryData(items));
        when(link.mediaMetadata()).thenReturn(mediaMetadata);
        when(mediaProperties.mediaGroupMaxSize()).thenReturn(MEDIA_GROUP_MAX_SIZE);
    }

    @Test
    void shouldSendTwoMediaGroups() throws TelegramApiException, IOException {
        var inputFile = mock(InputFileDTO.class);
        var inputMediaAnimation = InputMediaDTO.animation(inputFile);
        var inputMediaPhoto = InputMediaDTO.photo(inputFile);
        var inputMedias1 = List.of(inputMediaAnimation, inputMediaAnimation, inputMediaAnimation);
        var inputMedias2 = List.of(inputMediaPhoto, inputMediaPhoto);

        mockGifUrl(registerItem(Link.Metadata.Type.ANIMATED_IMAGE));
        mockGifUrl(registerItem(Link.Metadata.Type.ANIMATED_IMAGE));
        mockGifUrl(registerItem(Link.Metadata.Type.ANIMATED_IMAGE));

        var photoMetadata1 = registerItem(Link.Metadata.Type.IMAGE);
        var photoMetadata2 = registerItem(Link.Metadata.Type.IMAGE);

        when(mediaProperties.maxPhotoDimensions()).thenReturn(MAX_PHOTO_DIMENSIONS);

        try (var photoUtils = mockStatic(PhotoUtils.class); var inputFileDTO = mockStatic(InputFileDTO.class)) {
            photoUtils.when(() -> PhotoUtils.getUrl(photoMetadata1, MAX_PHOTO_DIMENSIONS)).thenReturn(url);
            photoUtils.when(() -> PhotoUtils.getUrl(photoMetadata2, MAX_PHOTO_DIMENSIONS)).thenReturn(url);
            inputFileDTO.when(() -> InputFileDTO.fromUrl(url, HAS_SPOILER)).thenReturn(inputFile);

            forwarder.forward(link);
        }

        verify(mediaGroupSender).send(inputMedias1, CAPTION);
        verify(mediaGroupSender).send(inputMedias2, null);
    }

    @Test
    void shouldSendSingleAnimation() throws TelegramApiException, IOException {
        var animationMetadata = registerItem(Link.Metadata.Type.ANIMATED_IMAGE);

        forwarder.forward(link);

        verify(animationMetadataForwarder).forward(animationMetadata, HAS_SPOILER);
    }

    @Test
    void shouldSendSinglePhoto() throws TelegramApiException, IOException {
        var photoMetadata = registerItem(Link.Metadata.Type.IMAGE);

        forwarder.forward(link);

        verify(photoMetadataForwarder).forward(photoMetadata, HAS_SPOILER);
    }

    private Link.Metadata registerItem(Link.Metadata.Type type) {
        var mediaId = UUID.randomUUID().toString();
        var metadata = mock(Link.Metadata.class);
        var item = mock(Link.GalleryData.Item.class);

        mediaMetadata.put(mediaId, metadata);
        items.add(item);

        when(metadata.type()).thenReturn(type);
        when(item.mediaId()).thenReturn(mediaId);

        return metadata;
    }

    private void mockGifUrl(Link.Metadata animationMetadata) {
        var source = mock(Link.Resolution.class);

        when(animationMetadata.source()).thenReturn(source);
        when(source.gif()).thenReturn(url);
    }
}
