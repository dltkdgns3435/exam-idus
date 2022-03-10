package shlee.exam.idus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class IdusApplication {

    public static void main(String[] args) {
        SpringApplication.run(IdusApplication.class, args);
    }

}
