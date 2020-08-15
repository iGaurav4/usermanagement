package com.enticer.usermananagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaAuditing
@EnableJpaRepositories
@EnableTransactionManagement
@Configuration
@SpringBootApplication()
public class TestNGWithSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestNGWithSpringApplication.class, args);
    }

}
