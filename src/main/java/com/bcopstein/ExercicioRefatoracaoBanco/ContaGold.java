package com.bcopstein.ExercicioRefatoracaoBanco;


public class ContaGold implements StatusConta
{
    
    private final int LIM_GOLD_PLATINUM = 200000;
    private final int LIM_GOLD_SILVER = 25000;
    private int numConta;
    private double saldo;
    private double saldoInicial;
    private String correntista;

    public ContaGold(int numConta, double saldo, String nome, double saldoInicial)
    {
        this.numConta = numConta;
        this.saldo = saldo;
        correntista = nome;
        this.saldoInicial = saldoInicial;
    }

    @Override
    public double getSaldo(){return saldo;}

    @Override
    public Integer getNumero(){return numConta;}

    @Override 
    public String getCorrentista(){return correntista;}

    @Override
    public int getStatus(){return 1;}

    @Override
    public String getStrStatus(){return "Gold";}

    @Override
    public double getLimRetiradaDiaria(){return 100000.0;}

    @Override
    public StatusConta deposito(double valor)
    {
        saldo += valor *1.01;
        if(saldo >= LIM_GOLD_PLATINUM)
            return new ContaPlatinum(numConta, saldo, correntista, saldoInicial);
        return this;
    }

    @Override 
    public StatusConta retirada (double valor)
    {
        saldo -= valor;
        if(saldo < LIM_GOLD_SILVER)
            return new ContaSilver(numConta, saldo, correntista, saldoInicial);
        return this;
    }



    @Override
    public double getSaldoInicial(){return saldoInicial;}
}