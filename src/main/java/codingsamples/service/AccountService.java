package codingsamples.service;

import codingsamples.model.Account;
import codingsamples.repository.AccountRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    private final EntityManager entityManager;

    AccountService(AccountRepository accountRepository, EntityManager entityManager) {
        this.accountRepository = accountRepository;
        this.entityManager = entityManager;
    }

    @Transactional(rollbackOn = Exception.class)
    public synchronized Account save(Account account) {
        if (accountRepository.count() >= 10) {
            throw new IllegalStateException("Exceeded maximum number of servers");
        }
        Optional<Account> serverOptional = accountRepository.findByNameAndDomain(account.getName(), account.getDomain());
        if (serverOptional.isPresent()) {
            throw new IllegalStateException("Server with name " + account.getName() + " and domain " + account.getDomain() + " already exists");
        }
        return accountRepository.save(account);
    }

    // Далее примеры - решение через изоляцию транзакций, явная блокировка на уровне бд,
    // атомарные запросы sql
    //    READ_UNCOMMITTED prevents nothing. It's the zero isolation level
    //    READ_COMMITTED prevents just one, i.e. Dirty reads
    //    REPEATABLE_READ prevents two anomalies: Dirty reads and Non-repeatable reads
    //    SERIALIZABLE prevents all three anomalies: Dirty reads, Non-repeatable reads and Phantom reads
    // по сути для задачи подходят только SERIALIZABLE так как даже REPEATABLE_READ не блокирует диапазон
    // а лишь блокирует доступ к конкретным считанным строкам (Non-repeatable reads)
    // Диапазон блокируется для SERIALIZABLE, но в нашем случае это будет целая таблица и лучше засинхронайзить
    // только java метод
    // Пробуем sql запросом -
    @Transactional(rollbackOn = Exception.class)
    public void saveInOneStatement(Account account) {
        String STATEMENT = "insert into account(id, name, domain) " +
                "select :id, :name, :domain where (select count(*) from account) < 10 " +
                "and not exists (select id from account where name = :name and domain = :domain))";
        int result = entityManager.createNativeQuery(STATEMENT)
                .setParameter("id", account.getId())
                .setParameter("name", account.getName())
                .setParameter("domain", account.getDomain())
                .executeUpdate();
        if (result != 1) {
            throw new IllegalStateException("Could not create server with name "
                    + account.getName() + " and domain " + account.getDomain());
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public List<Account> findByDomain(String domain) {
        return accountRepository.findAllByDomain(domain);
    }

    @Transactional(rollbackOn = Exception.class)
    public void deleteAll() {
        accountRepository.deleteAll();
    }
}
