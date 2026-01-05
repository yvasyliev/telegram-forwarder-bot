package io.github.yvasyliev.telegramforwarder.bot.mapper;

import io.github.yvasyliev.telegramforwarder.bot.configuration.TelegramBotProperties;
import io.github.yvasyliev.telegramforwarder.bot.dto.BotDTO;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.FieldSource;
import org.mapstruct.factory.Mappers;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class BotDTOMapperTest {
    private static final String NAME = "name";
    private static final String VERSION = "version";
    private static final BotDTOMapper BOT_DTO_MAPPER = Mappers.getMapper(BotDTOMapper.class);

    @SuppressWarnings("checkstyle:ConstantName")
    private static final Supplier<Stream<Arguments>> testMap = () -> {
        var bot = new User(1L, NAME, true);
        var botProperties = new TelegramBotProperties();

        botProperties.setVersion(VERSION);

        var expected1 = new BotDTO(null, VERSION);
        var expected2 = new BotDTO(NAME, null);
        var expected3 = new BotDTO(NAME, VERSION);

        return Stream.of(
                arguments(null, null, null),
                arguments(null, botProperties, expected1),
                arguments(bot, null, expected2),
                arguments(bot, botProperties, expected3)
        );
    };

    @ParameterizedTest
    @FieldSource
    void testMap(User bot, TelegramBotProperties botProperties, BotDTO expected) {
        var actual = BOT_DTO_MAPPER.map(bot, botProperties);

        assertEquals(expected, actual);
    }
}
