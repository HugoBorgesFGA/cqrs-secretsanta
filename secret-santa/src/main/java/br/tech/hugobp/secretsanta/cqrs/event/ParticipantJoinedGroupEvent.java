package br.tech.hugobp.secretsanta.cqrs.event;

import br.tech.hugobp.cqrs.event.Event;
import br.tech.hugobp.secretsanta.cqrs.command.joingroup.JoinGroupCommand;
import br.tech.hugobp.secretsanta.cqrs.command.joingroup.JoinGroupCommand.JoinGroupData;
import lombok.*;

import java.io.Serializable;

@Getter
public class ParticipantJoinedGroupEvent extends Event {

    public ParticipantJoinedGroupEvent(JoinedGroupData data, String entityId) {
        super(data, entityId);
    }

    @Override
    public JoinedGroupData getData() {
        return (JoinedGroupData) super.getData();
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class JoinedGroupData implements Serializable {
        private String id;
        private String name;
        private String email;
    }
}
