package br.tech.hugobp.secretsanta.configuration;

import br.tech.hugobp.cqrs.postgres.PostgresDatabaseConfig;
import br.tech.hugobp.cqrs.postgres.commandstore.CommandRepository;
import br.tech.hugobp.cqrs.postgres.commandstore.PostgresCommandStore;
import br.tech.hugobp.cqrs.postgres.commandstore.CommandMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandStoreConfiguration {

    @Configuration
    @ConfigurationProperties(prefix = "cqrs.commandstore.postgres")
    @Data
    public static class PostgresCommandStoreConfig {
        private String schema;
        private String table;
        private PostgresDatabaseConfig database;
    };

    @Bean
    public PostgresCommandStore commandStore(PostgresCommandStoreConfig commandStoreConfig, ObjectMapper objectMapper) {
        final CommandMapper commandMapper = new CommandMapper(objectMapper);
        final CommandRepository commandRepository = new CommandRepository(
            commandStoreConfig.getSchema(),
            commandStoreConfig.getTable(),
            commandStoreConfig.getDatabase()
        );

        return new PostgresCommandStore(commandRepository, commandMapper);
    }
}
