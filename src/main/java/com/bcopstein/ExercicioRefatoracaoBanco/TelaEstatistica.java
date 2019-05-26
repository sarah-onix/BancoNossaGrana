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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class TelaEstatistica {

    private Stage mainStage;
    private Scene cenaOperacoes;
    private List<Operacao> operacoesConta;
    private Conta conta;
    private ComboBox<String> monthSelection;
    private Spinner<Integer> years;
    private TableView tableCreditos = new TableView();
    private TableView tableDebitos = new TableView();
    private Label totalDeCreditosLabel;
    private Label totalDeDebitosLabel;
    private TextField saldoMedio;

    public TelaEstatistica(Stage mainStage, Scene cenaOperacoes, Conta conta, List<Operacao> operacoesConta) {
        this.mainStage = mainStage;
        this.operacoesConta = operacoesConta;
        this.conta = conta;
        this.cenaOperacoes = cenaOperacoes;
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
        botaoVoltar.resize(2000, 1000);

        // **********
        // shows client name
        Label clientName = new Label(conta.getCorrentista() + "     ");
        clientName.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        // sets output layout
        Label saldoMedioLabel = new Label("Saldo médio no periodo");
        saldoMedio = new TextField("123.14" + "$");
        saldoMedio.setPrefWidth(100);
        saldoMedio.setMaxWidth(100);
        totalDeCreditosLabel = new Label("Total de créditos no periodo: $" + "number");
        totalDeDebitosLabel = new Label("Total de débitos no periodo: $" + "number2");
        TableColumn dataColumnCreditos = new TableColumn("Data");
        TableColumn valorColumnCreditos = new TableColumn("Valor R$:");
        TableColumn dataColumnDebitos = new TableColumn("Data:");
        TableColumn valorColumnDebitos = new TableColumn("Valor R$:");
        dataColumnDebitos.setCellValueFactory(new PropertyValueFactory<>("dataHora"));
        valorColumnDebitos.setCellValueFactory(new PropertyValueFactory<>("valorOperacao"));
        dataColumnCreditos.setCellValueFactory(new PropertyValueFactory<>("dataHora"));
        valorColumnCreditos.setCellValueFactory(new PropertyValueFactory<>("valorOperacao"));
        dataColumnCreditos.prefWidthProperty().bind(tableCreditos.widthProperty().multiply(0.6));
        valorColumnCreditos.prefWidthProperty().bind(tableCreditos.widthProperty().multiply(0.4));
        dataColumnDebitos.prefWidthProperty().bind(tableCreditos.widthProperty().multiply(0.6));
        valorColumnDebitos.prefWidthProperty().bind(tableCreditos.widthProperty().multiply(0.4));
        dataColumnCreditos.setResizable(false);
        dataColumnDebitos.setResizable(false);
        tableCreditos.setEditable(false);
        tableDebitos.setEditable(false);
        tableCreditos.setMaxHeight(300);
        tableCreditos.setMinHeight(300);
        tableDebitos.setMaxHeight(300);
        tableDebitos.setMinHeight(300);
        tableDebitos.getColumns().clear();
        tableDebitos.getColumns().addAll(dataColumnDebitos,valorColumnDebitos);
        tableCreditos.getColumns().clear();
        tableCreditos.getColumns().addAll(dataColumnCreditos,valorColumnCreditos);
        tableCreditos.setEditable(false);
        tableDebitos.setEditable(false);
        saldoMedio.setEditable(false);
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
        return cenaEstatistica;
    }

    /**
     * @return int value of the selected month from the ComboBox monthSelection (1..12) for (Janeiro..Dezembro)
     */
    public int getSelectedMonthValue() {
        return CalendarTranslator.getMonthValue(monthSelection.getValue());
    }

    /**
     * @return int value of the selected year from the Spinner years
     */
    public int getSelectedYearValue() {
        return years.getValue();
    }

    private void setQuery() {
        // Business Logic, this part will be replaced from here later...
        //
        List<Operacao> operacoesNoMes =
                new ArrayList<>(
                        operacoesConta
                                .stream()
                                .filter(op -> op.getAno() == getSelectedYearValue())
                                .filter(op -> op.getMes() == getSelectedMonthValue())
                                .collect(Collectors.toList())
                );

        List<Operacao> debitosDoMes =
                new ArrayList<>(
                        operacoesNoMes
                                .stream()
                                .filter(op -> op.getTipoOperacao() == 1)
                                .collect(Collectors.toList())
                );
        //
        List<Operacao> creditosDoMes =
                new ArrayList<>(
                        operacoesNoMes
                                .stream()
                                .filter(op -> op.getTipoOperacao() == 0)
                                .collect(Collectors.toList())
                );

        //
        double totalDebitosDoMes = 0;
        for (Operacao x : debitosDoMes) {
            totalDebitosDoMes += x.getValorOperacao();
        }

        //
        double totalCreditosDoMes = 0;
        for (Operacao x : creditosDoMes) {
            totalCreditosDoMes += x.getValorOperacao();
        }

        // Calcula saldo no dia da primeira operacao do mes/ano tal
        // Assume que os dados estao ordenados do mais antigo para mais recente
        // Uma logica mais aprimorada sera necessaria caso a premissa acima nao seja mais verdade
        int yearValue = getSelectedYearValue();
        int monthValue = getSelectedMonthValue();

        boolean over = false;
        List<Operacao> operacoesBeforeYear =
                new ArrayList<>(
                        operacoesConta
                                .stream()
                                .filter(op -> op.getAno() <= yearValue)
                                .collect(Collectors.toList())
                );
        List<Operacao> operacoesBeforeDate = new ArrayList<>();
        for (Operacao x : operacoesBeforeYear) {
            if (x.getAno() == yearValue) {
                if (x.getMes() < monthValue) {
                    operacoesBeforeDate.add(x);
                }
            } else {
                operacoesBeforeDate.add(x);
            }
        }
        List<Operacao> operacoesNoMesTemp =
                new ArrayList<>(
                        operacoesConta
                                .stream()
                                .filter(op -> op.getAno() == yearValue)
                                .filter(op -> op.getMes() == monthValue)
                                .collect(Collectors.toList())
                );
        double saldoMedioNoMes;
        if (operacoesNoMesTemp.isEmpty()) {
            saldoMedioNoMes = 0;
        } else {
            int day = 0;
            int hour = 0;
            int minute = 0;
            int second = 0;
            Operacao firstOp = null;
            boolean first = true;
            for (Operacao x : operacoesNoMesTemp) {
                if (first) {
                    first = false;
                    day = x.getDia();
                    hour = x.getHora();
                    minute = x.getMinuto();
                    second = x.getSegundo();
                    firstOp = x;
                }
                if (x.getDia() < day) {
                    day = x.getDia();
                    hour = x.getHora();
                    minute = x.getMinuto();
                    second = x.getSegundo();
                    firstOp = x;
                }
                if (x.getDia() == day) {
                    if (x.getHora() == hour) {
                        if (x.getMinuto() < minute) {
                            day = x.getDia();
                            hour = x.getHora();
                            minute = x.getMinuto();
                            second = x.getSegundo();
                            firstOp = x;
                        } else if (x.getMinuto() == minute) {
                            if (x.getSegundo() == second) {
                                break;
                            } else if (x.getSegundo() < second) {
                                day = x.getDia();
                                hour = x.getHora();
                                minute = x.getMinuto();
                                second = x.getSegundo();
                                firstOp = x;
                            }
                        } else if (x.getMinuto() < minute) {
                            day = x.getDia();
                            hour = x.getHora();
                            minute = x.getMinuto();
                            second = x.getSegundo();
                            firstOp = x;
                        }
                    } else if (x.getHora() < hour) {
                        day = x.getDia();
                        hour = x.getHora();
                        minute = x.getMinuto();
                        second = x.getSegundo();
                        firstOp = x;
                    }
                }
            }
            operacoesBeforeDate.add(firstOp); // now it is before inclusive

            List<Double> saldosNoMes = new ArrayList<>();
            for (Operacao x : operacoesNoMesTemp) {
                if (x.getTipoOperacao() == 0) {
                    saldosNoMes.add(x.getValorOperacao());
                } else {
                    saldosNoMes.add(-x.getValorOperacao());
                }
            }

            double soma = 0;
            for (Double x : saldosNoMes) {
                soma += x;
            }
            saldoMedioNoMes = soma / 30;
        }


        // this will be kept here
        totalDeCreditosLabel.setText("Total de créditos no periodo: $" + totalCreditosDoMes);
        totalDeDebitosLabel.setText("Total de créditos no periodo: $" + totalDebitosDoMes);
        tableCreditos.setItems(FXCollections.observableArrayList(creditosDoMes));
        tableDebitos.setItems(FXCollections.observableArrayList(debitosDoMes));
        saldoMedio.setText(String.format("%.2f", saldoMedioNoMes));


    }


}
