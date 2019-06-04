package com.bcopstein.ExercicioRefatoracaoBanco;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Contas {

    private static Contas INSTANCE;
    private Map<Integer, Conta> contas;

    private Contas(Map<Integer, Conta> contas) {
        this.contas = contas;
    }

    public static void initialize(Map<Integer, Conta> contas) {
        if (INSTANCE == null) {
            INSTANCE = new Contas(contas);
        } else {
            throw new ExceptionInInitializerError("Already initialized");
        }
    }

    public static void reset() {
        INSTANCE = null;
    }

    public static Contas getInstance() {
        if (INSTANCE == null) {
            throw new ExceptionInInitializerError("Contas was never initialized");
        }
        return INSTANCE;
    }

    public Collection<Conta> getAllContas() {
        return contas.values();
    }

    public boolean contaExists(int nroConta) {
        if (contas.containsKey(nroConta)) {
            return true;
        }
        return false;
    }

    public String getCorrentista(int nroConta) {
        if (!contas.containsKey(nroConta)) {
            throw new NullPointerException("Correntista inexistente");
        }
        return contas.get(nroConta).getCorrentista();
    }

    public String getStrStatus(int nroConta) {
        if (!contas.containsKey(nroConta)) {
            throw new NullPointerException("Correntista inexistente");
        }
        return contas.get(nroConta).getStrStatus();
    }

    public double getLimRetiradaDiaria(int nroConta) {
        if (!contas.containsKey(nroConta)) {
            throw new NullPointerException("Correntista inexistente");
        }
        return contas.get(nroConta).getLimRetiradaDiaria();
    }

    public double getSaldo(int nroConta) {
        if (!contas.containsKey(nroConta)) {
            throw new NullPointerException("Correntista inexistente");
        }
        return contas.get(nroConta).getSaldo();
    }


    public int retirada(int numeroConta, double valor) {
        // tratamento de exceptions aqui ou a partir do return
        contas.get(numeroConta).deposito(valor);
        Operacoes.getInstance().createRetirada(numeroConta, valor, contas.get(numeroConta).getStatus());
        return 1; // return sucess; evaluate later
    }

    public Conta getConta(int numeroConta) {
        return contas.get(numeroConta);
    }

    public void deposito(int numeroConta, double valor) {
        Conta conta = contas.get(numeroConta);
        conta.deposito(valor);
        Operacoes.getInstance().createDeposito(numeroConta, valor, conta.getStatus());
    }

    public List<Operacao> getOperacoesNoMes(int numeroConta, int monthValue, int yearValue) {
        return new ArrayList<>(
                Operacoes.getInstance().getOperacoesDaConta(numeroConta)
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
                        Operacoes.getInstance().getOperacoesDaConta(numeroConta)
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
                        Operacoes.getInstance().getOperacoesDaConta(numeroConta)
                                .stream()
                                .filter(op -> op.getAno() == yearValue)
                                .filter(op -> op.getMes() == monthValue)
                                .collect(Collectors.toList())
                );
        double saldoMedioNoMes;
        if (operacoesNoMesTemp.isEmpty()) {
            return 0;
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
            return soma / 30;
        }

    }

}
