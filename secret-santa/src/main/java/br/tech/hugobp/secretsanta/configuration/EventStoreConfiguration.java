package br.tech.hugobp.secretsanta.configuration;

import br.tech.hugobp.cqrs.postgres.PostgresDatabaseConfig;
import br.tech.hugobp.cqrs.postgres.eventstore.EventMapper;
import br.tech.hugobp.cqrs.postgres.eventstore.EventRepository;
import br.tech.hugobp.cqrs.postgres.eventstore.PostgresEventStore;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventStoreConfiguration {

    @Configuration
    @ConfigurationProperties(prefix = "cqrs.eventstore.postgres")
    @Data
    public static class PostgresEventStoreConfig {
        private String schema;
        private String table;
        private PostgresDatabaseConfig database;
    };

    @Bean
    public EventMapper eventMapper(ObjectMapper objectMapper) {
        return new EventMapper(objectMapper);
    }

    @Bean
    public PostgresEventStore eventStore(PostgresEventStoreConfig eventStoreConfig, EventMapper eventMapper) {
        final EventRepository eventRepository = new EventRepository(
            eventStoreConfig.getSchema(),
            eventStoreConfig.getTable(),
            eventStoreConfig.getDatabase()
        );

        return new PostgresEventStore(eventRepository, eventMapper);
    }
}
