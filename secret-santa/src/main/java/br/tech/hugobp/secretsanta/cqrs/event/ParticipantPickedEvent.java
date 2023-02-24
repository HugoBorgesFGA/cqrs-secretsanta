package br.tech.hugobp.secretsanta.cqrs.event;

import br.tech.hugobp.cqrs.event.Event;
import br.tech.hugobp.secretsanta.domain.participant.Participant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

public class ParticipantPickedEvent extends Event {

    public ParticipantPickedEvent(String entityId, Participant participant, Participant picked) {
        super(
            Payload.builder()
                .participant(ParticipantDto.from(participant))
                .picked(ParticipantDto.from(picked))
                .build(),
            entityId
        );
    }

    @Override
    public Payload getData() {
        return (Payload) super.getData();
    }

    @Data
    @Builder
    public static class Payload implements Serializable {
        private ParticipantDto participant;
        private ParticipantDto picked;
    }

    @Getter
    @AllArgsConstructor
    public static class ParticipantDto {
        private final String id;
        private final String name;
        private final String email;

        public static ParticipantDto from(Participant participant) {
            return new ParticipantDto(
                participant.getId(),
                participant.getName(),
                participant.getEmail().toString()
            );
        }
    }
}
