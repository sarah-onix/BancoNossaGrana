package com.bcopstein.ExercicioRefatoracaoBanco;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ContasTest {

    private static Map<Integer, Conta> contas;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void init() {
        contas = new HashMap<>();
        contas.put(10, new Conta(10, "Joe Doe"));
        contas.put(12, new Conta(10, "Jane Doe"));
        contas.put(15, new Conta(15, "testeRetirada", 200, 0));
        Contas.initialize(contas);

    }

    @org.junit.Before
    public void setUp() throws Exception {
        //Contas.reset();
        List<Operacao> operacoesDaConta = new ArrayList<>(); // populate arrayList later
        operacoesDaConta.add(new Operacao(
                5,
                1,
                2000,
                12,
                00,
                30,
                10,
                0,
                100.00,
                1
        ));
        operacoesDaConta.add(new Operacao(
                5,
                1,
                2000,
                12,
                01,
                30,
                10,
                0,
                50.00,
                0
        ));
        operacoesDaConta.add(new Operacao(
                1,
                2,
                2000,
                12,
                01,
                22,
                10,
                1,
                10.50,
                0
        ));


        Operacoes mockOperacoes = mock(Operacoes.class);
        // when(mockOperacoes.getInstance()).thenReturn(mockOperacoes);
        when(mockOperacoes.getOperacoesDaConta(10)).thenReturn(operacoesDaConta);
        // when(mockOperacoes.getOperacoesDaConta(10)).thenReturn(operacoesDaConta);


    }


 /*   @org.junit.Test
    public void initialize() throws Exception {

   }*/

    @org.junit.Test
    public void getInstance() {
        assertNotNull(Contas.getInstance());
    }

    @org.junit.Test
    public void getAllContas() {
        assertEquals(contas.values(), Contas.getInstance().getAllContas());
    }

    @org.junit.Test
    public void contaExists() {
        assertTrue(Contas.getInstance().contaExists(10));
        assertTrue(Contas.getInstance().contaExists(12));
        assertFalse(Contas.getInstance().contaExists(100));
    }

    @org.junit.Test
    public void getCorrentista() {
        assertEquals(contas.get(10).getCorrentista(), Contas.getInstance().getCorrentista(10));
        assertEquals(contas.get(12).getCorrentista(), Contas.getInstance().getCorrentista(12));
    }

    @org.junit.Test
    public void getStrStatus() {
        assertEquals(contas.get(10).getStrStatus(), Contas.getInstance().getStrStatus(10));
        assertEquals(contas.get(12).getStrStatus(), Contas.getInstance().getStrStatus(12));
    }

    @org.junit.Test
    public void getLimRetiradaDiaria() {
        // assertEquals(contas.get(10).getLimRetiradaDiaria(),Contas.getInstance().getLimRetiradaDiaria(10));
        // assertEquals(contas.get(10).getLimRetiradaDiaria(),Contas.getInstance().getLimRetiradaDiaria(10));
    }

    @org.junit.Test
    public void getSaldo() {

        assertEquals(contas.get(10).getSaldo(), Contas.getInstance().getSaldo(10));
        assertEquals(contas.get(10).getSaldo(), Contas.getInstance().getSaldo(10));
    }

    @org.junit.Test
    public void retirada() {
        Contas.getInstance().retirada(15, 150.02);
        assertEquals((200 - 150.02), Contas.getInstance().getSaldo(15));
    }

    @org.junit.Test
    public void getConta() {
    }

    @org.junit.Test
    public void deposito() {
    }

    @org.junit.Test
    public void getOperacoesNoMes() {
    }

    @org.junit.Test
    public void getDebitosNoMes() {
    }

    @org.junit.Test
    public void getCreditosNoMes() {
    }

    @org.junit.Test
    public void getValorTotalDeDebitosNoMes() {
    }

    @org.junit.Test
    public void getValorTotalDeCreditosNoMes() {
    }

    @org.junit.Test
    public void getSaldoMedioNoMes() {
    }
}