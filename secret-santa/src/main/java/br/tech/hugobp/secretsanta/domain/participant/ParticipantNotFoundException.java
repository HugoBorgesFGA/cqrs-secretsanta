package br.tech.hugobp.secretsanta.domain.participant;

public class ParticipantNotFoundException extends RuntimeException {
    public ParticipantNotFoundException(String groupId, String participantId) {
        super(String.format("Participant (%s) does not belong to group (%s)", participantId, groupId));
    }
}
