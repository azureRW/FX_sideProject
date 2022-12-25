package com.fx_sideproject;

import controller.unitTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;
import java.text.ParseException;

@SpringBootApplication(scanBasePackages = {"controller","model","com.fx_sideproject","config","aop","security","scheduling"},
exclude = { SecurityAutoConfiguration.class })
@EnableJpaRepositories(basePackages = {"model.dao"})
@EnableAsync
@EnableAspectJAutoProxy
@EnableTransactionManagement
@EnableScheduling
@EntityScan(basePackages = {"model.PO"})
@EnableCaching

public class FxSideProjectApplication {


    public static void main(String[] args) throws IOException, ParseException, InterruptedException {
        ConfigurableApplicationContext ctx = SpringApplication.run(FxSideProjectApplication.class, args);
//        unitTest bean = ctx.getBean(unitTest.class);
//        bean.test();

    }
}

