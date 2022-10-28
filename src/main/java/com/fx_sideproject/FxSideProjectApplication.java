package com.fx_sideproject;

import controller.metaAPI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import java.io.IOException;
import java.text.ParseException;

@SpringBootApplication(scanBasePackages = {"controller","model"})
public class FxSideProjectApplication {

    public static void main(String[] args) throws IOException, ParseException {
        ConfigurableApplicationContext ctx = SpringApplication.run(FxSideProjectApplication.class, args);
        metaAPI bean = ctx.getBean(controller.metaAPI.class);
        bean.getCurrentCandle();


    }


}
