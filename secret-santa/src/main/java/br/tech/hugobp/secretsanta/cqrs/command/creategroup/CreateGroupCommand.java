package br.tech.hugobp.secretsanta.cqrs.command.creategroup;

import br.tech.hugobp.cqrs.command.Command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

public class CreateGroupCommand extends Command {

    public CreateGroupCommand(CreateGroupCommandData createGroupCommandData) {
        super(createGroupCommandData);
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateGroupCommandData implements Serializable {
        private String name;
    }

    @Override
    public CreateGroupCommandData getData() {
        return (CreateGroupCommandData) super.getData();
    }
}
