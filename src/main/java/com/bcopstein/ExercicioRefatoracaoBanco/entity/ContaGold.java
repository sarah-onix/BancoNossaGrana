package com.bcopstein.ExercicioRefatoracaoBanco.entity;


public class ContaGold implements StatusConta
{
    
    private final int LIM_GOLD_PLATINUM = 200000;
    private final int LIM_GOLD_SILVER = 25000;
    private int numConta;
    private double saldo;
    private String correntista;

    public ContaGold(int numConta, double saldo, String nome)
    {
        this.numConta = numConta;
        this.saldo = saldo;
        correntista = nome;
    }

    @Override
    public double getSaldo(){return saldo;}

    @Override
    public int getNumero() {
        return numConta;
    }

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
            return new ContaPlatinum(numConta, saldo, correntista);
        return this;
    }

    @Override 
    public StatusConta retirada (double valor)
    {
        saldo -= valor;
        if(saldo < LIM_GOLD_SILVER)
            return new ContaSilver(numConta, saldo, correntista);
        return this;
    }



}