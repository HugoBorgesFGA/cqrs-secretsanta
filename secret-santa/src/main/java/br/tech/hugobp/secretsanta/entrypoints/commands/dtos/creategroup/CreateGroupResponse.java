package br.tech.hugobp.secretsanta.entrypoints.commands.dtos.creategroup;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateGroupResponse {
    private String groupId;
}
