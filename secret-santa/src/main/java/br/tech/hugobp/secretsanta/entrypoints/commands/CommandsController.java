package br.tech.hugobp.secretsanta.entrypoints.commands;

import br.tech.hugobp.cqrs.command.CommandExecutor;
import br.tech.hugobp.secretsanta.cqrs.command.creategroup.CreateGroupCommand;
import br.tech.hugobp.secretsanta.cqrs.command.joingroup.JoinGroupCommand;
import br.tech.hugobp.secretsanta.cqrs.command.leavegroup.LeaveGroupCommand;
import br.tech.hugobp.secretsanta.cqrs.command.performdraw.PerformDrawCommand;
import br.tech.hugobp.secretsanta.entrypoints.commands.dtos.CommandMapper;
import br.tech.hugobp.secretsanta.entrypoints.commands.dtos.creategroup.CreateGroupRequest;
import br.tech.hugobp.secretsanta.entrypoints.commands.dtos.creategroup.CreateGroupResponse;
import br.tech.hugobp.secretsanta.entrypoints.commands.dtos.joingroup.JoinGroupRequest;
import br.tech.hugobp.secretsanta.entrypoints.commands.dtos.joingroup.JoinGroupResponse;
import br.tech.hugobp.secretsanta.entrypoints.commands.dtos.leavegroup.LeaveGroupRequest;
import br.tech.hugobp.secretsanta.entrypoints.commands.dtos.performdraw.PerformDrawRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/commands")
@RequiredArgsConstructor
public class CommandsController {

    private final CommandExecutor commandExecutor;
    private final CommandMapper commandMapper;

    @PostMapping("/createGroup")
    private Mono<CreateGroupResponse> createGroup(@RequestBody CreateGroupRequest body) {

        final CreateGroupCommand cmd = commandMapper.createGroupCommand(body);
        commandExecutor.execute(cmd);

        final CreateGroupResponse response = commandMapper.createGroupResponse(cmd);
        return Mono.just(response);
    }

    @PostMapping("/joinGroup")
    private Mono<JoinGroupResponse> joinGroup(@RequestBody JoinGroupRequest body) {

        final JoinGroupCommand cmd = commandMapper.joinGroupCommand(body);
        commandExecutor.execute(cmd);

        final JoinGroupResponse response = commandMapper.joinGroupResponse(cmd);
        return Mono.just(response);
    }

    @PostMapping("/leaveGroup")
    private Mono<Void> leaveGroup(@RequestBody LeaveGroupRequest body) {

        final LeaveGroupCommand cmd = commandMapper.leaveGroupCommand(body);
        commandExecutor.execute(cmd);

        return Mono.empty();
    }

    @PostMapping("/performDraw")
    private Mono<Void> performDraw(@RequestBody PerformDrawRequest body) {

        final PerformDrawCommand cmd = commandMapper.performDraw(body);
        commandExecutor.execute(cmd);

        return Mono.empty();
    }
}
