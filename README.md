# Projeto: Cálculo de Pi com Método de Monte Carlo (Versão Paralela)

Este projeto implementa o cálculo do valor de π utilizando o **método de Monte Carlo** com **paralelismo em Java**, por meio do uso de múltiplas threads.

## 🎯 Objetivo

Analisar o desempenho da execução paralela de um algoritmo computacionalmente intensivo, comparando diferentes números de threads e avaliando **tempo de execução**, **eficiência** e **escalabilidade**.

## 🧠 Método de Monte Carlo

Gera pontos aleatórios dentro de um quadrado de lado 1. Calcula π com base na proporção de pontos que caem dentro do círculo inscrito.

## 🛠️ Tecnologias

- Java
- Threads

## 📁 Estrutura do Projeto
```
MonteCarloPiParallel/
├── src/
│   ├── Main.java
│   ├── MonteCarloPiParallel.java
│   └── PiCalculatorThread.java
├── dados/
│   └── dados.csv          <- Resultados dos testes de desempenho
├── relatorio.md             <- Análise técnica e observações
├── README.md
└── .gitignore
```
**Testes**
Dados Coletados:

    Valor estimado de Pi

    Tempo de execução (segundos)

    Consumo de memória (MB)

    Uso de CPU (%)

1.Testes realizados com diferentes números de threads (1, 2, 4, 8,16).

2.Resultados registrados em dados/dados.csv.

**Máquina de Testes**

1.Descreva aqui: modelo do processador, número de núcleos, RAM, sistema operacional.

- **Processador:** 11th Gen Intel® Core™ i7-1165G7 @ 2.80GHz (4 núcleos, 8 threads)
- **Memória RAM:** 8,00 GB (utilizável: 5,49 GB)
- **Sistema operacional:** Windows 10 64 bits (processador x64)




📝 Observações Técnicas

    O método utilizado baseia-se na geração de pontos aleatórios dentro de um quadrado de área conhecida.

    O tempo de execução foi medido usando System.nanoTime().

    O consumo de memória foi monitorado usando Runtime.getRuntime().

    O uso de CPU foi estimado via OperatingSystemMXBean.

### Resultados 
    **Eficiência**
    A eficiência da solução foi alta até 8 threads, apresentando redução de tempo de execução quase linear. 
    No entanto, ao utilizar 16 threads, a eficiência caiu devido ao overhead de gerenciamento e à limitação física da máquina (8 threads lógicas).

    **Escalabilidade**
    O algoritmo apresentou boa escalabilidade para um número de threads igual ou inferior ao número de threads lógicas do processador. 
    A partir deste ponto, a escalabilidade foi limitada, com ganhos marginais. Esse comportamento é esperado em arquiteturas com número limitado de núcleos.
