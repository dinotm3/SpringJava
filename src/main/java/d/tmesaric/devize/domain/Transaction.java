package d.tmesaric.devize.domain;
import org.springframework.format.annotation.DateTimeFormat;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Transaction
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "{validation.transaction.name.notEmpty}")
    private String name;

    @Positive(message = "{validation.transaction.amount.positive}")
    @NotNull(message = "{validation.transaction.amount.notNull}")
    private Double amount;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "{validation.transaction.country.notNull}")
    private Country country;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "{validation.transaction.type.notNull}")
    private Type type;

    @Positive
    private Double exchangeRate;

    @Positive
    private Double amountHRK;

    public Transaction(){
        exchangeRate = 7.50;
        date = LocalDateTime.now();
    }

    public Transaction(Long id, String name, Double amount, LocalDateTime date, Country country, Type type, Double exchangeRate, Double amountHRK){
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.country = country;
        this.type = type;
        this.exchangeRate = exchangeRate;
        this.amountHRK = amountHRK;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Double getAmountHRK() {
        return amountHRK;
    }

    public void setAmountHRK(Double amountHRK) {
        this.amountHRK = amountHRK;
    }

    public Double CalculateHRK(Double amount, Double exchangeRate){
        amountHRK = this.exchangeRate * this.amount;
        return this.amountHRK;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return  "\nTransaction: " +
                "\nName='" + name + '\'' +
                "\nType=" + type +
                "\nAmountHRK=" + amountHRK;
    }
}
