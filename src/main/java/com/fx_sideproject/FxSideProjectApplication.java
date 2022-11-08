package com.fx_sideproject;

import controller.unitTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;
import java.text.ParseException;

@SpringBootApplication(scanBasePackages = {"controller","model","com.fx_sideproject","config"}
//                        ,exclude ={DataSourceAutoConfiguration.class }
                        )

@ContextConfiguration(classes = config.springDataConfig.class)
public class FxSideProjectApplication {


    public static void main(String[] args) throws IOException, ParseException {
        ConfigurableApplicationContext ctx = SpringApplication.run(FxSideProjectApplication.class, args);
        unitTest t1 = ctx.getBean(unitTest.class);
        t1.test();
        }


    }

