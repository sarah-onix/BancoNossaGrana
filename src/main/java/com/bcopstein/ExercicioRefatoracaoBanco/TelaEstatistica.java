package com.bcopstein.ExercicioRefatoracaoBanco;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Calendar;
import java.util.List;

public class TelaEstatistica {

    private Stage mainStage;
    private Scene cenaEntrada;
    private Scene cenaEstatistica;
    private List<Operacao> operacoes;
    private ObservableList<String> monthsOfTheYear;
    private ComboBox monthSelection;
    private final Spinner<Integer> years = new Spinner<Integer>();


    public TelaEstatistica(Stage mainStage){
        this.mainStage = mainStage;

    }

    public Scene getTelaOperacoes(){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        monthsOfTheYear = FXCollections.observableArrayList(
                "Janeiro",
                "Fevereiro",
                "Marco",
                "Abril",
                "Maio",
                "Junho",
                "Julho",
                "Agosto",
                "Setembro",
                "Outubro",
                "Novembro",
                "Dezembro"
        );
        monthSelection = new ComboBox(monthsOfTheYear);
        monthSelection.setValue("Selecionar");
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1900, Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.YEAR) );
        years.setValueFactory(valueFactory);
        Button botaoBuscar = new Button("Buscar");
        Text sceneTitle = new Text("JOHN CARLOS DOE");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));


        // deve ter: saldo medio, total e quantidade de creditos, total e quantidade de debitos -> No ano/mes indicados

        Label saldoMedioLabel = new Label("Saldo médio no periodo");
        TextField saldoMedio = new TextField("123.14"+"$");
        Label totalDeCreditosLabel = new Label("Total de créditos no periodo");
        TextField totalDeCreditos = new TextField("500.00"+"$");
        Label totalDeDebitosLabel = new Label("Total de débitos no periodo");
        TextField totalDeDebitos = new TextField("123.14"+"$");
        TableView tableCreditos = new TableView();
        TableView tableDebitos = new TableView();
        TableColumn dataColumnCreditos = new TableColumn("Data");
        TableColumn valorColumnCreditos = new TableColumn("Valor");
        TableColumn dataColumnDebitos = new TableColumn("Data:");
        TableColumn valorColumnDebitos = new TableColumn("Valor:");
        tableDebitos.getColumns().clear();
        tableDebitos.getColumns().addAll(dataColumnDebitos,valorColumnDebitos);
        tableCreditos.getColumns().clear();
        tableCreditos.getColumns().addAll(dataColumnCreditos,valorColumnCreditos);


        //grid.add(monthSelection, 0,0);



        FlowPane root = new FlowPane();
        root.setHgap(1);
        root.setVgap(1);
        root.setPadding(new Insets(10));
        Label anoLabel = new Label("Ano:");
        Label mesLabel = new Label("Mes:");
        grid.add(anoLabel, 0,1);
        grid.add(sceneTitle,3,1);
        grid.add(years,1,1);
        grid.add(mesLabel, 0, 2);
        grid.add(monthSelection, 1, 2);
        grid.add(botaoBuscar, 1, 3);
        grid.add(tableDebitos, 1,4);
        grid.add(tableCreditos, 2,4);

        root.getChildren().add(grid);
        /*root.getChildren().add(sceneTitle);
        root.getChildren().addAll(anoLabel, years,mesLabel,grid);
        root.getChildren().add(botaoBuscar);*/

        //root.getChildren().addAll(mesLabel, grid);

        cenaEstatistica = new Scene(root, 800, 600);
        return cenaEstatistica;
    }


}
