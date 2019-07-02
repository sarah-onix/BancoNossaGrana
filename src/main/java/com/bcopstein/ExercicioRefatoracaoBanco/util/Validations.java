package com.bcopstein.ExercicioRefatoracaoBanco.util;

import com.bcopstein.ExercicioRefatoracaoBanco.util.ProjectExceptions.AccountWithdrawalLimitExceededException;
import com.bcopstein.ExercicioRefatoracaoBanco.util.ProjectExceptions.InvalidAccountException;
import com.bcopstein.ExercicioRefatoracaoBanco.util.ProjectExceptions.NotEnoughFundsException;

public class Validations {


    public static boolean isNumeroContaValid(int numeroConta) throws InvalidAccountException {
        if (numeroConta > 0) {
            return true;
        }
        throw new InvalidAccountException("Número da conta é INVALIDO");
    }

    public static boolean isValueValid(double value) {
        if (value < 0.0) {
            throw new NumberFormatException("Valor inválido");
        } else
            return true;
    }

    public static boolean hasEnoughFundsAfterWithdrawal(double saldoDaConta, double value) throws NotEnoughFundsException, InvalidAccountException {
        if (saldoDaConta < value) {
            throw new NotEnoughFundsException();
        }
        return true;
    }

    public static boolean hasEnoughQuota(double totalJaRetiradoDoDia, double limiteDeRetiradaDiario, double value) throws AccountWithdrawalLimitExceededException
    {
        if (value > (limiteDeRetiradaDiario - totalJaRetiradoDoDia))
        {
            throw new AccountWithdrawalLimitExceededException();
        } else
            return true;
        
    }

    public static boolean isWithdrawalValid(double totalJaRetiradoDoDia, double limiteDeRetiradaDiario, double saldoAntes, double valorRetirada) throws AccountWithdrawalLimitExceededException, NotEnoughFundsException, NumberFormatException, InvalidAccountException {
        if (hasEnoughFundsAfterWithdrawal(saldoAntes, valorRetirada) && hasEnoughQuota(totalJaRetiradoDoDia, limiteDeRetiradaDiario, valorRetirada) && isValueValid(valorRetirada)) {
            return true;
        }
        return false;
    }

    public static boolean isDepositValid(int numeroConta, double value) throws InvalidAccountException, NumberFormatException {
        if (isValueValid(value) && isNumeroContaValid(numeroConta)) {
            return true;
        }
        return false;
    }
}
