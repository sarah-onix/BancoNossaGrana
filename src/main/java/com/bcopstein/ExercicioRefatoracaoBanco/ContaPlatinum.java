
package com.bcopstein.ExercicioRefatoracaoBanco;

public class ContaPlatinum implements StatusConta
{
    private final int LIM_GOLD_PLATINUM = 200000;
    private final int LIM_PLATINUM_GOLD = 100000;
	private final int LIM_GOLD_SILVER = 25000;
    private int numConta;
    private double saldo;
    private double saldoInicial;
    private String correntista;

    public ContaPlatinum(int numConta, double saldo, String nome, double saldoInicial)
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
    public int getStatus(){return 2;}

    @Override
    public String getStrStatus(){return "Platinum";}

    @Override
    public double getLimRetiradaDiaria(){return 500000.0;}

    @Override
    public StatusConta deposito(double valor)
    {
        saldo += valor * 1.025;
        return this;
    }

    @Override
    public StatusConta retirada (double valor)
    {
        saldo -= valor;
        if(saldo < LIM_PLATINUM_GOLD)
            return new ContaGold(numConta, saldo, correntista, saldoInicial);
        else if (saldo < LIM_GOLD_SILVER)
            return new ContaSilver(numConta, saldo, correntista, saldoInicial);
        return this;
    }

    @Override
    public double getSaldoInicial(){return 0; }
}