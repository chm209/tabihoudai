package com.tabihoudai.tabihoudai_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TabihoudaiApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TabihoudaiApiApplication.class, args);
    }

}
