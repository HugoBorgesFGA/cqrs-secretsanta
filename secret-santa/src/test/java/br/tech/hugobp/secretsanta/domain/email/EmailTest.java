package br.tech.hugobp.secretsanta.domain.email;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EmailTest {

    @Test
    public void test_valid_email_can_be_stored_in_value_object() {
        Assertions.assertDoesNotThrow(() -> {
            final Email email = new Email("validemail@domain.com");
            Assertions.assertNotNull(email);
        });
    }

    @Test
    public void test_invalid_email_throws_an_exception() {
        Assertions.assertThrows(InvalidEmailException.class, () -> {
            final Email email = new Email("invalidemail@@domain.com");
        });
    }
}