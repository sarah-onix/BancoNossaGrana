package com.bcopstein.ExercicioRefatoracaoBanco.view;

import com.bcopstein.ExercicioRefatoracaoBanco.entity.Operacao;
import com.bcopstein.ExercicioRefatoracaoBanco.service.BancoFacade;
import com.bcopstein.ExercicioRefatoracaoBanco.util.ProjectExceptions.AccountWithdrawalLimitExceededException;
import com.bcopstein.ExercicioRefatoracaoBanco.util.ProjectExceptions.InvalidAccountException;
import com.bcopstein.ExercicioRefatoracaoBanco.util.ProjectExceptions.NotEnoughFundsException;
import com.bcopstein.ExercicioRefatoracaoBanco.util.Validations;
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
	private ListView<Operacao> extrato;
	private Label cat;
	private Label lim;
	private String categoria;
	private String limRetDiaria;
	private TextField tfValorOperacao;
	private TextField tfSaldo;
	private int numeroConta;
	private ObservableList<Operacao> ultimasOperacoes;


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

		String dadosCorr = numeroConta + " : " + BancoFacade.getInstance().getCorrentista(numeroConta);
        Text scenetitle = new Text(dadosCorr);
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

		categoria = "Categoria: " + BancoFacade.getInstance().getStrStatus(numeroConta);
		limRetDiaria = "Limite retirada diaria: " + BancoFacade.getInstance().getLimRetiradaDiaria(numeroConta);

        cat = new Label(categoria);
        grid.add(cat, 0, 1);

        lim = new Label(limRetDiaria);
        grid.add(lim, 0, 2);
        
        Label tit = new Label("Ultimos movimentos");
        grid.add(tit,0,3);

		update();

		extrato = new ListView<Operacao>(ultimasOperacoes);
        extrato.setPrefHeight(140);
        grid.add(extrato, 0, 4);

        tfSaldo = new TextField();
        tfSaldo.setDisable(true);
		tfSaldo.setText("" + BancoFacade.getInstance().getSaldo(numeroConta));
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
				if (Validations.isDepositValid(numeroConta, valor)) {

					BancoFacade.getInstance().deposito(numeroConta, valor);
					tfSaldo.setText("" + BancoFacade.getInstance().getSaldo(numeroConta));
					update();
					extrato.setItems(ultimasOperacoes);

				}
        	}catch(NumberFormatException ex) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Valor inválido !!");
				alert.setHeaderText(null);
				alert.setContentText("Valor inválido para operacao de crédito!!");

				alert.showAndWait();
			} catch (InvalidAccountException ex) {
				ex.printStackTrace();
			}
		});
        
        btnDebito.setOnAction(e->{
        	try {
          	  double valor = Integer.parseInt(tfValorOperacao.getText());
					BancoFacade.getInstance().retirada(numeroConta, valor);
					tfSaldo.setText("" + BancoFacade.getInstance().getSaldo(numeroConta));
					update();
					extrato.setItems(ultimasOperacoes);
					tfSaldo.setText("" + BancoFacade.getInstance().getSaldo(numeroConta));
			} catch (AccountWithdrawalLimitExceededException limEx) {
  				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Limite excedido !!");
  				alert.setHeaderText(null);
				alert.setContentText("O valor do saque excede o máximo do seu limite diário!");

  				alert.showAndWait();
			} catch (NotEnoughFundsException noFundsEx) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Saldo Insuficiente !!");
				alert.setHeaderText(null);
				alert.setContentText("Não há saldo suficiente para sacar esse valor!");

				alert.showAndWait();
			} catch (NumberFormatException nfEx) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Valor inválido !!");
				alert.setHeaderText(null);
				alert.setContentText("Valor inválido para operacao de débito!");

				alert.showAndWait();
			} catch (InvalidAccountException ex) {
				ex.printStackTrace();
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
		// This will stay here
		ultimasOperacoes = FXCollections.observableArrayList(BancoFacade.getInstance().getOperacoesDaConta(numeroConta));
		categoria = "Categoria: " + BancoFacade.getInstance().getStrStatus(numeroConta);
		cat.setText(categoria);
		limRetDiaria = "Limite retirada diaria: " + BancoFacade.getInstance().getLimRetiradaDiaria(numeroConta);
		lim.setText(limRetDiaria);
	}

}
