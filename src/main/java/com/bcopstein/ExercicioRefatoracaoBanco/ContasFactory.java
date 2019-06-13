package com.bcopstein.ExercicioRefatoracaoBanco;

public class ContasFactory {

    private static final int SILVER_TO_GOLD = 50000;
    private static final int GOLD_TO_PLATINUM = 200000;


    public static Conta getConta(int umNumero, String umNome){
        return new Conta(umNumero,umNome);
    }

    public static Conta getConta(int umNumero, String umNome, double saldo){
        if(Validations.isValueValid(saldo)){
            if(saldo < SILVER_TO_GOLD){
                return new Conta(new ContaSilver(umNumero,saldo,umNome));
            }
            if(saldo >= SILVER_TO_GOLD && saldo < GOLD_TO_PLATINUM){
                return new Conta(new ContaGold(umNumero,saldo,umNome));
            }
            if(saldo >= GOLD_TO_PLATINUM){
                return new Conta(new ContaGold(umNumero,saldo,umNome));
            }
        }
        throw new IllegalStateException("Instanciacao de conta invalida!");
    }
}
