package d.tmesaric.devize.repository;

import d.tmesaric.devize.domain.Transaction;

import java.util.Optional;
import java.util.Set;

public interface TransactionRepository {
    Set<Transaction> findAll();
    Optional<Transaction> findById(Long id);
    Optional<Transaction> save(Transaction transaction);
    void deleteById(Long id);

}
