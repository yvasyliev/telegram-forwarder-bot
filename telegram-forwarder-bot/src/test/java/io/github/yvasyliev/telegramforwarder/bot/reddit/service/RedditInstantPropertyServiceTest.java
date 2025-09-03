package io.github.yvasyliev.telegramforwarder.bot.reddit.service;

import io.github.yvasyliev.telegramforwarder.bot.reddit.entity.RedditInstantProperty;
import io.github.yvasyliev.telegramforwarder.bot.reddit.repository.RedditInstantPropertyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.FieldSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedditInstantPropertyServiceTest {
    @SuppressWarnings("checkstyle:ConstantName")
    private static final Supplier<Stream<Arguments>> testGetLastCreated = () -> {
        var now = Instant.now();

        return Stream.of(arguments(Optional.of(now), now), arguments(Optional.empty(), Instant.EPOCH));
    };
    @InjectMocks private RedditInstantPropertyService service;
    @Mock private RedditInstantPropertyRepository repository;

    @ParameterizedTest
    @FieldSource
    void testGetLastCreated(Optional<Instant> value, Instant expected) {
        when(repository.getValue(RedditInstantProperty.RedditInstantPropertyName.LAST_CREATED)).thenReturn(value);

        var actual = service.getLastCreated();

        assertEquals(expected, actual);
    }

    @Test
    void testSaveLastCreated() {
        var value = Instant.now();
        var instantProperty = new RedditInstantProperty();
        var expected = mock(RedditInstantProperty.class);

        instantProperty.setName(RedditInstantProperty.RedditInstantPropertyName.LAST_CREATED);
        instantProperty.setValue(value);

        when(repository.save(instantProperty)).thenReturn(expected);

        var actual = service.saveLastCreated(value);

        assertEquals(expected, actual);
        verify(repository).save(instantProperty);
    }
}
