package com.bcopstein.ExercicioRefatoracaoBanco;

import com.bcopstein.ExercicioRefatoracaoBanco.entity.Conta;
import com.bcopstein.ExercicioRefatoracaoBanco.entity.ContasFactory;
import com.bcopstein.ExercicioRefatoracaoBanco.entity.Operacao;
import com.bcopstein.ExercicioRefatoracaoBanco.repository.Persistencia;
import com.bcopstein.ExercicioRefatoracaoBanco.service.Contas;
import com.bcopstein.ExercicioRefatoracaoBanco.service.Operacoes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.*;

import javax.management.InstanceAlreadyExistsException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OperacoesTest {
    Operacoes op;
    Map<Integer, Conta> contas;
    Contas contasTest;
    List<Operacao> lstOperacao;



    @BeforeEach
    public void setup() throws InstanceAlreadyExistsException
    {
        lstOperacao = new ArrayList<>();
        Persistencia mockPersistencia = mock(Persistencia.class);
        contas = new HashMap<>();
        op = new Operacoes(mockPersistencia,true);
        contasTest = new Contas(mockPersistencia, op, true);




        Conta conta1000 = ContasFactory.getConta(1, "Gabriel", 1000);
        Conta conta5000000 = ContasFactory.getConta(5, "João", 5000000);
        Conta conta0 = ContasFactory.getConta(0, "Maria");
        Conta conta60000 = ContasFactory.getConta(6, "Sara", 60000);
        contas.put(1, conta1000);
        contas.put(5, conta5000000);
        contas.put(0, conta0);
        contas.put(6, conta60000);

        Operacao op0 = new Operacao(
            (Calendar.getInstance().get(Calendar.DAY_OF_MONTH)),
            (Calendar.getInstance().get(Calendar.MONTH) + 1),
            (Calendar.getInstance().get(Calendar.YEAR)),
            (Calendar.getInstance().get(Calendar.HOUR)),
            (Calendar.getInstance().get(Calendar.MINUTE)),
            (Calendar.getInstance().get(Calendar.SECOND)),
            0,
            contas.get(0).getStatus(),
            100000,
            0);
        Operacao op5 = new Operacao(
            (Calendar.getInstance().get(Calendar.DAY_OF_MONTH)),
            (Calendar.getInstance().get(Calendar.MONTH) + 1),
            (Calendar.getInstance().get(Calendar.YEAR)),
            (Calendar.getInstance().get(Calendar.HOUR)),
            (Calendar.getInstance().get(Calendar.MINUTE)),
            (Calendar.getInstance().get(Calendar.SECOND)),
            0,
            contas.get(5).getStatus(),
            4900000,
            1);
        Operacao op6 = new Operacao(
            (Calendar.getInstance().get(Calendar.DAY_OF_MONTH)),
            (Calendar.getInstance().get(Calendar.MONTH) + 1),
            (Calendar.getInstance().get(Calendar.YEAR)),
            (Calendar.getInstance().get(Calendar.HOUR)),
            (Calendar.getInstance().get(Calendar.MINUTE)),
            (Calendar.getInstance().get(Calendar.SECOND)),
            0,
            contas.get(6).getStatus(),
            55000,
            1);

        Operacao op1 = new Operacao(
            (Calendar.getInstance().get(Calendar.DAY_OF_MONTH)),
            (Calendar.getInstance().get(Calendar.MONTH) + 1),
            (Calendar.getInstance().get(Calendar.YEAR)),
            (Calendar.getInstance().get(Calendar.HOUR)),
            (Calendar.getInstance().get(Calendar.MINUTE)),
            (Calendar.getInstance().get(Calendar.SECOND)),
            0,
            contas.get(1).getStatus(),
            5000000,
            0);



        lstOperacao.add(op0);
        lstOperacao.add(op5);
        lstOperacao.add(op6);
        lstOperacao.add(op1);





        op.createDeposito(0,100000, contas.get(0).getStatus());
        op.createRetirada(5,4900000, contas.get(5).getStatus());// o status deve mudar para silver
        op.createRetirada(6, 55000, conta60000.getStatus());//o status da conta deve mudar para silver
        op.createDeposito(1, 5000000, conta1000.getStatus());// o status da conta deve midar para platina
        op.save();



        
        when(mockPersistencia.loadContas()).thenReturn(contas);

        
    }

    @Test
    public void testOperacoesConta()
    {
        assertEquals(lstOperacao.get(0).getValorOperacao(), op.getOperacoesDaConta(0).get(0).getValorOperacao());
        assertEquals(lstOperacao.get(0).getTipoOperacao(), op.getOperacoesDaConta(0).get(0).getTipoOperacao());
        
        assertEquals(lstOperacao.get(1).getValorOperacao(), op.getOperacoesDaConta(5).get(0).getValorOperacao());
        assertEquals(lstOperacao.get(1).getTipoOperacao(), op.getOperacoesDaConta(5).get(0).getTipoOperacao());

        assertEquals(lstOperacao.get(2).getValorOperacao(), op.getOperacoesDaConta(6).get(0).getValorOperacao());
        assertEquals(lstOperacao.get(2).getTipoOperacao(), op.getOperacoesDaConta(6).get(0).getTipoOperacao());
        
        assertEquals(lstOperacao.get(3).getValorOperacao(), op.getOperacoesDaConta(1).get(0).getValorOperacao());         assertEquals(lstOperacao.get(2).getTipoOperacao(), op.getOperacoesDaConta(6).get(0).getTipoOperacao());
        assertEquals(lstOperacao.get(3).getTipoOperacao(), op.getOperacoesDaConta(1).get(0).getTipoOperacao());

    }
    @Test
    public void testOperacoesDia()
    {
        assertEquals(op.getOperacoesDia(0).get(0).getValorOperacao(), lstOperacao.get(0).getValorOperacao());
        assertEquals(op.getOperacoesDia(0).get(0).getTipoOperacao(), lstOperacao.get(0).getTipoOperacao());

        assertEquals(op.getOperacoesDia(5).get(0).getValorOperacao(), lstOperacao.get(1).getValorOperacao());
        assertEquals(op.getOperacoesDia(5).get(0).getTipoOperacao(), lstOperacao.get(1).getTipoOperacao());

        assertEquals(op.getOperacoesDia(6).get(0).getValorOperacao(), lstOperacao.get(2).getValorOperacao());
        assertEquals(op.getOperacoesDia(6).get(0).getTipoOperacao(), lstOperacao.get(2).getTipoOperacao());

        assertEquals(op.getOperacoesDia(1).get(0).getValorOperacao(), lstOperacao.get(3).getValorOperacao());
        assertEquals(op.getOperacoesDia(1).get(0).getTipoOperacao(), lstOperacao.get(3).getTipoOperacao());

        
    }
}