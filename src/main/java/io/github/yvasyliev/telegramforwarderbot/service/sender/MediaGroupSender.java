package io.github.yvasyliev.telegramforwarderbot.service.sender;

import io.github.yvasyliev.telegramforwarderbot.configuration.TelegramProperties;
import io.github.yvasyliev.telegramforwarderbot.dto.InputMediaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaAnimation;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaVideo;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MediaGroupSender {
    private final TelegramProperties telegramProperties;
    private final TelegramClient telegramClient;

    public List<Message> sendMediaGroup(
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
            case ANIMATION -> InputMediaAnimation.builder()
                    .media(inputStream, filename)
                    .hasSpoiler(hasSpoiler)
                    .build();
            case PHOTO -> InputMediaPhoto.builder()
                    .media(inputStream, filename)
                    .hasSpoiler(hasSpoiler)
                    .build();
            case VIDEO -> InputMediaVideo.builder()
                    .media(inputStream, filename)
                    .hasSpoiler(hasSpoiler)
                    .build();
        };
    }
}
