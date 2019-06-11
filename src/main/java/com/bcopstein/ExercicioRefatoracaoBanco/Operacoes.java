package com.bcopstein.ExercicioRefatoracaoBanco;

import javax.management.InstanceAlreadyExistsException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Operacoes {

    private List<Operacao> operacoes;

    private Persistencia persistencia;

    private static boolean alreadyInstantiated;

    public Operacoes(Persistencia persistencia, boolean isTest) {
        this.persistencia = persistencia;
        operacoes = this.persistencia.loadOperacoes();
    }

    public Operacoes(Persistencia persistencia) throws InstanceAlreadyExistsException {
        if (alreadyInstantiated == true) {
            throw new InstanceAlreadyExistsException();
        }
        alreadyInstantiated = true;
        this.persistencia = persistencia;
        operacoes = this.persistencia.loadOperacoes();
    }

    public void save() {
        persistencia.saveOperacoes(operacoes);
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
        return new LinkedList<>(
                operacoes
                        .stream()
                        .filter(op -> op.getNumeroConta() == numeroConta)
                        .collect(Collectors.toList())
        );
    }

    public List<Operacao> getOperacoesDia(int numeroConta)
    {
        return new LinkedList<>(
            operacoes  
                    .stream()
                    .filter(op -> op.getNumeroConta() == numeroConta)
                    .filter(op -> op.getDia() == Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
                    .filter(op -> op.getMes() == Calendar.getInstance().get(Calendar.MONTH) + 1)
                    .collect(Collectors.toList())
        );
    }

}
