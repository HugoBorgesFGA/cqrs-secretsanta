package br.tech.hugobp.secretsanta.domain.participant;

import br.tech.hugobp.secretsanta.domain.group.Group;

public class ParticipantAlreadyInGroupException extends RuntimeException {
    public ParticipantAlreadyInGroupException(Participant participant, Group group) {
        super(String.format("Participant (name = %s, email = %s) already registered in group %s",
            participant.getName(),
            participant.getEmail(),
            group.getId()
        ));
    }
}
