package br.tech.hugobp.secretsanta.entrypoints.commands.dtos.joingroup;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JoinGroupResponse {
    private String participantId;
}
