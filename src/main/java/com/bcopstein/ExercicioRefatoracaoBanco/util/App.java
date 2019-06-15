package com.bcopstein.ExercicioRefatoracaoBanco.util;

import controller.BancoController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = BancoController.class)
public class App {



    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    public void stop() {
        //BancoFacade.getInstance().save();
    }
}

