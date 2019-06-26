package com.bcopstein.ExercicioRefatoracaoBanco.service;

import com.bcopstein.ExercicioRefatoracaoBanco.entity.Operacao;
import com.bcopstein.ExercicioRefatoracaoBanco.repository.Persistencia;
import com.bcopstein.ExercicioRefatoracaoBanco.util.ProjectExceptions.AccountWithdrawalLimitExceededException;
import com.bcopstein.ExercicioRefatoracaoBanco.util.ProjectExceptions.InvalidAccountException;
import com.bcopstein.ExercicioRefatoracaoBanco.util.ProjectExceptions.NotEnoughFundsException;
import com.bcopstein.ExercicioRefatoracaoBanco.util.Validations;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;

public class BancoFacade {

    private static BancoFacade INSTANCE;

    private Contas contas;
    private Operacoes operacoes;

    private BancoFacade() {
        try {
            Persistencia persistencia = new Persistencia();
            operacoes = new Operacoes(persistencia);
            contas = new Contas(persistencia, operacoes);
        } catch (InstanceAlreadyExistsException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static BancoFacade getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BancoFacade();
        }
        return INSTANCE;
    }


    public boolean contaExists(int numeroConta) {
        return contas.contaExists(numeroConta);
    }

    public List<Operacao> getOperacoesDaConta(int numeroConta) {
        return operacoes.getOperacoesDaConta(numeroConta);
    }

    public List<Operacao> getOperacoesDia(int numConta) {
        return operacoes.getOperacoesDia(numConta);
    }

    public String getCorrentista(int numeroConta) {
        try {
            return contas.getCorrentista(numeroConta);
        } catch (InvalidAccountException e) {
            return "NON-EXISTENT ACCOUNT!";
        }
    }

    public String getStrStatus(int numeroConta) {
        return contas.getStrStatus(numeroConta);
    }

    public double getTotalRetiradaDia(int numConta) {
        return contas.getTotalRetiradaDia(numConta);
    }

    public double getLimRetiradaDiaria(int numeroConta) {
        return contas.getLimRetiradaDiaria(numeroConta);
    }

    public double getSaldo(int numeroConta) {
        return contas.getSaldo(numeroConta);
    }

    public void deposito(int numeroConta, double valor) throws InvalidAccountException, NumberFormatException {
        if (Validations.isDepositValid(numeroConta, valor)) {
            contas.deposito(numeroConta, valor);
        }
    }

    public void retirada(Integer numeroConta, double valor) throws NotEnoughFundsException, InvalidAccountException, AccountWithdrawalLimitExceededException {
        if (Validations.isWithdrawalValid(contas.getTotalRetiradaDia(numeroConta), contas.getLimRetiradaDiaria(numeroConta), contas.getSaldo(numeroConta), valor)) {
            contas.retirada(numeroConta, valor);
        }
    }

    public double getValorTotalDeCreditosNoMes(Integer numeroConta, int monthValue, Integer yearValue) {
        return contas.getValorTotalDeCreditosNoMes(numeroConta, monthValue, yearValue);
    }

    public double getValorTotalDeDebitosNoMes(Integer numeroConta, int monthValue, Integer yearValue) {
        return contas.getValorTotalDeDebitosNoMes(numeroConta, monthValue, yearValue);
    }

    public List<Operacao> getCreditosNoMes(Integer numeroConta, int monthValue, Integer yearValue) {
        return contas.getCreditosNoMes(numeroConta, monthValue, yearValue);
    }

    public List<Operacao> getDebitosNoMes(Integer numeroConta, int monthValue, Integer yearValue) {
        return contas.getCreditosNoMes(numeroConta, monthValue, yearValue);
    }

    public double getSaldoMedioNoMes(Integer numeroConta, int monthValue, Integer value) {
        return contas.getSaldoMedioNoMes(numeroConta, monthValue, value);
    }

    public void save() {
        contas.save();
        operacoes.save();
    }
}
