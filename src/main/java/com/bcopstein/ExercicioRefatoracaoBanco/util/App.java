package com.bcopstein.ExercicioRefatoracaoBanco.util;

import com.bcopstein.ExercicioRefatoracaoBanco.service.BancoFacade;
import com.bcopstein.ExercicioRefatoracaoBanco.view.TelaEntrada;
import javafx.application.Application;
import javafx.stage.Stage;


public class App extends Application {
	private TelaEntrada telaEntrada;

    @Override
    public void start(Stage primaryStage) {
    	primaryStage.setTitle("$$ Banco NOSSA GRANA $$");
        telaEntrada = new TelaEntrada(primaryStage);
        primaryStage.setScene(telaEntrada.getTelaEntrada());
        primaryStage.show();
        primaryStage.sizeToScene(); // added
        primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setMinHeight(primaryStage.getHeight());

    }
    
    @Override
    public void stop() {
        BancoFacade.getInstance().save();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

