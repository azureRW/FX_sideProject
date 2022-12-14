package com.fx_sideproject;

import model.deep.semiPersistence;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;
import java.text.ParseException;

@SpringBootApplication(scanBasePackages = {"controller","model","com.fx_sideproject","config","mappingObj","aop"})
@EnableJpaRepositories(basePackages = {"model","controller","com.fx_sideproject","mappingObj"})
@EnableAsync
@EnableAspectJAutoProxy
@EnableTransactionManagement
@EntityScan(basePackages = {"mappingObj","model","controller"})

public class FxSideProjectApplication {


    public static void main(String[] args) throws IOException, ParseException, InterruptedException {
        ConfigurableApplicationContext ctx = SpringApplication.run(FxSideProjectApplication.class, args);
        semiPersistence bean2=ctx.getBean(semiPersistence.class);
        bean2.callMainService();



    }
}

