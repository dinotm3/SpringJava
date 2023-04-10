package d.tmesaric.devize.repository;

import d.tmesaric.devize.domain.Country;
import d.tmesaric.devize.domain.Transaction;
import d.tmesaric.devize.domain.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Repository
public class JdbcTransactionRepository implements TransactionRepository {
    private JdbcTemplate jdbc;
    private SimpleJdbcInsert transactionInserter;
    private static final String TABLE_NAME = "Transaction";
    private static final String GENERATED_KEY_COLUMN = "id";
    private static final String SELECT_ALL = "SELECT id, name, amount, country, date, exchange_rate, amountHRK FROM transaction";
    private static final String FIND_BY_ID = " where id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM transaction WHERE id = ?";

    @Autowired
    public JdbcTransactionRepository(JdbcTemplate jdbc){
        this.jdbc = jdbc;
        this.transactionInserter = new SimpleJdbcInsert(jdbc)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(GENERATED_KEY_COLUMN);
    }

    @Override
    public Set<Transaction> findAll() {
        return Set.copyOf(jdbc.query(SELECT_ALL, this::mapRowToTransaction));
    }


    @Override
    public Optional<Transaction> findById(Long id) {
        try
        {
            return Optional.ofNullable(
                    jdbc.queryForObject(SELECT_ALL + FIND_BY_ID, this::mapRowToTransaction, id)
            );
        } catch (EmptyResultDataAccessException e){
            return  Optional.empty();
        }
    }

    @Override
    public Optional<Transaction> save(Transaction transaction) {
        try
        {
            transaction.setId(saveTransactionDetails(transaction));
            return Optional.of(transaction);
        } catch (DuplicateKeyException e){
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(Long id) {
        jdbc.update(DELETE_BY_ID, id);
    }

    private Transaction mapRowToTransaction(ResultSet rs, int rowNum) throws SQLException {
        return new Transaction(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getDouble("amount"),
                rs.getTimestamp("date").toLocalDateTime(),
                Country.valueOf(rs.getString("country")),
                Type.valueOf(rs.getString("type")),
                rs.getDouble("exchange_rate"),
                rs.getDouble("amountHRK")
        );
    }

    private long saveTransactionDetails(Transaction transaction){
        Map<String, Object> values = new HashMap<>();
        values.put("id", transaction.getId());
        values.put("name", transaction.getName());
        values.put("amount", transaction.getAmount());
        values.put("date", transaction.getDate());
        values.put("country", transaction.getCountry());
        values.put("type", transaction.getType());
        values.put("exchange_rate", transaction.getExchangeRate());
        values.put("amountHRK", transaction.getAmountHRK());

        return transactionInserter.executeAndReturnKey(values).longValue();
    }
}
