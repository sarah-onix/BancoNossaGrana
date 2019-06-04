package com.bcopstein.ExercicioRefatoracaoBanco;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class Operacoes {

    private static Operacoes INSTANCE;
    private List<Operacao> operacoes;

    private Operacoes(List<Operacao> operacoes) {
        this.operacoes = operacoes;
    }

    public static void initialize(List<Operacao> operacoes) {
        if (INSTANCE == null) {
            INSTANCE = new Operacoes(operacoes);
        } else {
            throw new ExceptionInInitializerError("Already initialized");
        }
    }

    // stop method?

    public static Operacoes getInstance() {
        if (INSTANCE == null) {
            throw new ExceptionInInitializerError("Operacoes was never initialized");
        }
        return INSTANCE;
    }

    public List<Operacao> getOperacoes() {
        return operacoes;
    }

    public void createRetirada(int numeroConta, double valor, int statusConta) {
        Operacao op = new Operacao(
                (Calendar.getInstance().get(Calendar.DAY_OF_MONTH)),
                (Calendar.getInstance().get(Calendar.MONTH) + 1),
                (Calendar.getInstance().get(Calendar.YEAR)),
                (Calendar.getInstance().get(Calendar.HOUR)),
                (Calendar.getInstance().get(Calendar.MINUTE)),
                (Calendar.getInstance().get(Calendar.SECOND)),
                numeroConta,
                statusConta,
                valor,
                1);


        operacoes.add(op);
    }

    public void createDeposito(int numeroConta, double valor, int statusConta) {
        Operacao op = new Operacao(
                (Calendar.getInstance().get(Calendar.DAY_OF_MONTH)),
                (Calendar.getInstance().get(Calendar.MONTH) + 1),
                (Calendar.getInstance().get(Calendar.YEAR)),
                (Calendar.getInstance().get(Calendar.HOUR)),
                (Calendar.getInstance().get(Calendar.MINUTE)),
                (Calendar.getInstance().get(Calendar.SECOND)),
                numeroConta,
                statusConta,
                valor,
                0);
        operacoes.add(op);
    }


    public List<Operacao> getOperacoesDaConta(int numeroConta) {
        return new ArrayList(

                operacoes
                        .stream()
                        .filter(op -> op.getNumeroConta() == numeroConta)
                        .collect(Collectors.toList())
        );
    }

}
