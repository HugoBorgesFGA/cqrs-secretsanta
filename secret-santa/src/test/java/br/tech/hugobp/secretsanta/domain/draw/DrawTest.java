package br.tech.hugobp.secretsanta.domain.draw;

import br.tech.hugobp.secretsanta.domain.email.Email;
import br.tech.hugobp.secretsanta.domain.participant.Participant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DrawTest {

    @RepeatedTest(999)
    public void santa_draw_works_as_expected() {
        final Set<Participant> participants = Stream.of(
            participant("Hugo"),
            participant("Clarissa"),
            participant("Francinaldo"),
            participant("Iracema"),
            participant("Bruno"),
            participant("Ana"),
            participant("Lucas"),
            participant("Vinicius"),
            participant("Francisca"),
            participant("Jose"),
            participant("Santilho")
        ).collect(Collectors.toSet());

        Assertions.assertDoesNotThrow(() -> {
            final Map<Participant, Participant> secretSantaMapping = Draw.shuffle(participants);
            assertEquals(participants.size(), secretSantaMapping.size());
        });
    }

    private Participant participant(String name) {
        return new Participant(
            UUID.randomUUID().toString(),
            name,
            new Email(name + "@gmail.com")
        );
    }
}