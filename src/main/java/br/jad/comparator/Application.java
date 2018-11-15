package br.jad.comparator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.web.WebApplicationInitializer;

import java.util.Base64;

/**
 * Start and run Spring boot Application
 * Uses HSQLDB in memory, configured using application.yml
 *
 * @author  Jadson Cabral
 * @version 1.0.0
 */
@SpringBootApplication
public class Application  extends SpringBootServletInitializer implements WebApplicationInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }

    public static void main(String args[]) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
