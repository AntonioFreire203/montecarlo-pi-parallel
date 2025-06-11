# Projeto: Cálculo de Pi com Método de Monte Carlo (Versão Paralela)

Este projeto implementa o cálculo do valor de π utilizando o **método de Monte Carlo** com **paralelismo em Java**, por meio do uso de múltiplas threads.

## 🎯 Objetivo

Analisar o desempenho da execução paralela de um algoritmo computacionalmente intensivo, comparando diferentes números de threads e avaliando **tempo de execução**, **eficiência** e **escalabilidade**.

## 🧠 Método de Monte Carlo

Gera pontos aleatórios dentro de um quadrado de lado 1. Calcula π com base na proporção de pontos que caem dentro do círculo inscrito.

## 🛠️ Tecnologias

- Java
- Threads
- Medição: `System.nanoTime()`

## 📁 Estrutura do Projeto
```
MonteCarloPiParallel/
├── src/
│   ├── Main.java
│   ├── MonteCarloPiParallel.java
│   └── PiCalculatorThread.java
├── results/
│   └── results.csv          <- Resultados dos testes de desempenho
├── relatorio.md             <- Análise técnica e observações
├── README.md
└── .gitignore
```
**Testes**

1.Testes realizados com diferentes números de threads (1, 2, 4, 8).

2.Resultados registrados em results/results.csv.

**Máquina de Testes**

1.Descreva aqui: modelo do processador, número de núcleos, RAM, sistema operacional.

Observações

    1.O método utilizado baseia-se na geração de pontos aleatórios em um quadrado.

    2.O tempo de execução é medido usando System.nanoTime().

    3.Resultados e análises adicionais estão em relatorio.md.
