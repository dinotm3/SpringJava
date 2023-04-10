package d.tmesaric.devize.jobs;

import d.tmesaric.devize.domain.Transaction;
import d.tmesaric.devize.repository.JpaTransactionRepository;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Set;

public class TransactionPrintJob extends QuartzJobBean {

    private final Logger log = LoggerFactory.getLogger(TransactionPrintJob.class);

    private final JpaTransactionRepository jpaTransactionRepository;

    public TransactionPrintJob(JpaTransactionRepository jpaTransactionRepository) {
        this.jpaTransactionRepository = jpaTransactionRepository;
    }


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Set<Transaction> transactionSet = jpaTransactionRepository.findAll();



        if(transactionSet.iterator().hasNext()) {
            log.info("\n***********************************\n");
            log.info("\nThese are the current transactions:\n");
            transactionSet.stream()
                            .filter(transaction -> transaction.getAmountHRK() > 0)
                            .forEach(
                                    transaction -> log.info(transaction.toString())
                            );
        } else {
            log.info("These are currently no transactions in the app");
        }
    }

}
