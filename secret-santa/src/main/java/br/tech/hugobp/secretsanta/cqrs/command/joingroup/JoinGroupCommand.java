package br.tech.hugobp.secretsanta.cqrs.command.joingroup;

import br.tech.hugobp.cqrs.command.Command;
import lombok.*;

import java.io.Serializable;

@Getter
public class JoinGroupCommand extends Command {

    public JoinGroupCommand(String groupId, JoinGroupData joinGroupData) {
        super(joinGroupData, groupId);
    }

    @Override
    public JoinGroupData getData() {
        return (JoinGroupData) super.getData();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinGroupData implements Serializable {
        private String fullName;
        private String email;
    }
}
