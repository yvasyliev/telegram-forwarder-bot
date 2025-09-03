package io.github.yvasyliev.telegramforwarder.bot.service.sender;

import io.github.yvasyliev.telegramforwarder.bot.configuration.TelegramProperties;
import io.github.yvasyliev.telegramforwarder.core.dto.InputMediaDTO;
import io.github.yvasyliev.telegramforwarder.core.service.PostSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaVideo;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for sending media groups to Telegram.
 * This service allows sending multiple media files (photos, videos, animations) in a single message.
 */
@Service
@RequiredArgsConstructor
public class MediaGroupSender implements PostSender<List<InputMediaDTO>, List<Message>> {
    private final TelegramProperties telegramProperties;
    private final TelegramClient telegramClient;

    /**
     * Sends a media group to Telegram.
     *
     * @param inputMedias the list of input media files to send
     * @param caption     the caption for the media group
     * @return a list of messages sent
     * @throws IOException          if there is an error reading the input files
     * @throws TelegramApiException if there is an error sending the media group
     */
    @Override
    public List<Message> send(
            List<InputMediaDTO> inputMedias,
            String caption
    ) throws IOException, TelegramApiException {
        var inputStreams = new ArrayList<InputStream>();

        try {
            var medias = createInputMedias(inputMedias, inputStreams);

            medias.getFirst().setCaption(caption);

            var sendMediaGroup = new SendMediaGroup(telegramProperties.adminId().toString(), medias);

            return telegramClient.execute(sendMediaGroup);
        } finally {
            for (var inputStream : inputStreams) {
                inputStream.close();
            }
        }
    }

    private List<InputMedia> createInputMedias(
            List<InputMediaDTO> inputMedias,
            List<InputStream> inputStreams
    ) throws IOException {
        var medias = new ArrayList<InputMedia>();

        for (var inputMedia : inputMedias) {
            var inputFile = inputMedia.inputFile();
            var inputStream = inputFile.fileSupplier().get();
            inputStreams.add(inputStream);
            medias.add(createInputMedia(
                    inputMedia.type(),
                    inputStream,
                    inputFile.filename(),
                    inputFile.hasSpoiler()
            ));
        }

        return medias;
    }

    private InputMedia createInputMedia(
            InputMediaDTO.Type type,
            InputStream inputStream,
            String filename,
            boolean hasSpoiler
    ) {
        return switch (type) {
            case ANIMATION, VIDEO -> InputMediaVideo.builder()
                    .media(inputStream, filename)
                    .hasSpoiler(hasSpoiler)
                    .build();
            case PHOTO -> InputMediaPhoto.builder()
                    .media(inputStream, filename)
                    .hasSpoiler(hasSpoiler)
                    .build();
        };
    }
}
