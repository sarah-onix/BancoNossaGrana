package com.bcopstein.ExercicioRefatoracaoBanco;

import com.bcopstein.ExercicioRefatoracaoBanco.ProjectExceptions.AccountWithdrawalLimitExceededException;
import com.bcopstein.ExercicioRefatoracaoBanco.ProjectExceptions.InvalidAccountException;
import com.bcopstein.ExercicioRefatoracaoBanco.ProjectExceptions.NotEnoughFundsException;

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

    public String getCorrentista(Integer numeroConta) {
        return contas.getCorrentista(numeroConta);
    }

    public String getStrStatus(Integer numeroConta) {
        return contas.getStrStatus(numeroConta);
    }

    public double getLimRetiradaDiaria(Integer numeroConta) {
        return contas.getLimRetiradaDiaria(numeroConta);
    }

    public double getSaldo(Integer numeroConta) {
        return contas.getSaldo(numeroConta);
    }

    public void deposito(Integer numeroConta, double valor) throws InvalidAccountException, NumberFormatException {
        if (Validations.isDepositValid(numeroConta, valor)) {
            contas.deposito(numeroConta, valor);
        }
    }

    public void retirada(Integer numeroConta, double valor) throws NotEnoughFundsException, InvalidAccountException, AccountWithdrawalLimitExceededException {
        if (Validations.isWithdrawalValid(numeroConta, valor)) {
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

    public double getSaqueDisponivelDoDia(int numeroConta) {
        //return contas.getSaqueDisponivelDoDia(numeroConta); IMPLEMENT THERE! Contas class::
        return 0;
    }

    public void save() {
        contas.save();
        operacoes.save();
    }
}
