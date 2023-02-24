package br.tech.hugobp.secretsanta.cqrs.command.leavegroup;

import br.tech.hugobp.cqrs.command.CommandHandler;
import br.tech.hugobp.cqrs.event.EventPublisher;
import br.tech.hugobp.secretsanta.cqrs.event.ParticipantLeftGroupEvent;
import br.tech.hugobp.secretsanta.cqrs.projections.GroupProjection;
import br.tech.hugobp.secretsanta.domain.participant.Participant;
import br.tech.hugobp.secretsanta.domain.group.Group;
import br.tech.hugobp.secretsanta.domain.group.GroupNotFoundException;
import br.tech.hugobp.secretsanta.domain.participant.ParticipantNotFoundException;

import java.util.Objects;
import java.util.Optional;

public class LeaveGroupHandler extends CommandHandler<LeaveGroupCommand> {

    private final GroupProjection groupProjection;

    public LeaveGroupHandler(GroupProjection groupProjection) {
        super(LeaveGroupCommand.class);
        this.groupProjection = groupProjection;
    }

    @Override
    public void process(LeaveGroupCommand command, EventPublisher eventPublisher) {

        final String groupId = command.getEntityId();

        final Group group = groupProjection.get(groupId);
        if (Objects.isNull(group)) {
            throw new GroupNotFoundException(groupId);
        }

        final String leavingParticipantId = command.getData().getParticipantId();
        final Optional<Participant> leavingParticipant = group.getParticipants().stream()
            .filter(participant -> participant.getId().equals(leavingParticipantId))
            .findFirst();

        if (leavingParticipant.isEmpty()) {
            throw new ParticipantNotFoundException(groupId, leavingParticipantId);
        }

        leavingParticipant.ifPresent(group::removeParticipant);

        eventPublisher.publish(new ParticipantLeftGroupEvent(command));
    }
}
