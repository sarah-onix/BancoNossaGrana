package com.bcopstein.ExercicioRefatoracaoBanco.controller;

import com.bcopstein.ExercicioRefatoracaoBanco.entity.Operacao;
import com.bcopstein.ExercicioRefatoracaoBanco.service.BancoFacade;
import com.bcopstein.ExercicioRefatoracaoBanco.util.ProjectExceptions.AccountWithdrawalLimitExceededException;
import com.bcopstein.ExercicioRefatoracaoBanco.util.ProjectExceptions.InvalidAccountException;
import com.bcopstein.ExercicioRefatoracaoBanco.util.ProjectExceptions.NotEnoughFundsException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conta")
public class BancoController {


    @GetMapping(path = "{numeroConta}/exists")
    public Boolean contaExists(@PathVariable(value = "numeroConta") int numeroConta) {
        return BancoFacade.getInstance().contaExists(numeroConta);
    }

    @GetMapping(path = "{numeroConta}/operacoes")
    public List<Operacao> getOperacoesDaConta(@PathVariable(value = "numeroConta") int numeroConta) {
        return BancoFacade.getInstance().getOperacoesDaConta(numeroConta);
    }

    @GetMapping(path = "{numeroConta}/operacoesDoDia")
    public List<Operacao> getOperacoesDia(@PathVariable(value = "numeroConta") int numConta)
    {
        return BancoFacade.getInstance().getOperacoesDia(numConta);
    }

    @GetMapping(path = "{numeroConta}/nomeCorrentista")
    public String getCorrentista(@PathVariable(value = "numeroConta") int numeroConta) {
        return BancoFacade.getInstance().getCorrentista(numeroConta);
    }

    @GetMapping(path = "{numeroConta}/status")
    public String getStrStatus(@PathVariable(value = "numeroConta") int numeroConta) {
        return BancoFacade.getInstance().getStrStatus(numeroConta);
    }

    @GetMapping(path = "{numeroConta}/totalRetiradaDisponivel")
    public double getTotalRetiradaDia(@PathVariable(value = "numeroConta") int numConta)
    {
        return BancoFacade.getInstance().getTotalRetiradaDia(numConta);
    }

    @GetMapping(path = "{numeroConta}/limRetDiaria")
    public double getLimRetiradaDiaria(@PathVariable(value = "numeroConta") int numeroConta) {
        return BancoFacade.getInstance().getLimRetiradaDiaria(numeroConta);
    }

    @GetMapping(path = "{numeroConta}/saldo")
    public double getSaldo(@PathVariable(value = "numeroConta") int numeroConta) {
        return BancoFacade.getInstance().getSaldo(numeroConta);
    }

    @PutMapping(path = "deposito")
    public void deposito(@RequestBody int numeroConta, @RequestBody double valor) throws InvalidAccountException, NumberFormatException {
        BancoFacade.getInstance().deposito(numeroConta, valor);
    }

    @PutMapping(path = "retirada")
    public void retirada(@RequestBody int numeroConta, @RequestBody double valor) throws NotEnoughFundsException, InvalidAccountException, AccountWithdrawalLimitExceededException {
        BancoFacade.getInstance().retirada(numeroConta, valor);
    }

    @GetMapping(path = "{numeroConta}/totalCreditosNoMes/{monthValue}/{yearValue}")
    public double getValorTotalDeCreditosNoMes(@PathVariable(value = "numeroConta") int numeroConta, @PathVariable(value = "monthValue") int monthValue, @PathVariable(value = "yearValue") int yearValue) {
        return BancoFacade.getInstance().getValorTotalDeCreditosNoMes(numeroConta, monthValue, yearValue);
    }

    @GetMapping(path = "{numeroConta}/totalDebitosNoMes/{monthValue}/{yearValue}")
    public double getValorTotalDeDebitosNoMes(@PathVariable(value = "numeroConta") int numeroConta, @PathVariable(value = "monthValue") int monthValue, @PathVariable(value = "yearValue") int yearValue) {
        return BancoFacade.getInstance().getValorTotalDeDebitosNoMes(numeroConta, monthValue, yearValue);
    }

    @GetMapping(path = "{numeroConta}/creditosNoMes/{monthValue}/{yearValue}")
    public List<Operacao> getCreditosNoMes(@PathVariable(value = "numeroConta") int numeroConta, @PathVariable(value = "monthValue") int monthValue, @PathVariable(value = "yearValue") int yearValue) {
        return BancoFacade.getInstance().getCreditosNoMes(numeroConta, monthValue, yearValue);
    }

    @GetMapping(path = "{numeroConta}/debitosNoMes/{monthValue}/{yearValue}")
    public List<Operacao> getDebitosNoMes(@PathVariable(value = "numeroConta") int numeroConta, @PathVariable(value = "monthValue") int monthValue, @PathVariable(value = "yearValue") int yearValue) {
        return BancoFacade.getInstance().getCreditosNoMes(numeroConta, monthValue, yearValue);
    }

    @GetMapping(path = "{numeroConta}/saldoMedioNoMes/{monthValue}/{yearValue}")
    public double getSaldoMedioNoMes(@PathVariable(value = "numeroConta") int numeroConta, @PathVariable(value = "monthValue") int monthValue, @PathVariable(value = "yearValue") int yearValue) {
        return BancoFacade.getInstance().getSaldoMedioNoMes(numeroConta, monthValue, yearValue);
    }

    @PatchMapping(path = "save")
    public void save() {
        BancoFacade.getInstance().save();
    }
}
