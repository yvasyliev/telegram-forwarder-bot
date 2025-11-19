package io.github.yvasyliev.telegramforwarder.bot.service.sender;

import io.github.yvasyliev.telegramforwarder.bot.configuration.TelegramProperties;
import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarder.core.dto.InputMediaDTO;
import io.github.yvasyliev.telegramforwarder.core.util.InputStreamSupplier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaVideo;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedditMediaGroupSenderTest {
    private static final String CAPTION = "Test caption";
    private static final boolean HAS_SPOILER = true;
    @InjectMocks private MediaGroupSender sender;
    @Mock private TelegramProperties telegramProperties;
    @Mock private TelegramClient telegramClient;

    @Test
    void shouldCloseInputStreamWhenIOException() throws IOException {
        var animationFilename = "animation.gif";
        var photoFilename = "photo.jpg";
        var animationInputStream = mock(InputStream.class);
        var animationInputStreamSupplier = mock(InputStreamSupplier.class);
        var photoInputStreamSupplier = mock(InputStreamSupplier.class);
        var animationInputMediaDTO = new InputMediaDTO(
                InputMediaDTO.Type.ANIMATION,
                new InputFileDTO(animationInputStreamSupplier, animationFilename, HAS_SPOILER)
        );
        var photoInputMediaDTO = new InputMediaDTO(
                InputMediaDTO.Type.PHOTO,
                new InputFileDTO(photoInputStreamSupplier, photoFilename, HAS_SPOILER)
        );

        when(animationInputStreamSupplier.get()).thenReturn(animationInputStream);
        when(photoInputStreamSupplier.get()).thenThrow(IOException.class);

        assertThrows(
                IOException.class,
                () -> sender.send(List.of(animationInputMediaDTO, photoInputMediaDTO), CAPTION)
        );

        verify(animationInputStream).close();
    }

    @Nested
    class SendMediaGroupTests {
        private static final long ADMIN_ID = 123L;
        private static final String CAPTION = "Test caption";
        private final List<InputStream> inputStreams = new LinkedList<>();
        private final List<InputMediaDTO> inputMedias = new LinkedList<>();
        private SendMediaGroup sendMediaGroup;

        @BeforeEach
        void setUp() throws IOException {
            var animationFilename = "animation.gif";
            var photoFilename = "photo.jpg";
            var videoFilename = "video.mp4";
            var animationInputStream = createInputStream();
            var photoInputStream = createInputStream();
            var videoInputStream = createInputStream();
            var inputMediaAnimationVideo = InputMediaVideo.builder()
                    .media(animationInputStream, animationFilename)
                    .caption(CAPTION)
                    .hasSpoiler(HAS_SPOILER)
                    .build();
            var inputMediaPhoto = InputMediaPhoto.builder()
                    .media(photoInputStream, photoFilename)
                    .hasSpoiler(HAS_SPOILER)
                    .build();
            var inputMediaVideo = InputMediaVideo.builder()
                    .media(videoInputStream, videoFilename)
                    .hasSpoiler(HAS_SPOILER)
                    .build();
            sendMediaGroup = new SendMediaGroup(
                    String.valueOf(ADMIN_ID),
                    List.of(inputMediaAnimationVideo, inputMediaPhoto, inputMediaVideo)
            );

            inputMedias.add(createInputMediaDTO(InputMediaDTO.Type.ANIMATION, animationInputStream, animationFilename));
            inputMedias.add(createInputMediaDTO(InputMediaDTO.Type.PHOTO, photoInputStream, photoFilename));
            inputMedias.add(createInputMediaDTO(InputMediaDTO.Type.VIDEO, videoInputStream, videoFilename));

            when(telegramProperties.adminId()).thenReturn(ADMIN_ID);
        }

        @AfterEach
        void tearDown() throws IOException {
            for (var inputStream : inputStreams) {
                verify(inputStream).close();
            }
        }

        @Test
        void shouldSendMediaGroup() throws IOException, TelegramApiException {
            var expected = List.of(mock(Message.class), mock(Message.class), mock(Message.class));

            when(telegramClient.execute(sendMediaGroup)).thenReturn(expected);

            var actual = sender.send(inputMedias, CAPTION);

            assertEquals(expected, actual);
        }

        @Test
        void shouldCloseInputStreamsWhenTelegramApiException() throws TelegramApiException {
            when(telegramClient.execute(sendMediaGroup)).thenThrow(TelegramApiException.class);

            assertThrows(TelegramApiException.class, () -> sender.send(inputMedias, CAPTION));
        }

        private InputStream createInputStream() {
            var inputStream = mock(InputStream.class);
            inputStreams.add(inputStream);
            return inputStream;
        }

        private InputMediaDTO createInputMediaDTO(
                InputMediaDTO.Type type,
                InputStream inputStream,
                String filename
        ) throws IOException {
            var fileSupplier = mock(InputStreamSupplier.class);
            var inputFile = new InputFileDTO(fileSupplier, filename, HAS_SPOILER);

            when(fileSupplier.get()).thenReturn(inputStream);

            return new InputMediaDTO(type, inputFile);
        }
    }
}
