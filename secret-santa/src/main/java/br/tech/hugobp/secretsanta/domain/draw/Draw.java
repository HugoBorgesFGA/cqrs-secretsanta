package br.tech.hugobp.secretsanta.domain.draw;

import br.tech.hugobp.secretsanta.domain.participant.Participant;

import java.util.*;

public class Draw {

    public static Map<Participant, Participant> shuffle(Set<Participant> participants) {
        final Map<Participant, Participant> result = new HashMap<>();
        final List<Participant> urn = new ArrayList<>(participants);
        final Random random = new Random();
        for(Participant participant: participants) {
            final int remainingParticipantsInUrn = urn.size();

            Participant picked;
            do {
                final int randomPosition = random.nextInt(remainingParticipantsInUrn);
                picked = urn.get(randomPosition);
            } while(picked.equals(participant) && (remainingParticipantsInUrn > 1));
            urn.remove(picked);

            result.put(participant, picked);
        }

        if (!result.keySet().containsAll(participants)) {
            return shuffle(participants);
        }

        boolean someonePickedItself = result.entrySet().stream()
            .anyMatch(participant -> {
                final Participant picked = result.get(participant);
                return participant.equals(picked);
            });

        if (someonePickedItself) {
            return shuffle(participants);
        }

        return result;
    }
}
