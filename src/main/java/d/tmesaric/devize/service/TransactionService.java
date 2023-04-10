package d.tmesaric.devize.service;

import d.tmesaric.devize.domain.Transaction;
import d.tmesaric.devize.repository.JpaTransactionRepository;
import d.tmesaric.devize.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;


@Service
@Transactional
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final JpaTransactionRepository jpaTransactionRepository;

    public TransactionService(TransactionRepository transactionRepository, JpaTransactionRepository jpaTransactionRepository){
        this.transactionRepository = transactionRepository;
        this.jpaTransactionRepository = jpaTransactionRepository;
    }
    public Iterable<Transaction> findAll(){
        return jpaTransactionRepository.findAll();
    }
    public Optional<Transaction> findById(Long id){return jpaTransactionRepository.findById(id);}
    public Optional<Transaction> findByName(String name){return jpaTransactionRepository.findByName(name);}
    public Optional<Transaction> save(Transaction transaction){return transactionRepository.save(transaction);}
    public Transaction saveJpa(Transaction transaction){return jpaTransactionRepository.save(transaction);}
    public boolean existsById(Long id) {return jpaTransactionRepository.existsById(id);}
    public boolean existsByName(String name){return jpaTransactionRepository.existsByName(name);}
    public void deleteById(Long id){jpaTransactionRepository.deleteById(id);}
    public void deleteByName(String name) {jpaTransactionRepository.deleteByName(name);}
}
