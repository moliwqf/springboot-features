package com.moli.features.sba;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author moli
 * @time 2024-08-01 16:15:35
 */
@EnableAdminServer
@SpringBootApplication
public class SBAApplication {

    public static void main(String[] args) {
        SpringApplication.run(SBAApplication.class, args);
    }
}
