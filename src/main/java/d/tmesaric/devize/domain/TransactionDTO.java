package d.tmesaric.devize.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

public class TransactionDTO {
    private final Long id;

    private final String name;

    private final LocalDateTime date;

    private final Type type;

    private final Country country;

    private final Double amount;

    private final Double amountHRK;

    private final Double exchangeRate;

    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.name = transaction.getName();
        this.date = transaction.getDate();
        this.type = transaction.getType();
        this.country = transaction.getCountry();
        this.amount = transaction.getAmount();
        this.exchangeRate = transaction.getExchangeRate();
        this.amountHRK = transaction.getAmountHRK();
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Type getType() {
        return type;
    }

    public Double getAmount() {return amount;}

    public Double getAmountHRK() {
        return amountHRK;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public Country getCountry() {
        return country;
    }

    public Long getId() {
        return id;
    }
    @Override
    public String toString() {
        return "TransactionDTO{" +
                "name='" + name + '\'' +
                ", id=" + id + '\'' +
                ", date=" + date + '\'' +
                ", type=" + type + '\'' +
                ", amount=" + amount + '\'' +
                ", exchangeRate=" + exchangeRate + '\'' +
                ", country=" + country + '\'' +
                ", amountHRK=" + amountHRK +
                '}';
    }
}



