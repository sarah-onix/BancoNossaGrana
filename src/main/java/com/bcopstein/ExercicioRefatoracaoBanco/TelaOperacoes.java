package com.bcopstein.ExercicioRefatoracaoBanco;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TelaOperacoes {
	private Stage mainStage; 
	private Scene cenaEntrada;
	private Scene cenaOperacoes;
	private ObservableList<Operacao> ultimasOperacoes;
	private ListView<Operacao> extrato;
	private Label cat;
	private Label lim;
	private String categoria;
	private String limRetDiaria;

	private static final Contas CONTAS = Contas.getInstance();

	private TextField tfValorOperacao;
	private TextField tfSaldo;
	private int numeroConta;


	public TelaOperacoes(Stage mainStage, Scene telaEntrada, int numeroConta) {                                                                                    // conta
		this.mainStage = mainStage;
		this.cenaEntrada = telaEntrada;
		this.numeroConta = numeroConta;
	}

	public Scene getTelaOperacoes() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

		String dadosCorr = numeroConta + " : " + CONTAS.getCorrentista(numeroConta);
        Text scenetitle = new Text(dadosCorr);
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

		categoria = "Categoria: " + CONTAS.getStrStatus(numeroConta);
		limRetDiaria = "Limite retirada diaria: " + CONTAS.getLimRetiradaDiaria(numeroConta);

        cat = new Label(categoria);
        grid.add(cat, 0, 1);

        lim = new Label(limRetDiaria);
        grid.add(lim, 0, 2);
        
        Label tit = new Label("Ultimos movimentos");
        grid.add(tit,0,3);

		update();

		extrato = new ListView<>(ultimasOperacoes);
        extrato.setPrefHeight(140);
        grid.add(extrato, 0, 4);

        tfSaldo = new TextField();
        tfSaldo.setDisable(true);
		tfSaldo.setText("" + CONTAS.getSaldo(numeroConta));
        HBox valSaldo = new HBox(20);        
        valSaldo.setAlignment(Pos.BOTTOM_LEFT);
        valSaldo.getChildren().add(new Label("Saldo"));
        valSaldo.getChildren().add(tfSaldo);
        grid.add(valSaldo, 0, 5);        

        tfValorOperacao = new TextField();
        HBox valOper = new HBox(30);        
        valOper.setAlignment(Pos.BOTTOM_CENTER);
        valOper.getChildren().add(new Label("Valor operacao"));
        valOper.getChildren().add(tfValorOperacao);
        grid.add(valOper, 1, 1);        

        Button btnCredito = new Button("Credito");
        Button btnDebito = new Button("Debito");
        Button btnEstatistica = new Button("Estatistica");
        Button btnVoltar = new Button("Voltar");
        HBox hbBtn = new HBox(20);
        hbBtn.setAlignment(Pos.TOP_CENTER);
        hbBtn.getChildren().add(btnCredito);
        hbBtn.getChildren().add(btnDebito);
        hbBtn.getChildren().add(btnEstatistica);
        hbBtn.getChildren().add(btnVoltar);
        grid.add(hbBtn, 1, 2);
        
        btnCredito.setOnAction(e->{
        	try {
        	  double valor = Integer.parseInt(tfValorOperacao.getText());
        	  if (valor < 0.0) {
        		  throw new NumberFormatException("Valor invalido");
        	  }
				// evaluate
				CONTAS.deposito(numeroConta, valor);

				tfSaldo.setText("" + CONTAS.getSaldo(numeroConta));
				// evaluate all
				update();
				extrato.setItems(ultimasOperacoes);

        	}catch(NumberFormatException ex) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Valor inválido !!");
				alert.setHeaderText(null);
				alert.setContentText("Valor inválido para operacao de crédito!!");

				alert.showAndWait();
        	}        	
        });
        
        btnDebito.setOnAction(e->{
        	try {
          	  double valor = Integer.parseInt(tfValorOperacao.getText());
				if (valor < 0.0 || valor > CONTAS.getSaldo(numeroConta)) {
          		  throw new NumberFormatException("Saldo insuficiente");
          	  }
				CONTAS.retirada(numeroConta, valor);
				tfSaldo.setText("" + CONTAS.getSaldo(numeroConta));

				update();
				extrato.setItems(ultimasOperacoes);
				tfSaldo.setText("" + CONTAS.getSaldo(numeroConta));
          	}catch(NumberFormatException ex) {
  				Alert alert = new Alert(AlertType.WARNING);
  				alert.setTitle("Valor inválido !!");
  				alert.setHeaderText(null);
  				alert.setContentText("Valor inválido para operacao de débito!");

  				alert.showAndWait();
			  }catch(IllegalArgumentException argu)
			  {
				  Alert alert = new Alert(AlertType.WARNING);
				  alert.setTitle(argu.getMessage());
				  alert.setHeaderText(null);
			  }        	
        });

        btnEstatistica.setOnAction(e->{
			TelaEstatistica toper = new TelaEstatistica(mainStage, cenaOperacoes, numeroConta);
			Scene scene = toper.getTelaEstatistica();
			mainStage.setScene(scene);

        });

        btnVoltar.setOnAction(e->{
        	mainStage.setScene(cenaEntrada);
        });

        cenaOperacoes = new Scene(grid);
        return cenaOperacoes;
	}

	public void update() {
		//Persistencia.getInstance().saveOperacoes(operacoes);
		// WIll be replaced to another location later
		// Seleciona apenas o extrato da conta atual


		// This will stay here
		ultimasOperacoes = FXCollections.observableArrayList(Operacoes.getInstance().getOperacoesDaConta(numeroConta));
		categoria = "Categoria: " + CONTAS.getStrStatus(numeroConta);
		cat.setText(categoria);
		limRetDiaria = "Limite retirada diaria: " + CONTAS.getLimRetiradaDiaria(numeroConta);
		lim.setText(limRetDiaria);
	}

}
