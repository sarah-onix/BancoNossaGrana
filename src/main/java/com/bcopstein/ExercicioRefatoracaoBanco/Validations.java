package com.bcopstein.ExercicioRefatoracaoBanco;


import com.bcopstein.ExercicioRefatoracaoBanco.ProjectExceptions.AccountWithdrawalLimitExceededException;
import com.bcopstein.ExercicioRefatoracaoBanco.ProjectExceptions.InvalidAccountException;
import com.bcopstein.ExercicioRefatoracaoBanco.ProjectExceptions.NotEnoughFundsException;

public class Validations {


    public static boolean isNumeroContaValid(int numeroConta) throws InvalidAccountException {
        if (BancoFacade.getInstance().contaExists(numeroConta)) {
            return true;
        }
        throw new InvalidAccountException("Número da conta é INVALIDO ou NAO EXISTE");
    }

    public static boolean isValueValid(double value) {
        if (value < 0.0) {
            throw new NumberFormatException("Valor inválido");
        } else
            return true;
    }

    public static boolean hasEnoughFundsAfterWithdrawal(int numeroConta, double value) throws NotEnoughFundsException, InvalidAccountException {
        if (BancoFacade.getInstance().getSaldo(numeroConta) < value) {
            throw new NotEnoughFundsException();
        }
        return true;
    }

    public static boolean hasEnoughQuota(int numeroConta, double value) throws AccountWithdrawalLimitExceededException 
    {
        double totalDia = BancoFacade.getInstance().getTotalRetiradaDia(numeroConta);
        totalDia += value;

        if(totalDia > BancoFacade.getInstance().getLimRetiradaDiaria(numeroConta))
        {
            throw new AccountWithdrawalLimitExceededException();
        }
        return true;
        
    }

    public static boolean isWithdrawalValid(int numeroConta, double value) throws AccountWithdrawalLimitExceededException, NotEnoughFundsException, NumberFormatException, InvalidAccountException {
        if (hasEnoughFundsAfterWithdrawal(numeroConta, value) && hasEnoughQuota(numeroConta, value) && isValueValid(value)) {
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
