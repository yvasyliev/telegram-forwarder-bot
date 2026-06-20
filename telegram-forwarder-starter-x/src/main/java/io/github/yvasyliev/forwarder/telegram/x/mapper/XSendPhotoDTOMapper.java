package io.github.yvasyliev.forwarder.telegram.x.mapper;

import io.github.yvasyliev.forwarder.telegram.core.dto.SendPhotoDTO;
import io.github.yvasyliev.forwarder.telegram.core.util.InputFileDTOConverter;
import io.github.yvasyliev.forwarder.telegram.x.dto.XDescription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.io.IOException;

/**
 * {@link SendPhotoDTO} mapper for X posts.
 */
@Mapper(uses = InputFileDTOConverter.class)
public interface XSendPhotoDTOMapper {
    /**
     * Maps an {@link XDescription} to a {@link SendPhotoDTO}.
     *
     * @param description the X post description to map
     * @return the mapped {@link SendPhotoDTO}
     * @throws IOException if an I/O error occurs during mapping
     */
    @Mapping(target = "photo", source = "images.first")
    @Mapping(target = "caption", source = "title")
    SendPhotoDTO map(XDescription description) throws IOException;
}
