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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class TelaOperacoes {
	private Stage mainStage; 
	private Scene cenaEntrada;
	private Scene cenaOperacoes;
	private List<Operacao> operacoes;
	private List<Operacao> operacoesConta;
	private ObservableList<Operacao> ultimasOperacoes;
	private ListView<Operacao> extrato;
	private Label cat;
	private Label lim;
	private String categoria;
	private String limRetDiaria;

	private Conta conta; 

	private TextField tfValorOperacao;
	private TextField tfSaldo;

	public TelaOperacoes(Stage mainStage, Scene telaEntrada, Conta conta, List<Operacao> operacoes) { 																					// conta
		this.mainStage = mainStage;
		this.cenaEntrada = telaEntrada;
		this.conta = conta;
		this.operacoes = operacoes;
	}

	public Scene getTelaOperacoes() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        String dadosCorr = conta.getNumero()+" : "+conta.getCorrentista();
        Text scenetitle = new Text(dadosCorr);
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
        
        categoria = "Categoria: "+conta.getStrStatus();
        limRetDiaria = "Limite retirada diaria: "+conta.getLimRetiradaDiaria();
        
        cat = new Label(categoria);
        grid.add(cat, 0, 1);

        lim = new Label(limRetDiaria);
        grid.add(lim, 0, 2);
        
        Label tit = new Label("Ultimos movimentos");
        grid.add(tit,0,3);

		update();

        /*operacoesConta =
        		FXCollections.observableArrayList(
        				operacoes
        				.stream()
        				.filter(op -> op.getNumeroConta() == this.conta.getNumero())
        				.collect(Collectors.toList())
        				);*/

		extrato = new ListView<>(ultimasOperacoes);
        extrato.setPrefHeight(140);
        grid.add(extrato, 0, 4);

        tfSaldo = new TextField();
        tfSaldo.setDisable(true);
        tfSaldo.setText(""+conta.getSaldo());
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
        	  conta.deposito(valor);
        	  Operacao op = new Operacao(
					  (Calendar.getInstance().get(Calendar.DAY_OF_MONTH)),
					  (Calendar.getInstance().get(Calendar.MONTH) + 1),
					  (Calendar.getInstance().get(Calendar.YEAR)),
					  (Calendar.getInstance().get(Calendar.HOUR)),
					  (Calendar.getInstance().get(Calendar.MINUTE)),
					  (Calendar.getInstance().get(Calendar.SECOND)),
        			  conta.getNumero(),
        			  conta.getStatus(),
        			  valor,
        			  0);
              operacoes.add(op);        	  
        	  tfSaldo.setText(""+conta.getSaldo());
        	  operacoesConta.add(op);
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
          	  if (valor < 0.0 || valor > conta.getSaldo()) {
          		  throw new NumberFormatException("Saldo insuficiente");
          	  }
          	  conta.retirada(valor);
        	  Operacao op = new Operacao(
					  (Calendar.getInstance().get(Calendar.DAY_OF_MONTH)),
					  (Calendar.getInstance().get(Calendar.MONTH) + 1),
					  (Calendar.getInstance().get(Calendar.YEAR)),
					  (Calendar.getInstance().get(Calendar.HOUR)),
					  (Calendar.getInstance().get(Calendar.MINUTE)),
					  (Calendar.getInstance().get(Calendar.SECOND)),
        			  conta.getNumero(),
        			  conta.getStatus(),
        			  valor,
        			  1);
              operacoes.add(op);        	  
        	  tfSaldo.setText(""+conta.getSaldo());
        	  operacoesConta.add(op);
				update();
				extrato.setItems(ultimasOperacoes);
				tfSaldo.setText("" + conta.getSaldo());
          	}catch(NumberFormatException ex) {
  				Alert alert = new Alert(AlertType.WARNING);
  				alert.setTitle("Valor inválido !!");
  				alert.setHeaderText(null);
  				alert.setContentText("Valor inválido para operacao de débito!");

  				alert.showAndWait();
          	}        	
        });

        btnEstatistica.setOnAction(e->{
			TelaEstatistica toper = new TelaEstatistica(mainStage,cenaOperacoes,conta,operacoesConta);
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
		Persistencia.getInstance().saveOperacoes(operacoes);
		// WIll be replaced to another location later
		// Seleciona apenas o extrato da conta atual
		operacoesConta =
				new ArrayList(
						operacoes
								.stream()
								.filter(op -> op.getNumeroConta() == this.conta.getNumero())
								.collect(Collectors.toList())
				);

		// This will stay here
		ultimasOperacoes = FXCollections.observableArrayList(operacoesConta);
		categoria = "Categoria: "+conta.getStrStatus();
		cat.setText(categoria);
		limRetDiaria = "Limite retirada diaria: "+conta.getLimRetiradaDiaria();
		lim.setText(limRetDiaria);
	}

}
