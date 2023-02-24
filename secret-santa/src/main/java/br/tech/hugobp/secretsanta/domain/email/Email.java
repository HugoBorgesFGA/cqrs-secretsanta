package br.tech.hugobp.secretsanta.domain.email;

import org.apache.commons.validator.routines.EmailValidator;

public class Email {

    private final String value;
    private static final EmailValidator emailValidator = EmailValidator.getInstance();

    public Email(String value) {
        validateEmail(value);

        this.value = value;
    }

    public String toString() {
        return value;
    }

    public static void validateEmail(String value) {
        if (!emailValidator.isValid(value)) {
            throw new InvalidEmailException(value);
        }
    }
}
