package com.bcopstein.ExercicioRefatoracaoBanco.util.ProjectExceptions;

public class InvalidAccountException extends Exception {
    public InvalidAccountException() {

    }

    public InvalidAccountException(String message) {
        super(message);
    }
}
