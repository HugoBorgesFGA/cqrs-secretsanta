package br.tech.hugobp.secretsanta.domain.participant;

import br.tech.hugobp.cqrs.Entity;
import br.tech.hugobp.secretsanta.domain.email.Email;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Participant extends Entity {

    private final String name;
    private final Email email;

    public Participant(String id, String name, Email email) {
        super(id);
        this.name = name;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Participant that = (Participant) o;

        if (!Objects.equals(name, that.name)) return false;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
