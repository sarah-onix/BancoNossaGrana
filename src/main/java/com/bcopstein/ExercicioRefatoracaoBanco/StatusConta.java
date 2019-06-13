package com.bcopstein.ExercicioRefatoracaoBanco;


public interface StatusConta
{
    double getSaldo();
    Integer getNumero();
    String getCorrentista();
    int getStatus();
    String getStrStatus();
    double getLimRetiradaDiaria();
    StatusConta deposito(double valor);
    StatusConta retirada(double valor);
}