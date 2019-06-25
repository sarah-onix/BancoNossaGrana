package com.bcopstein.ExercicioRefatoracaoBanco;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.*;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Operacoestet
{
    Operacoes op;

    @BeforeEach
    public void setup()
    {
        Persistencia mockPersistencia = mock(Persistencia.class);
        Map<Integer, Conta> contas = new HashMap<>();


        Conta conta1000 = new Conta(1, "Gabriel", 1000);
        Conta conta5000000 = new Conta(5, "João", 5000000);
        Conta conta0 = new Conta(0, "Maria");
        Conta conta60000 = new Conta(6, "Sara", 60000);
        contas.put(1, conta1000);
        contas.put(5, conta5000000);
        contas.put(0, conta0);
        contas.put(3, conta60000);

        op = new Operacoes(mockPersistencia);

        


        when(mockPersistencia.loadContas()).thenReturn(contas);
    }

    @Test
    public void testCreateRetirada()
    {
        op.createRetirada(5, 4900000, conta5000000.getStatus());// o status deve mudar para silver
        op.createRetirada(6, 55000, conta60000.getStatus());//o status da conta deve mudar para silver
        op.createDeposito(0, 100000, conta0.getStatus());// o status deve mudar para gold
        op.createDeposito(1, 5000000, conta1000.getStatus);// o status da conta deve midar para platina

    }
}