package com.fx_sideproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication(scanBasePackages = {"controller","model"})
public class FxSideProjectApplication {

    public static void main(String[] args) throws InterruptedException, IOException {
        ConfigurableApplicationContext ctx = SpringApplication.run(FxSideProjectApplication.class, args);


    }


}
