package br.tech.hugobp.secretsanta.cqrs.projections;

import br.tech.hugobp.cqrs.event.Event;
import br.tech.hugobp.cqrs.event.EventStore;
import br.tech.hugobp.cqrs.event.Projection;

import br.tech.hugobp.secretsanta.cqrs.command.creategroup.CreateGroupCommand.CreateGroupCommandData;
import br.tech.hugobp.secretsanta.cqrs.command.leavegroup.LeaveGroupCommand.LeaveGroupData;
import br.tech.hugobp.secretsanta.cqrs.event.GroupCreatedEvent;
import br.tech.hugobp.secretsanta.cqrs.event.ParticipantJoinedGroupEvent;
import br.tech.hugobp.secretsanta.cqrs.event.ParticipantJoinedGroupEvent.JoinedGroupData;
import br.tech.hugobp.secretsanta.cqrs.event.ParticipantLeftGroupEvent;
import br.tech.hugobp.secretsanta.domain.email.Email;
import br.tech.hugobp.secretsanta.domain.group.Group;
import br.tech.hugobp.secretsanta.domain.participant.Participant;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;

@Component
public class GroupProjection extends Projection<Group> {

    final Map<String, BiFunction<Event, Group, Group>> eventHandlers = Map.of(
        Event.createMessageName(GroupCreatedEvent.class), (event, group) -> handleGroupCreated(event, group),
        Event.createMessageName(ParticipantJoinedGroupEvent.class), (event, group) -> handleParticipantJoinedGroup(event, group),
        Event.createMessageName(ParticipantLeftGroupEvent.class), (event, group) -> handleParticipantLeftGroup(event, group)
    );
    private final ObjectMapper objectMapper;

    public GroupProjection(EventStore eventStore, ObjectMapper objectMapper) {
        super(eventStore);
        this.objectMapper = objectMapper;
    }

    @Override
    protected Group handleEvent(Group group, Event event) {
        return Optional.ofNullable(eventHandlers.get(event.getName()))
            .map(function -> function.apply(event, group))
            .orElse(group);
    }

    @SneakyThrows
    private Group handleGroupCreated(Event event, Group group) {
        if (Objects.nonNull(group)) {
            throw new RuntimeException("Group was created twice");
        }

        final CreateGroupCommandData data = objectMapper.readValue((String) event.getData(), CreateGroupCommandData.class);
        return new Group(event.getEntityId(), data.getName());
    }

    @SneakyThrows
    private Group handleParticipantJoinedGroup(Event event, Group group) {
        if (Objects.isNull(group)) {
            throw new RuntimeException("Group was not created");
        }

        final JoinedGroupData data = objectMapper.readValue((String) event.getData(), JoinedGroupData.class);
        final Participant participant = new Participant(data.getId(), data.getName(), new Email(data.getEmail()));
        group.addParticipant(participant);

        return group;
    }

    @SneakyThrows
    private Group handleParticipantLeftGroup(Event event, Group group) {
        if (Objects.isNull(group)) {
            throw new RuntimeException("Group was not created");
        }

        final LeaveGroupData data = objectMapper.readValue((String) event.getData(), LeaveGroupData.class);
        final Participant toBeRemoved = group.getParticipants().stream()
            .filter(participant -> data.getParticipantId().equals(participant.getId()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No Participant with id: " + data.getParticipantId()));

        group.removeParticipant(toBeRemoved);
        return group;
    }
}
