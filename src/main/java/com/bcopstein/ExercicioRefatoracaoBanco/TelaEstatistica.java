package com.bcopstein.ExercicioRefatoracaoBanco;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.Calendar;
import java.util.List;

public class TelaEstatistica {

    private Stage mainStage;
    private Scene cenaOperacoes;
    private ComboBox<String> monthSelection;
    private Spinner<Integer> years;
    private TableView tableCreditos = new TableView();
    private TableView tableDebitos = new TableView();
    private Label totalDeCreditosLabel;
    private Label totalDeDebitosLabel;
    private TextField saldoMedio;
    private int numeroConta;

    public TelaEstatistica(Stage mainStage, Scene cenaOperacoes, int numeroConta) {
        this.mainStage = mainStage;
        this.cenaOperacoes = cenaOperacoes;
        this.numeroConta = numeroConta;
    }

    public Scene getTelaEstatistica() {
        // Sets grid
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        // Initializes input layouts
        ObservableList<String> monthsOfTheYear = FXCollections.observableArrayList(
                "Janeiro",
                "Fevereiro",
                "Março",
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
        monthSelection.setValue(CalendarTranslator.getCurrentMonth());
        years = new Spinner<Integer>();
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1900, Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.YEAR) );
        years.setValueFactory(valueFactory);
        Button botaoBuscar = new Button("Buscar");
        Button botaoVoltar = new Button("Voltar");
        // **********
        // shows client name
        Label clientName = new Label(BancoFacade.getInstance().getCorrentista(numeroConta) + "     ");
        clientName.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        // sets JavaFx data objects
        Label saldoMedioLabel = new Label("Saldo médio no periodo");
        saldoMedio = new TextField("000.00" + "$");
        totalDeCreditosLabel = new Label("Total de créditos no periodo: $" + "000.00");
        totalDeDebitosLabel = new Label("Total de débitos no periodo: $" + "000.00");
        TableColumn dataColumnCreditos = new TableColumn("Data:");
        TableColumn valorColumnCreditos = new TableColumn("Valor R$:");
        TableColumn dataColumnDebitos = new TableColumn("Data:");
        TableColumn valorColumnDebitos = new TableColumn("Valor R$:");
        // Maps data content to tables
        dataColumnDebitos.setCellValueFactory(new PropertyValueFactory<>("dataHora"));
        valorColumnDebitos.setCellValueFactory(new PropertyValueFactory<>("valorOperacao"));
        dataColumnCreditos.setCellValueFactory(new PropertyValueFactory<>("dataHora"));
        valorColumnCreditos.setCellValueFactory(new PropertyValueFactory<>("valorOperacao"));
        // **
        // Resize to make dataCollumn 60% and valorColumn 40%
        dataColumnCreditos.prefWidthProperty().bind(tableCreditos.widthProperty().multiply(0.6));
        valorColumnCreditos.prefWidthProperty().bind(tableCreditos.widthProperty().multiply(0.4));
        dataColumnDebitos.prefWidthProperty().bind(tableCreditos.widthProperty().multiply(0.6));
        valorColumnDebitos.prefWidthProperty().bind(tableCreditos.widthProperty().multiply(0.4));
        // **
        // blocks alterations to data objects
        dataColumnCreditos.setResizable(false);
        dataColumnDebitos.setResizable(false);
        tableCreditos.setEditable(false);
        tableDebitos.setEditable(false);
        saldoMedio.setEditable(false);
        // **
        // Resize tables to fit window properly
        tableCreditos.setMaxHeight(300);
        tableCreditos.setMinHeight(300);
        tableDebitos.setMaxHeight(300);
        tableDebitos.setMinHeight(300);
        // **
        tableDebitos.getColumns().clear();
        tableDebitos.getColumns().addAll(dataColumnDebitos,valorColumnDebitos);
        tableCreditos.getColumns().clear();
        tableCreditos.getColumns().addAll(dataColumnCreditos,valorColumnCreditos);
        //**********
        // updates tables for current month/year
        setQuery();
        //**********
        // Add items to scene
        FlowPane root = new FlowPane();
        root.setHgap(1);
        root.setVgap(1);
        root.setPadding(new Insets(10));
        Label anoLabel = new Label("Ano:");
        Label mesLabel = new Label("Mes:");
        grid.add(anoLabel, 0,1);
        grid.add(clientName, 2, 1);
        grid.add(years,1,1);
        grid.add(mesLabel, 0, 2);
        grid.add(monthSelection, 1, 2);
        grid.add(botaoBuscar, 1, 3);
        grid.add(botaoVoltar, 2, 7);
        grid.add(totalDeDebitosLabel, 1, 4);
        grid.add(totalDeCreditosLabel, 2, 4);
        grid.add(tableDebitos, 1, 5);
        grid.add(tableCreditos, 2, 5);
        grid.add(saldoMedioLabel, 1, 6);
        grid.add(saldoMedio, 1, 7);
        root.getChildren().add(grid);
        Scene cenaEstatistica = new Scene(root, 700, 600);
        // ******
        // behaviors
        botaoBuscar.setOnAction(e -> {
            setQuery();
        });
        botaoVoltar.setOnAction(e -> {
            mainStage.setScene(cenaOperacoes);
        });
        // ***
        return cenaEstatistica;
    }

    private void setQuery() {
        // sets values
        double totalCreditosNoMes = BancoFacade.getInstance().getValorTotalDeCreditosNoMes(numeroConta, CalendarTranslator.getMonthValue(monthSelection.getValue()), years.getValue());
        double totalDebitosNoMes = BancoFacade.getInstance().getValorTotalDeDebitosNoMes(numeroConta, CalendarTranslator.getMonthValue(monthSelection.getValue()), years.getValue());
        List<Operacao> creditosDoMes = BancoFacade.getInstance().getCreditosNoMes(numeroConta, CalendarTranslator.getMonthValue(monthSelection.getValue()), years.getValue());
        List<Operacao> debitosDoMes = BancoFacade.getInstance().getDebitosNoMes(numeroConta, CalendarTranslator.getMonthValue(monthSelection.getValue()), years.getValue());
        // sets to javaFx objects
        totalDeCreditosLabel.setText("Total de créditos no periodo: $" + totalCreditosNoMes);
        totalDeDebitosLabel.setText("Total de Débitos no periodo: $" + totalDebitosNoMes);
        tableCreditos.setItems(FXCollections.observableArrayList(creditosDoMes));
        tableDebitos.setItems(FXCollections.observableArrayList(debitosDoMes));
        saldoMedio.setText(String.format("%.2f", BancoFacade.getInstance().getSaldoMedioNoMes(numeroConta, CalendarTranslator.getMonthValue(monthSelection.getValue()), years.getValue())));
    }
}
