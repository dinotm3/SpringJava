package d.tmesaric.devize.controller;

import d.tmesaric.devize.domain.Country;
import d.tmesaric.devize.domain.Transaction;
import d.tmesaric.devize.domain.Type;
import d.tmesaric.devize.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Controller
@RequestMapping("transaction")
public class TransactionController {

    private final JmsTemplate jmsTemplate;

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService, JmsTemplate jmsTemplate){
        this.transactionService = transactionService;
        this.jmsTemplate = jmsTemplate;
    }
    @GetMapping("new")
    public String form(Transaction transaction, Model model){
        model.addAttribute("country", Country.values());
        model.addAttribute("type", Type.values());

        return "form";
    }

    @PostMapping("confirmed")
    public String confirmed(@Valid Transaction transaction, Errors errors, Model model){
        if (errors.hasErrors()){
            model.addAttribute("country", Country.values());
            model.addAttribute("type", Type.values());
            return "form";
        }
        model.addAttribute("amountHRK", transaction.CalculateHRK(transaction.getAmount(), transaction.getExchangeRate()));
        transactionService.save(transaction);
        jmsTemplate.convertAndSend("The transaction " + transaction.getName() + " is accepted!");

        return "confirmed";
    }
}
