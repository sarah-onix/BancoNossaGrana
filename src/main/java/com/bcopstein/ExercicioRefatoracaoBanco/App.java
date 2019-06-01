package com.bcopstein.ExercicioRefatoracaoBanco;

import javafx.application.Application;
import javafx.stage.Stage;


public class App extends Application {
    private static final Persistencia PERSISTENCIA = Persistencia.getInstance();
	private TelaEntrada telaEntrada;
	
    @Override
    public void start(Stage primaryStage) {
        Contas.initialize(PERSISTENCIA.loadContas());
        Operacoes.initialize(PERSISTENCIA.loadOperacoes());

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
        // Persistir os dados daqui e usar método que retorna as contas e operacoes?
        // Ou Adicionar + 1 dependencia de Persistencia para Contas e Operacoes e aumentar o numeros de funcoes dessas classes?
        PERSISTENCIA.saveContas(Contas.getInstance().getAllContas());
        PERSISTENCIA.saveOperacoes(Operacoes.getInstance().getOperacoes());
    }

    public static void main(String[] args) {
        launch(args);
    }
}

