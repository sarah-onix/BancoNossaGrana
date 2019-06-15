package com.bcopstein.ExercicioRefatoracaoBanco.repository;

import com.bcopstein.ExercicioRefatoracaoBanco.entity.Conta;
import com.bcopstein.ExercicioRefatoracaoBanco.entity.ContasFactory;
import com.bcopstein.ExercicioRefatoracaoBanco.entity.Operacao;

import javax.management.InstanceAlreadyExistsException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
public class Persistencia {
    private final String NomeBDContas = "BDContasBNG.txt";
    private final String NomeBDOperacoes = "BDOperBNG.txt";
    private boolean alreadyInstantiated;

    public Persistencia() throws InstanceAlreadyExistsException {
        if (alreadyInstantiated == true) {
            throw new InstanceAlreadyExistsException();
        }
        alreadyInstantiated = true;
    }

    public Persistencia(boolean isTest) {

    }

    public Map<Integer, Conta> loadContas() {
    	Map<Integer,Conta> contas = new HashMap<>();
    	
        String currDir = Paths.get("").toAbsolutePath().toString();
        String nameComplete = currDir+File.separator+NomeBDContas;
        //System.out.println(nameComplete);loadOperacoes
        Path path2 = Paths.get(nameComplete); 
        try (Scanner sc = new Scanner(Files.newBufferedReader(path2, Charset.defaultCharset()))){ 
           sc.useDelimiter("[;\n]"); // separadores: ; e nova linha 
           int numero;
           String nomeCorr;
           double saldo;
           while (sc.hasNext()){
               numero = Integer.parseInt(sc.next()); 
               nomeCorr = sc.next();
               saldo = Double.parseDouble(sc.next());
               sc.next();
               contas.put(numero, ContasFactory.getConta(numero,nomeCorr,saldo));
           }
        }catch (IOException x){ 
            System.err.format("Erro de E/S: %s%n", x);
            return null;
        } 
        return contas;
    }

    public void saveContas(Collection<Conta> contas) {
        Path path1 = Paths.get(NomeBDContas); 
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(path1, Charset.defaultCharset()))) 
        { 
            for(Conta c: contas) 
                writer.format(Locale.ENGLISH,
                		      "%d;%s;%f;%d;",
                		      c.getNumero(),c.getCorrentista(), 
                              c.getSaldo(),c.getStatus()); 
        } 
        catch (IOException x) 
        { 
            System.err.format("Erro de E/S: %s%n", x); 
        } 
    }

    public void saveOperacoes(Collection<Operacao> operacoes) {
        Path path1 = Paths.get(NomeBDOperacoes); 
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(path1, Charset.defaultCharset()))) 
        { 
            for(Operacao op:operacoes) 
                writer.format(Locale.ENGLISH,
                		      "%d;%d;%d;%d;%d;%d;%d;%d;%f;%d;",  
                              op.getDia(),op.getMes(),op.getAno(),
                              op.getHora(),op.getMinuto(),op.getSegundo(),
                              op.getNumeroConta(),op.getStatusConta(),
                              op.getValorOperacao(),op.getTipoOperacao()
                             ); 
        } 
        catch (IOException x) 
        { 
            System.err.format("Erro de E/S: %s%n", x); 
        } 
    }
    
    public List<Operacao> loadOperacoes(){
        List<Operacao> operacoes = new LinkedList<Operacao>();
        
    	String currDir = Paths.get("").toAbsolutePath().toString();
        String nameComplete = currDir+File.separator+NomeBDOperacoes;
        System.out.println(nameComplete);
        Path path2 = Paths.get(nameComplete); 
        try (Scanner sc = new Scanner(Files.newBufferedReader(path2, Charset.defaultCharset()))){ 
           sc.useDelimiter("[;\n]"); // separadores: ; e nova linha 
           int dia,mes,ano;
           int hora,minuto,segundo;
           int numero,status,tipo;
           double valor;
       
           while (sc.hasNext()){ 
               dia = Integer.parseInt(sc.next()); 
               mes = Integer.parseInt(sc.next()); 
               ano = Integer.parseInt(sc.next()); 
               hora = Integer.parseInt(sc.next()); 
               minuto = Integer.parseInt(sc.next()); 
               segundo = Integer.parseInt(sc.next()); 
               numero = Integer.parseInt(sc.next()); 
               status = Integer.parseInt(sc.next()); 
               valor = Double.parseDouble(sc.next());
               tipo = Integer.parseInt(sc.next());
               
               Operacao op = new Operacao(
            		   dia, mes, ano,
            		   hora, minuto, segundo,
	                   numero, status,
	                   valor, tipo);
               
               operacoes.add(op);
           }
        }catch (IOException x){ 
            System.err.format("Erro de E/S: %s%n", x);
            return null;
        } 
        return operacoes;    	
    }
}
