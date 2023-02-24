package br.tech.hugobp.secretsanta.cqrs.command.creategroup;

import br.tech.hugobp.cqrs.command.CommandHandler;
import br.tech.hugobp.cqrs.event.EventPublisher;
import br.tech.hugobp.secretsanta.cqrs.event.GroupCreatedEvent;
import br.tech.hugobp.secretsanta.cqrs.projections.GroupProjection;
import br.tech.hugobp.secretsanta.domain.group.Group;

public class CreateGroupHandler extends CommandHandler<CreateGroupCommand> {

    private final GroupProjection groupProjection;

    public CreateGroupHandler(GroupProjection groupProjection) {
        super(CreateGroupCommand.class);
        this.groupProjection = groupProjection;
    }

    @Override
    public void process(CreateGroupCommand command, EventPublisher eventPublisher) {
        final String groupId = command.getEntityId();
        if (groupProjection.get(groupId) != null) {
            return;
        }

        final Group group = new Group(
            groupId,
            command.getData().getName()
        );

        eventPublisher.publish(new GroupCreatedEvent(command));
    }
}
