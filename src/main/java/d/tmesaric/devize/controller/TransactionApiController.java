package d.tmesaric.devize.controller;

import d.tmesaric.devize.domain.Transaction;
import d.tmesaric.devize.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping(path = "transaction/api")
public class TransactionApiController {

    private final TransactionService transactionService;
    private final String errorNotFound = "There was no transaction found by that name";
    private final String errorExists = "Transaction with that name already exists, please enter new name";

    public TransactionApiController(TransactionService transactionService) {
        this.transactionService = transactionService;

    }

    @GetMapping
    public Iterable<Transaction> findAll() {
        return transactionService.findAll();
    }

    @GetMapping("{name}")
    public Transaction getByName(@PathVariable final String name) {
        return transactionService.findByName(name)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, errorNotFound)
                );
    }

    //@Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Transaction save(@Valid @RequestBody Transaction transaction) {
        if (!transactionService.existsByName(transaction.getName())){
            return transactionService.saveJpa(transaction);
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, errorExists);
        }
    }

    //@Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{name}")
    void delete(@PathVariable String name) {
        if (!transactionService.existsByName(name)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorNotFound);
        }
            transactionService.deleteByName(name);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class, MethodArgumentNotValidException.class})
    ResponseEntity<String> handleConstraintViolationException(Exception e) {
        return new ResponseEntity<>("Validation error occurred: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}