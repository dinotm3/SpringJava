package d.tmesaric.devize.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import d.tmesaric.devize.domain.Country;
import d.tmesaric.devize.domain.Transaction;
import d.tmesaric.devize.domain.Type;
import d.tmesaric.devize.repository.JpaTransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.JsonPath;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JpaTransactionRepository jpaTransactionRepository;


    @Test
    void findAll() throws Exception {
        this.mockMvc.perform(
                        get("/transaction/api")
                                .with(user("test").password("testpass").roles("ADMIN", "USER"))
                                .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Transactional
    @Test
    void save() throws Exception {
        String TEST_NAME = "Test Name";
        Double TEST_AMOUNT = 500.5;
        LocalDateTime TEST_DATE = LocalDateTime.now().plusDays(1).truncatedTo(ChronoUnit.MINUTES);
        String TEST_DATE_RESPONSE = TEST_DATE + ":00";
        Type TEST_TYPE = Type.INCOME;
        String TEST_TYPE_RESPONSE = Type.INCOME.toString();
        Country TEST_COUNTRY = Country.AUSTRIA;
        String TEST_COUNTRY_RESPONSE = Country.AUSTRIA.toString();
        Double TEST_EXCHANGE_RATE = 7.50;
        Double TEST_AMOUNT_HRK = TEST_AMOUNT * TEST_EXCHANGE_RATE;


        Transaction transaction = new Transaction();
        transaction.setName(TEST_NAME);
        transaction.setAmount(TEST_AMOUNT);
        transaction.setDate(TEST_DATE);
        transaction.setType(TEST_TYPE);
        transaction.setCountry(TEST_COUNTRY);
        transaction.setExchangeRate(TEST_EXCHANGE_RATE);
        transaction.setAmountHRK(TEST_AMOUNT_HRK);


        this.mockMvc.perform(
                        post("/transaction/api")
                                .with(user("test").password("testpass").roles("ADMIN", "USER"))
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(transaction))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(TEST_NAME))
                .andExpect(jsonPath("$.amount").value(TEST_AMOUNT))
                .andExpect(jsonPath("$.date").value(TEST_DATE_RESPONSE))
                .andExpect(jsonPath("$.type").value(TEST_TYPE_RESPONSE))
                .andExpect(jsonPath("$.country").value(TEST_COUNTRY_RESPONSE))
                .andExpect(jsonPath("$.exchangeRate").value(TEST_EXCHANGE_RATE))
                .andExpect(jsonPath("$.amountHRK").value(TEST_AMOUNT_HRK));
    }

    @Transactional
    @Test
    void delete_adminRights() throws Exception {
        final var TEST_NAME = "placa";

        assertTrue(jpaTransactionRepository.findByName(TEST_NAME).isPresent());
        this.mockMvc.perform(
                        delete("/transaction/api/" + TEST_NAME)
                                .with(user("admin").password("password").roles("ADMIN", "USER"))
                                .with(csrf())
                )
                .andExpect(status().isNoContent());
        assertTrue(jpaTransactionRepository.findByName(TEST_NAME).isEmpty());
    }

    @Test
    void delete_noAdminRights() throws Exception {
        final var TEST_NAME = "placa";

        this.mockMvc.perform(
                        delete("/transaction/api/" + TEST_NAME)
                                .with(user("admin").password("password").roles("USER"))
                                .with(csrf())
                )
                .andExpect(status().isForbidden());
    }
}
