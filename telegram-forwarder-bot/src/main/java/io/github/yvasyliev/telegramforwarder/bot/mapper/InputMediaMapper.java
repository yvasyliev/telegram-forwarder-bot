package io.github.yvasyliev.telegramforwarder.bot.mapper;

import io.github.yvasyliev.telegramforwarder.bot.dto.InputStreamInputMediaPhoto;
import io.github.yvasyliev.telegramforwarder.bot.dto.InputStreamInputMediaVideo;
import io.github.yvasyliev.telegramforwarder.core.dto.InputMediaDTO;
import io.github.yvasyliev.telegramforwarder.core.dto.InputMediaPhotoDTO;
import io.github.yvasyliev.telegramforwarder.core.dto.InputMediaVideoDTO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassExhaustiveStrategy;
import org.mapstruct.SubclassMapping;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;

/**
 * {@link InputMedia} mapper.
 */
@Mapper(
        subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION,
        builder = @Builder(disableBuilder = true)
)
public interface InputMediaMapper {
    /**
     * Maps {@link InputMediaDTO} to {@link InputMedia}.
     *
     * @param inputMediaDTO the input media DTO
     * @return the mapped input media
     */
    @SubclassMapping(target = InputStreamInputMediaPhoto.class, source = InputMediaPhotoDTO.class)
    @SubclassMapping(target = InputStreamInputMediaVideo.class, source = InputMediaVideoDTO.class)
    InputMedia map(InputMediaDTO inputMediaDTO);
}
