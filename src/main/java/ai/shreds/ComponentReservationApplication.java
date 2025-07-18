package ai.shreds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
@EnableScheduling
@EnableTransactionManagement
@ComponentScan(basePackages = "ai.shreds")
@EnableJpaRepositories(basePackages = "ai.shreds.infrastructure.repositories")
@EntityScan(basePackages = "ai.shreds.infrastructure.entities")
public class ComponentReservationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComponentReservationApplication.class, args);
    }

}