package br.tech.hugobp.secretsanta.cqrs.command.leavegroup;

import br.tech.hugobp.cqrs.command.Command;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

public class LeaveGroupCommand extends Command {
    public LeaveGroupCommand(String groupId, LeaveGroupData data) {
        super(data, groupId);
    }

    @Override
    public LeaveGroupData getData() {
        return (LeaveGroupData) super.getData();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LeaveGroupData implements Serializable {
        private String participantId;
    }
}
