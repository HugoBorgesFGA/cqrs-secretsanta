package br.tech.hugobp.secretsanta.configuration;

import br.tech.hugobp.cqrs.command.CommandExecutor;
import br.tech.hugobp.cqrs.command.CommandHandler;
import br.tech.hugobp.cqrs.command.CommandHandlerRegistry;
import br.tech.hugobp.cqrs.command.CommandStore;
import br.tech.hugobp.cqrs.event.EventPublisher;
import br.tech.hugobp.cqrs.event.EventStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
@Slf4j
public class TopologyConfiguration {

    @Bean
    public EventPublisher eventPublisher(EventStore eventStore) {
        return event -> {
            eventStore.store(event);
            log.info("Publishing event " + event.getId());
        };
    }

    @Bean
    public CommandExecutor commandPublisher(
        Set<CommandHandler<?>> handlers,
        CommandStore commandStore,
        EventPublisher eventPublisher
    ) {
        return new CommandExecutor(
            new CommandHandlerRegistry(handlers),
            commandStore,
            eventPublisher
        );
    }
}
