package br.tech.hugobp.secretsanta.entrypoints.commands.dtos;

import br.tech.hugobp.secretsanta.cqrs.command.creategroup.CreateGroupCommand;
import br.tech.hugobp.secretsanta.cqrs.command.creategroup.CreateGroupCommand.CreateGroupCommandData;
import br.tech.hugobp.secretsanta.cqrs.command.joingroup.JoinGroupCommand;
import br.tech.hugobp.secretsanta.cqrs.command.joingroup.JoinGroupCommand.JoinGroupData;
import br.tech.hugobp.secretsanta.cqrs.command.leavegroup.LeaveGroupCommand;
import br.tech.hugobp.secretsanta.cqrs.command.performdraw.PerformDrawCommand;
import br.tech.hugobp.secretsanta.entrypoints.commands.dtos.creategroup.CreateGroupRequest;
import br.tech.hugobp.secretsanta.entrypoints.commands.dtos.creategroup.CreateGroupResponse;
import br.tech.hugobp.secretsanta.entrypoints.commands.dtos.joingroup.JoinGroupRequest;
import br.tech.hugobp.secretsanta.entrypoints.commands.dtos.joingroup.JoinGroupResponse;
import br.tech.hugobp.secretsanta.entrypoints.commands.dtos.leavegroup.LeaveGroupRequest;
import br.tech.hugobp.secretsanta.entrypoints.commands.dtos.performdraw.PerformDrawRequest;
import org.springframework.stereotype.Component;

@Component
public class CommandMapper {

    // Create Group
    public CreateGroupCommand createGroupCommand(CreateGroupRequest body) {
        return new CreateGroupCommand(
            CreateGroupCommandData.builder()
                .name(body.getName())
                .build()
        );
    }

    public CreateGroupResponse createGroupResponse(CreateGroupCommand cmd) {
        return CreateGroupResponse.builder()
            .groupId(cmd.getEntityId())
            .build();
    }

    // Join Group
    public JoinGroupCommand joinGroupCommand(JoinGroupRequest body) {
        return new JoinGroupCommand(
            body.getGroupId(),
            JoinGroupData.builder()
                .fullName(body.getFullName())
                .email(body.getEmail())
                .build()
        );
    }

    public JoinGroupResponse joinGroupResponse(JoinGroupCommand cmd) {
        return JoinGroupResponse.builder()
            .participantId(cmd.getId())
            .build();
    }

    // Leave Group
    public LeaveGroupCommand leaveGroupCommand(LeaveGroupRequest body) {
        return new LeaveGroupCommand(
            body.getGroupId(),
            LeaveGroupCommand.LeaveGroupData.builder()
                .participantId(body.getParticipantId())
                .build()
        );
    }

    // Perform Draw
    public PerformDrawCommand performDraw(PerformDrawRequest body) {
        return new PerformDrawCommand(
            body.getGroupId()
        );
    }
}
