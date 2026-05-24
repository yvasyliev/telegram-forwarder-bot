package io.github.yvasyliev.forwarder.telegram.x.mapper;

import io.github.yvasyliev.forwarder.telegram.core.dto.SendMediaGroupDTO;
import io.github.yvasyliev.forwarder.telegram.core.util.InputMediaPhotoDTOConverter;
import io.github.yvasyliev.forwarder.telegram.x.dto.XDescription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.io.IOException;

/**
 * {@link SendMediaGroupDTO} mapper for X posts.
 */
@Mapper(uses = InputMediaPhotoDTOConverter.class)
public interface XSendMediaGroupDTOMapper {
    /**
     * Maps an {@link XDescription} to a {@link SendMediaGroupDTO}.
     *
     * @param description the X post description containing media information
     * @return the mapped {@link SendMediaGroupDTO} with media and caption
     * @throws IOException if an I/O error occurs during mapping
     */
    @Mapping(target = "medias", source = "images")
    @Mapping(target = "caption", source = "title")
    SendMediaGroupDTO map(XDescription description) throws IOException;
}
