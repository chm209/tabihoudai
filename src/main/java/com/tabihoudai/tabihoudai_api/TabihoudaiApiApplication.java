package com.tabihoudai.tabihoudai_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
//@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableJpaAuditing
public class TabihoudaiApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TabihoudaiApiApplication.class, args);
    }

}
