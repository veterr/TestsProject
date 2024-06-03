package codingsamples.repository;

import codingsamples.model.Account;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    Account account;

    @BeforeEach
    void setUp() {
        account = new Account().
                builder()
                .id(UUID.randomUUID())
                .domain("nb")
                .name("testyyy").build();
    }

    @AfterEach
    void tearDown() {
        accountRepository.deleteAll();
    }

    @Test
    void save() {
        Account ser = accountRepository.saveAndFlush(account);
        assertThat(ser).isNotNull();
        assertThat(ser.getId()).isNotNull();
        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts.stream().filter(s -> s.getId().equals(ser.getId())).findFirst().isPresent());
        assertThat(accountRepository.findAllByName("test")).isNotNull();
    }

//    @Test
//    void testUniqueness() {
//        accountRepository.insertAccountIfUnique(account.getId(), account.getName(), account.getDomain());
//        assertThatThrownBy(() -> accountRepository
//                .insertAccountIfUnique(account.getId(), account.getName(), account.getDomain()))
//                .isInstanceOf(Exception.class);
//    }
}
