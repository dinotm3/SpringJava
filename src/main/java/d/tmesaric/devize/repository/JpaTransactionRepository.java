package d.tmesaric.devize.repository;

import d.tmesaric.devize.domain.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
import java.util.Set;

public interface JpaTransactionRepository extends CrudRepository<Transaction, Long> {
    Set<Transaction> findAll();
    Optional<Transaction> findByAmountBetween(int x, int y);
    boolean existsByName(String name);
    void deleteByName(String name);
    Optional<Transaction> findByName(String name);
}
