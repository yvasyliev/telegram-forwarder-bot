package io.github.yvasyliev.forwarder.telegram.core.configuration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Core autoconfiguration class for the Telegram Forwarder application.
 */
@AutoConfiguration
@EnableConfigurationProperties({TelegramAdminProperties.class, TelegramMediaProperties.class})
@EntityScan("io.github.yvasyliev.forwarder.telegram.core.entity")
@EnableJpaRepositories("io.github.yvasyliev.forwarder.telegram.core.repository")
@ComponentScan("io.github.yvasyliev.forwarder.telegram.core.service")
public class TelegramForwarderCoreAutoConfiguration {}
