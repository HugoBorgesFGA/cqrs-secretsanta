package br.tech.hugobp.secretsanta.domain.group;

import br.tech.hugobp.cqrs.Entity;
import br.tech.hugobp.secretsanta.domain.participant.Participant;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class Group extends Entity {

    private final String name;
    private Set<Participant> participants;
    private Map<Participant, Participant> pickedBy;

    public Group(String id, String name) {
        super(id);
        this.name = name;
        this.participants = new LinkedHashSet<>();
        this.pickedBy = new HashMap<>();
    }

    public void addParticipant(Participant participant) {
        this.participants.add(participant);
    }

    public void removeParticipant(Participant participant) {
        this.participants = this.participants.stream()
            .filter(entry -> !entry.equals(participant))
            .collect(Collectors.toSet());
    }

    public boolean wasDrawn() {
        return !participants.isEmpty() &&
            Objects.nonNull(pickedBy) &&
            pickedBy.keySet().containsAll(participants);
    }

    public boolean hasParticipant(Participant participant) {
        return this.participants.stream().anyMatch(entry -> entry.equals(participant));
    }

    public void setDrawnResult(Map<Participant, Participant> pairs) {
        this.pickedBy = pairs;
    }
}
