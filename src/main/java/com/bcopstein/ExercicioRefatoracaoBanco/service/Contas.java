package com.bcopstein.ExercicioRefatoracaoBanco.service;

import com.bcopstein.ExercicioRefatoracaoBanco.entity.Conta;
import com.bcopstein.ExercicioRefatoracaoBanco.entity.Operacao;
import com.bcopstein.ExercicioRefatoracaoBanco.repository.Persistencia;
import com.bcopstein.ExercicioRefatoracaoBanco.util.ProjectExceptions.InvalidAccountException;

import javax.management.InstanceAlreadyExistsException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Contas {

    private Map<Integer, Conta> contas;

    private static boolean alreadyInstantiated;

    private Operacoes operacoes;

    private Persistencia persistencia;


    public Contas(Persistencia persistencia, Operacoes operacoes, boolean isTest) {
        this.persistencia = persistencia;
        contas = this.persistencia.loadContas();
        this.operacoes = operacoes;
    }

    public Contas(Persistencia persistencia, Operacoes operacoes) throws InstanceAlreadyExistsException {
        if (alreadyInstantiated == true) {
            throw new InstanceAlreadyExistsException();
        }
        alreadyInstantiated = true;
        this.persistencia = persistencia;
        contas = this.persistencia.loadContas();
        this.operacoes = operacoes;
    }

    public void save() {
        persistencia.saveContas(contas.values());
    }

    public boolean contaExists(int nroConta) {
        if (contas.containsKey(nroConta)) {
            return true;
        }
        return false;
    }

    public String getCorrentista(int nroConta) throws InvalidAccountException {
        if (contas.containsKey(nroConta)) {
            return contas.get(nroConta).getCorrentista();
        }
        return "CONTA_INEXISTENTE";
    }

    public String getStrStatus(int nroConta) {
        return contas.get(nroConta).getStrStatus();
    }

    public double getLimRetiradaDiaria(int nroConta) {
        return contas.get(nroConta).getLimRetiradaDiaria();
    }

    public double getSaldo(int nroConta) {
        return contas.get(nroConta).getSaldo();
    }

    public double getTotalRetiradaDia(int numConta)
    {
        LinkedList<Operacao> operacoesDia = (LinkedList<Operacao>) operacoes.getOperacoesDia(numConta);
        double totalDia = 0;
        for( Operacao o : operacoesDia)
        {
            if (o.getTipoOperacao() == 1) //1 é o código de operação de débito
                totalDia += o.getValorOperacao();
        }
        return totalDia;
    }

    public void retirada(int numeroConta, double valor) {
        contas.get(numeroConta).retirada(valor);
        operacoes.createRetirada(numeroConta, valor, contas.get(numeroConta).getStatus());
    }

    public void deposito(int numeroConta, double valor) {
        Conta conta = contas.get(numeroConta);
        conta.deposito(valor);
        operacoes.createDeposito(numeroConta, valor, conta.getStatus());
    }

    public List<Operacao> getOperacoesNoMes(int numeroConta, int monthValue, int yearValue) {
        return new ArrayList<>(
                operacoes.getOperacoesDaConta(numeroConta)
                        .stream()
                        .filter(op -> op.getAno() == yearValue)
                        .filter(op -> op.getMes() == monthValue)
                        .collect(Collectors.toList())
        );
    }

    public List<Operacao> getDebitosNoMes(int numeroConta, int monthValue, int yearValue) {
        return new ArrayList<>(
                getOperacoesNoMes(numeroConta, monthValue, yearValue)
                        .stream()
                        .filter(op -> op.getTipoOperacao() == 1)
                        .collect(Collectors.toList())
        );
    }

    public List<Operacao> getCreditosNoMes(int numeroConta, int monthValue, int yearValue) {
        return new ArrayList<>(
                getOperacoesNoMes(numeroConta, monthValue, yearValue)
                        .stream()
                        .filter(op -> op.getTipoOperacao() == 0)
                        .collect(Collectors.toList())
        );
    }

    public double getValorTotalDeDebitosNoMes(int numeroConta, int monthValue, int yearValue) {
        double totalDebitosDoMes = 0;
        for (Operacao x : getDebitosNoMes(numeroConta, monthValue, yearValue)) {
            totalDebitosDoMes += x.getValorOperacao();
        }
        return totalDebitosDoMes;
    }

    public double getValorTotalDeCreditosNoMes(int numeroConta, int monthValue, int yearValue) {
        double totalCreditosDoMes = 0;
        for (Operacao x : getCreditosNoMes(numeroConta, monthValue, yearValue)) {
            totalCreditosDoMes += x.getValorOperacao();
        }
        return totalCreditosDoMes;
    }


    public double getSaldoMedioNoMes(int numeroConta, int monthValue, int yearValue) {
        boolean over = false;
        List<Operacao> operacoesBeforeYear =
                new ArrayList<>(
                        operacoes.getOperacoesDaConta(numeroConta)
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
                        operacoes.getOperacoesDaConta(numeroConta)
                                .stream()
                                .filter(op -> op.getAno() == yearValue)
                                .filter(op -> op.getMes() == monthValue)
                                .collect(Collectors.toList())
                );

            double saldoAnterior = 0;
            for(Operacao x:operacoesBeforeDate){
                if (x.getTipoOperacao() == 0) {
                    saldoAnterior += x.getValorOperacao();
                } else {
                    saldoAnterior -= x.getValorOperacao();
                }
            }


            double saldoNoMes = 0;
            double saldoTotalDoMes = 0;
            for(int i = 1;i<=30;i++){
                int dia = i;
                List<Operacao> operacoesDodia =
                        new ArrayList<>(
                                operacoesNoMesTemp
                                        .stream()
                                        .filter(op -> op.getDia() == dia)
                                        .collect(Collectors.toList())
                        );
                for(Operacao x : operacoesDodia){
                    if (x.getTipoOperacao() == 0) {
                        saldoAnterior += x.getValorOperacao();
                    } else {
                        saldoAnterior -= x.getValorOperacao();
                    }

                }
                saldoTotalDoMes += saldoAnterior;
            }

            return saldoTotalDoMes / 30;
        }

}
