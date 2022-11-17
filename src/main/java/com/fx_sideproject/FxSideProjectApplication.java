package com.fx_sideproject;

import model.semiPersistence;
import model.unitTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;
import java.text.ParseException;

@SpringBootApplication(scanBasePackages = {"controller","model","com.fx_sideproject","config","mappingObj"}
//                        ,exclude ={DataSourceAutoConfiguration.class }
                        )

@EnableJpaRepositories(basePackages = {"model","controller","com.fx_sideproject","mappingObj"})

@EnableTransactionManagement
@EntityScan(basePackages = {"mappingObj","model","controller"})

public class FxSideProjectApplication {


    public static void main(String[] args) throws IOException, ParseException, InterruptedException {
        ConfigurableApplicationContext ctx = SpringApplication.run(FxSideProjectApplication.class, args);

        unitTest bean1 = ctx.getBean(unitTest.class);
        semiPersistence bean2=ctx.getBean(semiPersistence.class);
        bean2.callMainService();
//        bean1.test();
    }
}

