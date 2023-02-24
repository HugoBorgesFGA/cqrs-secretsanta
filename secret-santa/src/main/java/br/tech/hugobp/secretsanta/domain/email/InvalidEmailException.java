package br.tech.hugobp.secretsanta.domain.email;

public class InvalidEmailException extends RuntimeException {

    public InvalidEmailException(String invalidEmail) {
        super("The following isn't a valid e-mail: " + invalidEmail);
    }
}
