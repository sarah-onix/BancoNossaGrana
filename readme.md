# BANCO NOSSA GRANA

![travis](https://travis-ci.org/sarah-onix/BancoNossaGrana.svg?branch=master)

** Trabalho final da disciplina de Técnicas de Programação PUCRS 2019/1 **

* Modulo de acesso a conta corrente do Banco Nossa Grana*



O módulo de acesso a conta corrente é um módulo simples que permite ao funcionário do banco executar operações básicas sobre as contas correntes:
* Consultar saldo
* Consultar últimos movimentos
* Consultar a categoria da conta
* Efetuar depósitos
* Efetuar retiradas
* Consultar estatísticas sobre a conta corrente

**Nesse projeto o sistema é implementado em duas versões:**

- como uma aplicação com interface gráfica em JavaFX (padrão)

- como uma aplicação Spring API REST (branch SpringApp)

**A primeira opção encontra-se implementada na branch master do projeto. Para a versão WEB, ver a branch SpringApp**

* Na versão padrão, sistema é composto por três telas: *
* Tela de identificação da conta corrente: nesta tela o usuário informa o número da conta corrente que deseja acessar.

* Tela de operações: nesta tela o usuário visualiza o saldo, a categoria da conta, o limite diário para saque e os últimos movimentos da conta informada e pode executar operações de depósito e retirada. *

* Tela de estatísticas: nesta tela o usuário visualiza informações gerais sobre a conta tais como: saldo médio no mês/ano indicados; total e quantidade de créditos no mês ano indicados; total e quantidade de débitos no mês ano indicados. O usuário tem acesso a tela de estatisticas a partir da tela de operacoes  *

**Características da lógica de peração**

As contas desse banco tem um comportamento específico. Quanto mais dinheiro o cliente tem depositado mais o banco valoriza seus depósitos. Todos as contas iniciam na categoria “Silver” e zeradas. Contas “Silver” não têm seus depósitos valorizados, ou seja, o valor creditado é exatamente o valor depositado pelo cliente. Quando o saldo da conta atinge ou ultrapassa R$ 50.000,00, a conta passa para a categoria “Gold”. Contas “Gold” têm seus depósitos valorizados em 1%. Neste caso se o cliente depositar R$ 1.000,00 o valor creditado será de R$ 1.010,00. Finalmente se o saldo da conta atinge ou supera os R$ 200.000,00, a conta passa para a categoria “Platinum”. Contas “Platinum” têm seus depósitos valorizados em 2,5%. A verificação de “upgrade” da conta se dá via operação de depósito, e não é possível que um cliente faça “upgrade” diretamente de “Silver” para “Platinum” em uma única operação.

Quando o saldo da conta diminui, em função de uma operação de retirada/saque, a categoria também pode retroceder. **Os limites, porém, não são os mesmos ao verificados quando uma conta sofre “upgrade”**.  
Uma conta só perde sua categoria “Platinum”, e passa para “Gold”, se o saldo cair abaixo de R$ 100.000,00. A conta só perde a categoria “Gold”, e passa para “Silver”, se o saldo cair para menos de R$ 25.000,00. Note que uma conta nunca perde duas categorias em uma única operação de retirada mesmo que o saldo caia abaixo de R$ 25.000,00. Se ele era “Platinum”, cai para “Gold”. Só poderá cair para “Silver” na próxima operação de retirada. Observação: as contas nunca podem ficar negativas (o banco não trabalha com cheque especial).

***Abaixo estão listados os limites diários para saque de acordo com cada tipo de conta:***

| Categoria da conta  | Limite diário para saque |
| ------------- | ------------- |
| SILVER  | R$ 10000,00  |
| GOLD  | R$ 100000,00  |
| PLATINUM  | R$ 500000,00  |


- Para efeitos de armazenamento no arquivo a categoria “Silver” é identificada com o número “0”, a categoria “Gold” com o número “1” e a categoria “Platinum” com o número “2”.

- O número de conta pode ser qualquer inteiro positivo

# PERSISTENCIA DOS DADOS:
Para simplificar a troca de dados os seguintes arquivos são fornecidos:

* Persistencia.java:
  * modulo Java com métodos para leitura e gravação de dados relativos a contas corrente e movimentações de contas corrente (operações de depósito e retirada).
* BDContasBNG.txt:
  * arquivo exemplo com dados de contas corrente.
* BDOperBNG.txt:
  * arquivo exemplo com dados de operações sobre contas corrente.

# Estrutura do projeto padrão
![Nome](https://github.com/TP-BCopsteinSource/ExercicioRefatoracaoBanco/raw/master/ArquiteturaDaSolucaoRefatorada.jpg)

# Mapeamento de endpoints da API REST

**Consultas**

#####   Verificar se uma conta existe pelo número da conta:

    GET localhost:8080/conta/{numeroConta}/exists
  
    Parâmetro: numeroConta Número da conta  a ser pesquisada
  
#####   Buscar relação de operações de uma conta pelo número da conta:
 
    GET localhost:8080/conta/{numeroConta}/operacoes
    
    Parâmetro: numeroConta Número da conta  a ser pesquisada

#####  Buscar relação de operações do dia corrente de uma conta pelo número da conta:
  
    `GET localhost:8080/conta/{numeroConta}/operacoesDoDia`
    
    Parâmetro: numeroConta Número da conta  a ser pesquisada
    
#####  Buscar relação de operações de uma conta pelo número da conta:
  
    GET localhost:8080/conta/{numeroConta}/operacoes
    
    Parâmetro: numeroConta Número da conta  a ser pesquisada
    
#####  Buscar buscar o nome de um correntista pelo seu número da conta:
  
    `GET localhost:8080/conta/{numeroConta}/nomeCorrentista
    
    Parâmetro: numeroConta Número da conta  a ser pesquisada    
    
#####  Buscar o status de uma conta pelo número da conta:
  
    GET localhost:8080/conta/{numeroConta}/status
    
    Parâmetro: numeroConta Número da conta  a ser pesquisada
    
    
#####  Buscar valor total diario disponível para retirada no dia corrente:
  
    GET localhost:8080/conta/{numeroConta}/totalRetiradaDisponivel
    
    Parâmetro: numeroConta Número da conta  a ser pesquisada
    
#####  Buscar valor limite disponível para retirada diaria:
  
    GET localhost:8080/conta/{numeroConta}/limRetDiaria
    
    Parâmetro: numeroConta Número da conta  a ser pesquisada
    
#####  Buscar saldo do correntista pelo seu número da conta:
  
    GET localhost:8080/conta/{numeroConta}/saldo
    
    Parâmetro: numeroConta Número da conta  a ser pesquisada
    
#####  Buscar valor total diario disponível para retirada no dia corrente:
  
    GET localhost:8080/conta/{numeroConta}/totalRetiradaDisponivel
    
    Parametro: numeroConta Número da conta  a ser pesquisada
    
#####  Buscar o valor total de créditos em um mês/ano especificados:
  
    GET localhost:8080/conta/{numeroConta}/totalCreditosNoMes/{monthValue}/{yearValues}
    
    Parâmetro: numeroConta Número da conta  a ser pesquisada
    Parâmetro: monthValue  Mês a ser pesquisado (numérico de 1-12)
    Parâmetro: yearValue   Ano do mês a ser pesquisado (numérico)
    
#####  Buscar o valor total de débitos em um mês/ano especificados:
  
    GET localhost:8080/conta/{numeroConta}/totalDebitosNoMes/{monthValue}/{yearValues}
    
    Parâmetro: numeroConta Número da conta  a ser pesquisada  
    Parâmetro: monthValue  Mês a ser pesquisado (numérico de 1-12)  
    Parâmetro: yearValue   Ano do mês a ser pesquisado (numérico)  
    
#####  Buscar a relação de todos os créditos em um mês/ano especificados:
  
    GET localhost:8080/conta/{numeroConta}/creditosNoMes/{monthValue}/{yearValues}
    
    Parâmetro: numeroConta Número da conta  a ser pesquisada
    Parâmetro: monthValue  Mês a ser pesquisado (numérico de 1-12)  
    Parâmetro: yearValue   Ano do mês a ser pesquisado (numérico)  
    
#####  Buscar a relação de todos os débitos em um mês/ano especificados:
  
    GET localhost:8080/conta/{numeroConta}/debitosNoMes/{monthValue}/{yearValues}
    
    Parâmetro: numeroConta Número da conta  a ser pesquisada  
    Parâmetro: monthValue  Mês a ser pesquisado (numérico de 1-12)  
    Parâmetro: yearValue   Ano do mês a ser pesquisado (numérico)  
    
#####  Buscar o saldo médio de um correntista em um mês/ano especificados:
  
    GET localhost:8080/conta/{numeroConta}/saldoMedio/{monthValue}/{yearValues}
    
    Parâmetro: numeroConta Número da conta  a ser pesquisada   
    Parâmetro: monthValue Mês a ser pesquisado (numérico de 1-12)
    Parâmetro: yearValue Ano do mês a ser pesquisado (numérico)_  

**Operações**

##### Deposita um valor em uma conta pelo número da conta do correntista:
  
    PUT localhost:8080/conta/deposito
     {
       "numeroConta": numero da conta a ser pesquisada,
       valor: valor do deposito
     }


 ##### Efetua um saque em uma conta pelo número da conta do correntista:
  
    PUT localhost:8080/conta/retirada
     {
       "numeroConta": numero da conta a ser pesquisada,
       valor: valor do saque
     }

    
 ##### Salvar os dados e persistir:
  
    PATCH localhost:8080/conta/save
    
    
    
#Autores:

* Bernardo Copstein
* Gabriel Justo
* Sarah Lacerda
