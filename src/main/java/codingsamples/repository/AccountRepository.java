package codingsamples.repository;

import codingsamples.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    List<Account> findAllByName(String serverName);

    List<Account> findAllByDomain(String domain);

    Optional<Account> findByNameAndDomain(String name, String domain);

//    @Query(nativeQuery = true, value = "INSERT INTO account(id, name, domain) " +
//            "SELECT :id, :name, :domain " +
//            "WHERE (SELECT COUNT(*) FROM account) < 10 " +
//            "AND NOT EXISTS (SELECT id FROM account WHERE name = :name AND domain = :domain)")
//    void insertAccountIfUnique(@Param("id") UUID id, @Param("name") String name, @Param("domain") String domain);

}
