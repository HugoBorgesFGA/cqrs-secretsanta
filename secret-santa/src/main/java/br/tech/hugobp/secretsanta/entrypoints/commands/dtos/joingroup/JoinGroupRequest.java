package br.tech.hugobp.secretsanta.entrypoints.commands.dtos.joingroup;

import lombok.Data;

@Data
public class JoinGroupRequest {
    private String groupId;
    private String fullName;
    private String email;
}
