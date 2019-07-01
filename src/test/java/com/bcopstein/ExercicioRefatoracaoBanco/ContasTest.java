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

public class ContasTest {

    private Contas contasTest;
    private Operacao opEmDec2018;
    private Operacao opEmNov2017;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void init() {


    }

    @org.junit.Before
    public void setUp() throws Exception {
        Persistencia mockPersistencia = mock(Persistencia.class);
        Map<Integer, Conta> contas = new HashMap<>();
        Conta conta10 = new Conta(10, "John Doe");
        Conta conta12 = new Conta(12, "Jane Doe");
        Conta conta15 = new Conta(15, "Sam Smith", 100);
        Conta contaRetirada = new Conta(100, "ContaParaRetirada", 200);
        Conta contaDeposito = new Conta(200, "ContaParaDeposito", 10);
        Conta contaComSaldo = new Conta(300, "ContaComSaldo", 100);
        contas.put(10, conta10);
        contas.put(12, conta12);
        contas.put(15, conta15);
        contas.put(100, contaRetirada);
        contas.put(200, contaDeposito);
        contas.put(300, contaComSaldo);
        Operacoes mockOperacoes = mock(Operacoes.class);
        opEmDec2018 = new Operacao(10, 12, 2018, 12, 01, 01,
                10,
                0, 120, 1);
        opEmNov2017 = new Operacao(10, 11, 2017, 12, 01, 01,
                10,
                0, 130, 0);
        List<Operacao> ops10 = new ArrayList<>();
        ops10.add(opEmDec2018);
        ops10.add(opEmNov2017);
        Operacao op1 = new Operacao(10, 11, 2017, 12, 01, 01,
                10,
                0, 100, 0);

        Operacao op2 = new Operacao(10, 11, 2017, 12, 02, 01,
                10,
                0, 150, 1);

        Operacao op3 = new Operacao(10, 11, 2017, 12, 03, 01,
                10,
                0, 200, 0);
        List<Operacao> ops300 = new ArrayList<>();
        ops300.add(op1);
        ops300.add(op2);
        ops300.add(op3);

        when(mockPersistencia.loadContas()).thenReturn(contas);
        when(mockOperacoes.getOperacoesDaConta(10)).thenReturn(ops10);
        when(mockOperacoes.getOperacoesDaConta(300)).thenReturn(ops300);
        contasTest = new Contas(mockPersistencia, mockOperacoes, true);
    }

    @org.junit.Test
    public void contaExists() {
        assertTrue(contasTest.contaExists(10));
        assertTrue(contasTest.contaExists(12));
        assertFalse(contasTest.contaExists(999));
    }

    @org.junit.Test
    public void getCorrentista() {
        assertEquals("John Doe", contasTest.getCorrentista(10));
        assertEquals("Jane Doe", contasTest.getCorrentista(12));
        assertEquals("Sam Smith", contasTest.getCorrentista(15));
    }

    @org.junit.Test
    public void getStrStatus() {
        assertEquals("Silver", contasTest.getStrStatus(10));
        assertEquals("Silver", contasTest.getStrStatus(12));
        assertEquals("Silver", contasTest.getStrStatus(15));
    }

    @org.junit.Test
    public void getLimRetiradaDiaria() {
        assertEquals(10000.0, contasTest.getLimRetiradaDiaria(10));
        assertEquals(10000.0, contasTest.getLimRetiradaDiaria(12));
        assertEquals(10000.0, contasTest.getLimRetiradaDiaria(15));
    }

    @org.junit.Test
    public void getSaldo() {
        assertEquals(0, contasTest.getSaldo(10));
        assertEquals(0, contasTest.getSaldo(12));
        assertEquals(100, contasTest.getSaldo(15));
    }

    @org.junit.Test
    public void retirada() {
        contasTest.retirada(100, 150.02);
        assertEquals((200 - 150.02), contasTest.getSaldo(100));
    }

    @org.junit.Test
    public void deposito() {
        contasTest.deposito(200, 100);
        assertEquals((10 + 100), contasTest.getSaldo(200));
    }

    @org.junit.Test
    public void getOperacoesNoMes() {
        List<Operacao> opsDoMes = new ArrayList();
        opsDoMes.add(opEmDec2018);
        assertEquals(opsDoMes, contasTest.getOperacoesNoMes(10, 12, 2018));
    }

    @org.junit.Test
    public void getDebitosNoMes() {
        // Em 12/2018 houve 1 debito e 0 creditos
        List<Operacao> opsDebitos = new ArrayList();
        opsDebitos.add(opEmDec2018);
        assertEquals(opsDebitos, contasTest.getDebitosNoMes(10, 12, 2018));
        assertEquals(Collections.emptyList(), contasTest.getDebitosNoMes(10, 11, 2017));
    }

    @org.junit.Test
    public void getCreditosNoMes() {
        // em 12/2018 houve 1 credito e 0 debitos
        List<Operacao> opsCreditos = new ArrayList<>();
        opsCreditos.add(opEmNov2017);
        assertEquals(Collections.emptyList(), contasTest.getCreditosNoMes(10, 12, 2018));
        assertEquals(opsCreditos, contasTest.getCreditosNoMes(10, 11, 2017));
    }

    @org.junit.Test
    public void getValorTotalDeDebitosNoMes() {
        // em 12/2018 houve 1 debito no valor de 120$
        assertEquals(120, contasTest.getValorTotalDeDebitosNoMes(10, 12, 2018));
        // em 11/2017 houve 0 debitos
        assertEquals(0, contasTest.getValorTotalDeDebitosNoMes(10, 11, 2017));
    }

    @org.junit.Test
    public void getValorTotalDeCreditosNoMes() {
        // em 11/2017 houve 1 credito no valor de 130$
        assertEquals(130, contasTest.getValorTotalDeCreditosNoMes(10, 11, 2017));
        // em 12/2018 houve 0 debitos
        assertEquals(0, contasTest.getValorTotalDeCreditosNoMes(10, 12, 2018));
    }

    @org.junit.Test
    public void getSaldoMedioNoMes() {
        // A conta iniciou com 100, depositou 100, sacou 150, e depositou 200
        assertEquals(20, contasTest.getSaldoMedioNoMes(300, 11, 2017));
    }

}