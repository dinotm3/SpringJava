package d.tmesaric.devize.controller;


import d.tmesaric.devize.domain.Country;
import d.tmesaric.devize.domain.Type;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void confirmed_noDataEntered() throws Exception {
        this.mockMvc
                .perform(
                        post("/transaction/confirmed")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .with(csrf())
                                .with(user("test").password("testpass").roles("USER", "ADMIN"))
                )
                    .andExpect(status().isOk())
                    .andExpect(view().name("form"));
    }

    @Transactional
    @Test
    void confirmed_validDataEntered() throws Exception {
        this.mockMvc
                .perform(
                        post("/transaction/confirmed")
                                .param("name", "Test Name")
                                .param("id", "12345678")
                                .param("amount", String.valueOf(1500))
                                .param("date", LocalDateTime.now().plusDays(1).truncatedTo(ChronoUnit.MINUTES).toString())
                                .param("type", Type.INCOME.toString())
                                .param("country", Country.AUSTRIA.toString())
                                .param("exchangerate", String.valueOf(7.50))
                                .param("amounthrk", String.valueOf(1500 * 7.50))
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .with(csrf())
                                .with(user("test").password("testpass").roles("USER", "ADMIN"))
                )
                .andExpect(status().isOk())
                .andExpect(view().name("confirmed"));
    }
}
