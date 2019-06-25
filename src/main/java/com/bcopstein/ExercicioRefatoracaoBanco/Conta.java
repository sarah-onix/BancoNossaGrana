package com.bcopstein.ExercicioRefatoracaoBanco;

public class Conta {
	StatusConta statusConta;

	public Conta(int umNumero, String umNome) {
		statusConta = new ContaSilver(umNumero, 0.0, umNome, 0.0);
	}

	public Conta(int umNumero, String umNome, double umSaldo) {
		statusConta = new ContaSilver(umNumero, 0.0, umNome, umSaldo);
		statusConta = statusConta.deposito(umSaldo);
	}

	public double getSaldo() {
		return statusConta.getSaldo();
	}

	public Integer getNumero() {
		return statusConta.getNumero();
	}

	public String getCorrentista() 	{
		return statusConta.getCorrentista();
	}

	public int getStatus()	{
		return statusConta.getStatus();
	}

	public String getStrStatus() 	{
		return statusConta.getStrStatus();
	}

	public double getLimRetiradaDiaria() {
		return statusConta.getLimRetiradaDiaria();
	}

	public void deposito(double valor) {
		statusConta = statusConta.deposito(valor);
	}

	public void retirada(double valor) {
        statusConta = statusConta.retirada(valor);
    }

    public double getSaldoInicial () {
        return statusConta.getSaldoInicial();
	}

    @Override
    public String toString () {
        return "Conta [numero=" + statusConta.getNumero() + ", correntista=" + statusConta.getCorrentista() + ", saldo=" + statusConta.getSaldo() + ", status=" + statusConta.getStatus()
                        + "]";
        }
}
