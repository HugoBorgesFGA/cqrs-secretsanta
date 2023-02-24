package br.tech.hugobp.secretsanta.cqrs.event;

import br.tech.hugobp.cqrs.event.Event;
import br.tech.hugobp.secretsanta.cqrs.command.creategroup.CreateGroupCommand;
import br.tech.hugobp.secretsanta.cqrs.command.creategroup.CreateGroupCommand.CreateGroupCommandData;
import lombok.Getter;

@Getter
public class GroupCreatedEvent extends Event {

    public GroupCreatedEvent(CreateGroupCommand command) {
        super(command);
    }

    @Override
    public CreateGroupCommandData getData() {
        return (CreateGroupCommandData) super.getData();
    }
}
