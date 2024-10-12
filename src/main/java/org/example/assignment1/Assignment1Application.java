package org.example.assignment1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * The main entry point for the application.
 */
@SpringBootApplication
@ComponentScan({"service", "controller","model", "repository", "service"})
@EntityScan("model")
@EnableJpaRepositories("repository")
public class Assignment1Application {

    /**
     * The main method to start the Spring Boot application.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(Assignment1Application.class, args);
    }

}
