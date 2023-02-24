package br.tech.hugobp.secretsanta.configuration;

import br.tech.hugobp.cqrs.event.EventStore;
import br.tech.hugobp.secretsanta.cqrs.command.creategroup.CreateGroupHandler;
import br.tech.hugobp.secretsanta.cqrs.command.joingroup.JoinGroupHandler;
import br.tech.hugobp.secretsanta.cqrs.command.leavegroup.LeaveGroupHandler;
import br.tech.hugobp.secretsanta.cqrs.command.performdraw.PerformDrawHandler;
import br.tech.hugobp.secretsanta.cqrs.projections.GroupProjection;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandHandlersConfiguration {

    public GroupProjection groupProjection(EventStore eventStore, ObjectMapper objectMapper) {
        return new GroupProjection(eventStore, objectMapper);
    }

    @Bean
    public CreateGroupHandler createGroupHandler(GroupProjection groupProjection) {
        return new CreateGroupHandler(groupProjection);
    }

    @Bean
    public JoinGroupHandler joinGroupHandler(GroupProjection groupProjection) {
        return new JoinGroupHandler(groupProjection);
    }

    @Bean
    public LeaveGroupHandler leaveGroupHandler(GroupProjection groupProjection) {
        return new LeaveGroupHandler(groupProjection);
    }

    @Bean
    public PerformDrawHandler performDrawHandler(GroupProjection groupProjection) {
        return new PerformDrawHandler(groupProjection);
    }
}
