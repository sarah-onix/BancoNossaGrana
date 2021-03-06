package com.bcopstein.ExercicioRefatoracaoBanco.entity;


public interface StatusConta
{
    double getSaldo();

    int getNumero();
    String getCorrentista();
    int getStatus();
    String getStrStatus();
    double getLimRetiradaDiaria();
    StatusConta deposito(double valor);
    StatusConta retirada(double valor);
}