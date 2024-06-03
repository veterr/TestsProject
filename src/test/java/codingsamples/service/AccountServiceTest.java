package codingsamples.service;

import codingsamples.model.Account;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest {

    @Autowired
    AccountService accountService;

    Account account;

    @BeforeEach
    void setUp() {
        account = new Account().
                builder()
                .id(UUID.randomUUID())
                .domain("cv")
                .name("test32s225").build();
    }

    @AfterEach
    void tearDown() {
        accountService.deleteAll();
    }

    @Test
    void testUniqueness() {
        accountService.save(account);
        assertThatThrownBy(() -> accountService.save(account)).isInstanceOf(Exception.class);
    }
}
