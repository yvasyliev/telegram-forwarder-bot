package io.github.yvasyliev.telegramforwarder.bot.mapper;

import io.github.yvasyliev.telegramforwarder.bot.dto.InputStreamInputFile;
import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.telegram.telegrambots.meta.api.objects.InputFile;

/**
 * {@link InputFile} mapper.
 */
@Mapper
public interface InputFileMapper {
    /**
     * Maps {@link InputFileDTO} to {@link InputFile}.
     *
     * @param inputFile the input file DTO
     * @return the mapped input file
     */
    @BeanMapping(resultType = InputStreamInputFile.class)
    InputFile map(InputFileDTO inputFile);
}
