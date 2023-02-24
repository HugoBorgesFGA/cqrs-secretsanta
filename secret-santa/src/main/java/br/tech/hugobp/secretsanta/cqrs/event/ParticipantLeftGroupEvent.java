package br.tech.hugobp.secretsanta.cqrs.event;

import br.tech.hugobp.cqrs.event.Event;
import br.tech.hugobp.secretsanta.cqrs.command.leavegroup.LeaveGroupCommand;
import br.tech.hugobp.secretsanta.cqrs.command.leavegroup.LeaveGroupCommand.LeaveGroupData;
import lombok.Getter;

@Getter
public class ParticipantLeftGroupEvent extends Event {

    public ParticipantLeftGroupEvent(LeaveGroupCommand cmd) {
        super(cmd);
    }

    @Override
    public LeaveGroupData getData() {
        return (LeaveGroupData) super.getData();
    }
}
