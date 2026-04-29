package io.github.yvasyliev.forwarder.telegram.core.configuration;

import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * JPA configuration class for the Telegram Forwarder application, enabling JPA repositories and scanning for entity
 * classes.
 */
@Configuration
@EnableJpaRepositories("io.github.yvasyliev.forwarder.telegram.core.repository")
@EntityScan("io.github.yvasyliev.forwarder.telegram.core.entity")
public class TelegramForwarderCoreJpaConfiguration {}
