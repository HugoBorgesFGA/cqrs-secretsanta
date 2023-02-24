package br.tech.hugobp.secretsanta.entrypoints.commands.dtos.leavegroup;

import lombok.Data;

@Data
public class LeaveGroupRequest {
    private String groupId;
    private String participantId;
}
