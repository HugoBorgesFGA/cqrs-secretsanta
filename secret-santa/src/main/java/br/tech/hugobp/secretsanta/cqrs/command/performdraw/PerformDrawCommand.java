package br.tech.hugobp.secretsanta.cqrs.command.performdraw;

import br.tech.hugobp.cqrs.command.Command;
import lombok.Getter;

@Getter
public class PerformDrawCommand extends Command {
    public PerformDrawCommand(String groupId) {
        super(null, groupId);
    }
}
