package d.tmesaric.devize.jobs;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchedulerConfig {

    @Bean
    public JobDetail transactionPrintJobDetail() {
        return JobBuilder.newJob(TransactionPrintJob.class).withIdentity("transactionPrintJob")
                .storeDurably().build();
    }

    @Bean
    public Trigger vaccinationPrintTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInMinutes(15).repeatForever();

        return TriggerBuilder.newTrigger().forJob(transactionPrintJobDetail())
                .withIdentity("transactionPrintTrigger").withSchedule(scheduleBuilder).build();
    }
}
