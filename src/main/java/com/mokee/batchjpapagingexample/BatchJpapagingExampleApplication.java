package com.mokee.batchjpapagingexample;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BatchJpapagingExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatchJpapagingExampleApplication.class, args);
    }


    @Bean
    public CommandLineRunner run(UserRepository userRepository) {
        return args -> {
            User a = new User();
            a.setUsername("a");
            a.setPassword("a");
            a.setAge(20);
            userRepository.save(a);

            User b = new User();
            b.setUsername("b");
            b.setPassword("b");
            b.setAge(25);

            userRepository.save(b);
        };
    }
}
