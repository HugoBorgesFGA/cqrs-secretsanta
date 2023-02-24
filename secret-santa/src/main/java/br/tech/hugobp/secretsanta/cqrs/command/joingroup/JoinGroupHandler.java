package br.tech.hugobp.secretsanta.cqrs.command.joingroup;

import br.tech.hugobp.cqrs.command.CommandHandler;
import br.tech.hugobp.cqrs.event.EventPublisher;
import br.tech.hugobp.secretsanta.cqrs.event.ParticipantJoinedGroupEvent;
import br.tech.hugobp.secretsanta.cqrs.projections.GroupProjection;
import br.tech.hugobp.secretsanta.domain.participant.Participant;
import br.tech.hugobp.secretsanta.domain.email.Email;
import br.tech.hugobp.secretsanta.domain.group.Group;
import br.tech.hugobp.secretsanta.domain.group.GroupNotFoundException;
import br.tech.hugobp.secretsanta.domain.participant.ParticipantAlreadyInGroupException;

import java.util.Objects;

public class JoinGroupHandler extends CommandHandler<JoinGroupCommand> {

    private final GroupProjection groupProjection;

    public JoinGroupHandler(GroupProjection groupProjection) {
        super(JoinGroupCommand.class);
        this.groupProjection = groupProjection;
    }

    @Override
    public void process(JoinGroupCommand command, EventPublisher eventPublisher) {

        final String groupId = command.getEntityId();
        final JoinGroupCommand.JoinGroupData commandJoinGroupData = command.getData();

        final Group group = groupProjection.get(groupId);
        if (Objects.isNull(group)) {
            throw new GroupNotFoundException(groupId);
        }

        final String joinerId = command.getId();
        final Participant newJoiner = new Participant(
            joinerId,
            commandJoinGroupData.getFullName(),
            new Email(commandJoinGroupData.getEmail())
        );

        if (group.hasParticipant(newJoiner)) {
            throw new ParticipantAlreadyInGroupException(newJoiner, group);
        }

        group.addParticipant(newJoiner);

        eventPublisher.publish(
            new ParticipantJoinedGroupEvent(ParticipantJoinedGroupEvent.JoinedGroupData.builder()
                .id(joinerId)
                .name(commandJoinGroupData.getFullName())
                .email(commandJoinGroupData.getEmail())
                .build(),
                groupId
            )
        );
    }
}
