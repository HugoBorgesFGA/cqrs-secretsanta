package br.tech.hugobp.secretsanta.cqrs.command.performdraw;

import br.tech.hugobp.cqrs.command.CommandHandler;
import br.tech.hugobp.cqrs.event.EventPublisher;
import br.tech.hugobp.secretsanta.cqrs.event.ParticipantPickedEvent;
import br.tech.hugobp.secretsanta.cqrs.projections.GroupProjection;
import br.tech.hugobp.secretsanta.domain.draw.Draw;
import br.tech.hugobp.secretsanta.domain.group.Group;
import br.tech.hugobp.secretsanta.domain.group.GroupAlreadyDrawnException;
import br.tech.hugobp.secretsanta.domain.group.GroupNotFoundException;
import br.tech.hugobp.secretsanta.domain.participant.Participant;

import java.util.Map;
import java.util.Objects;

public class PerformDrawHandler extends CommandHandler<PerformDrawCommand> {

    private final GroupProjection groupProjection;

    public PerformDrawHandler(GroupProjection groupProjection) {
        super(PerformDrawCommand.class);
        this.groupProjection = groupProjection;
    }

    @Override
    public void process(PerformDrawCommand command, EventPublisher eventPublisher) {
        final String groupId = command.getEntityId();

        final Group group = groupProjection.get(groupId);
        if (Objects.isNull(group)) {
            throw new GroupNotFoundException(groupId);
        }

        if (group.wasDrawn()) {
            throw new GroupAlreadyDrawnException(group);
        }

        final Map<Participant, Participant> secretSantaResult = Draw.shuffle(group.getParticipants());
        group.setDrawnResult(secretSantaResult);

        secretSantaResult.forEach((participant, picked) -> eventPublisher.publish(
            new ParticipantPickedEvent(
                groupId,
                participant,
                picked
            )
        ));
    }
}
