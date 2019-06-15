package controller;

import com.bcopstein.ExercicioRefatoracaoBanco.entity.Operacao;
import com.bcopstein.ExercicioRefatoracaoBanco.repository.Persistencia;
import com.bcopstein.ExercicioRefatoracaoBanco.service.Contas;
import com.bcopstein.ExercicioRefatoracaoBanco.service.Operacoes;
import com.bcopstein.ExercicioRefatoracaoBanco.util.ProjectExceptions.AccountWithdrawalLimitExceededException;
import com.bcopstein.ExercicioRefatoracaoBanco.util.ProjectExceptions.InvalidAccountException;
import com.bcopstein.ExercicioRefatoracaoBanco.util.ProjectExceptions.NotEnoughFundsException;
import com.bcopstein.ExercicioRefatoracaoBanco.util.Validations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;

@RestController
@RequestMapping("/conta")
public class BancoController {

    private Contas contas;
    private Operacoes operacoes;

    public BancoController() {
        try {
            Persistencia persistencia = new Persistencia();
            operacoes = new Operacoes(persistencia);
            contas = new Contas(persistencia, operacoes);
        } catch (InstanceAlreadyExistsException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @RequestMapping("/hello")
    public boolean sayHi() {
        return true;
    }

    @GetMapping(path = "{numeroConta}/exists")
    public Boolean contaExists(@PathVariable(value = "numeroConta") int numeroConta) {
        return contas.contaExists(numeroConta);
    }

    @GetMapping(path = "{numeroConta}/operacoes")
    public List<Operacao> getOperacoesDaConta(@PathVariable(value = "numeroConta") int numeroConta) {
        return operacoes.getOperacoesDaConta(numeroConta);
    }

    @GetMapping(path = "{numeroConta}/operacoesDoDia")
    public List<Operacao> getOperacoesDia(@PathVariable(value = "numeroConta") int numConta)
    {
        return operacoes.getOperacoesDia(numConta);
    }

    @GetMapping(path = "{numeroConta}/nomeCorrentista")
    public String getCorrentista(@PathVariable(value = "numeroConta") int numeroConta) {
        try {
            return contas.getCorrentista(numeroConta);
        } catch (InvalidAccountException e) {
            return "NON-EXISTENT ACCOUNT!";
        }
    }

    @GetMapping(path = "{numeroConta}/status")
    public String getStrStatus(@PathVariable(value = "numeroConta") int numeroConta) {
        return contas.getStrStatus(numeroConta);
    }

    @GetMapping(path = "{numeroConta}/totalRetiradaDisponivel")
    public double getTotalRetiradaDia(@PathVariable(value = "numeroConta") int numConta)
    {
        return contas.getTotalRetiradaDia(numConta);
    }

    @GetMapping(path = "{numeroConta}/limRetDiaria")
    public double getLimRetiradaDiaria(@PathVariable(value = "numeroConta") int numeroConta) {
        return contas.getLimRetiradaDiaria(numeroConta);
    }

    @GetMapping(path = "{numeroConta}/saldo")
    public double getSaldo(@PathVariable(value = "numeroConta") int numeroConta) {
        return contas.getSaldo(numeroConta);
    }

    @GetMapping(path = "{numeroConta}/deposito/{valor}")
    public void deposito(@PathVariable(value = "numeroConta") int numeroConta, @PathVariable(value = "valor") double valor) throws InvalidAccountException, NumberFormatException {
        if (Validations.isDepositValid(numeroConta, valor)) {
            contas.deposito(numeroConta, valor);
        }
    }

    @GetMapping(path = "{numeroConta}/retirada/{valor}")
    public void retirada(@PathVariable(value = "numeroConta") int numeroConta, @PathVariable(value = "valor") double valor) throws NotEnoughFundsException, InvalidAccountException, AccountWithdrawalLimitExceededException {
        if (Validations.isWithdrawalValid(contas.getTotalRetiradaDia(numeroConta), contas.getLimRetiradaDiaria(numeroConta), contas.getSaldo(numeroConta), valor)) {
            contas.retirada(numeroConta, valor);
        }
    }

    @GetMapping(path = "{numeroConta}/totalCreditosNoMes/{monthValue}/{yearValue}")
    public double getValorTotalDeCreditosNoMes(@PathVariable(value = "numeroConta") int numeroConta, @PathVariable(value = "monthValue") int monthValue, @PathVariable(value = "yearValue") int yearValue) {
        return contas.getValorTotalDeCreditosNoMes(numeroConta, monthValue, yearValue);
    }

    @GetMapping(path = "{numeroConta}/totalDebitosNoMes/{monthValue}/{yearValue}")
    public double getValorTotalDeDebitosNoMes(@PathVariable(value = "numeroConta") int numeroConta, @PathVariable(value = "monthValue") int monthValue, @PathVariable(value = "yearValue") int yearValue) {
        return contas.getValorTotalDeDebitosNoMes(numeroConta, monthValue, yearValue);
    }

    @GetMapping(path = "{numeroConta}/creditosNoMes/{monthValue}/{yearValue}")
    public List<Operacao> getCreditosNoMes(@PathVariable(value = "numeroConta") int numeroConta, @PathVariable(value = "monthValue") int monthValue, @PathVariable(value = "yearValue") int yearValue) {
        return contas.getCreditosNoMes(numeroConta, monthValue, yearValue);
    }

    @GetMapping(path = "{numeroConta}/debitosNoMes/{monthValue}/{yearValue}")
    public List<Operacao> getDebitosNoMes(@PathVariable(value = "numeroConta") int numeroConta, @PathVariable(value = "monthValue") int monthValue, @PathVariable(value = "yearValue") int yearValue) {
        return contas.getCreditosNoMes(numeroConta, monthValue, yearValue);
    }

    @GetMapping(path = "{numeroConta}/saldoMedioNoMes/{monthValue}/{yearValue}")
    public double getSaldoMedioNoMes(@PathVariable(value = "numeroConta") int numeroConta, @PathVariable(value = "monthValue") int monthValue, @PathVariable(value = "yearValue") int yearValue) {
        return contas.getSaldoMedioNoMes(numeroConta, monthValue, yearValue);
    }

    public void save() {
        contas.save();
        operacoes.save();
    }
}
