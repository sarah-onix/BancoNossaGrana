package com.bcopstein.ExercicioRefatoracaoBanco;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;


public class App extends Application {
	private Persistencia persistencia;
	private Map<Integer,Conta> contas;
	private List<Operacao> operacoes;
	
	private TelaEntrada telaEntrada;
	
    @Override
    public void start(Stage primaryStage) {
        persistencia = Persistencia.getInstance();
        contas = persistencia.loadContas();    	
    	operacoes = persistencia.loadOperacoes();
    	
    	primaryStage.setTitle("$$ Banco NOSSA GRANA $$");

        telaEntrada = new TelaEntrada(primaryStage, contas, operacoes);

        primaryStage.setScene(telaEntrada.getTelaEntrada());
        primaryStage.show();
        primaryStage.sizeToScene(); // added
        primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setMinHeight(primaryStage.getHeight());

    }
    
    @Override
    public void stop() {
        persistencia.saveContas(contas.values());
        persistencia.saveOperacoes(operacoes);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

