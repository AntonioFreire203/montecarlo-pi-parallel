# Monte Carlo π – Paralelização em Java

Um projeto em Java que estima o valor de π pelo Método de Monte Carlo, aproveitando paralelismo via `Threads`. O objetivo é comparar tempos de execução serial vs. paralelo, analisar escalabilidade e eficiência, e identificar possíveis gargalos numa arquitetura de múltiplas threads.

---

## Sumário

- [Visão Geral](#visão-geral)
- [Características](#características)
- [Pré-requisitos](#pré-requisitos)
- [Como Compilar e Executar](#como-compilar-e-executar)
- [Medição de Tempo e Benchmark](#medição-de-tempo-e-benchmark)
- [Configuração da Máquina](#configuração-da-máquina)
- [Análise de Escalabilidade](#análise-de-escalabilidade)
- [Possíveis Melhorias](#possíveis-melhorias)
- [Contribuição](#contribuição)
- [Licença](#licença)

---

## Visão Geral

O Método de Monte Carlo estima π gerando pontos aleatórios dentro de um quadrado de lado 2 e verificando quantos caem dentro do círculo inscrito (raio 1). A razão entre pontos dentro do círculo e total de pontos, multiplicada por 4, aproxima π.

Esta implementação:

1. Permite execução **serial** (1 thread) e **paralela** (N threads).
2. Recebe parâmetros de número total de amostras e número de threads.
3. Coleta tempos de início/fim para cada configuração.

---

## Características

- Estimativa de π por Monte Carlo
- Paralelização manual com `Thread` e `Runnable`
- Comparação de tempos de execução para diferentes valores de `numThreads`
- Relatórios simples de velocidade, eficiência e escalabilidade

---

## Pré-requisitos

- Java 
- Maven ou Gradle (opcional, se preferir gerenciar dependências)
- Git

---

## Como Compilar e Executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/SEU_USUARIO/montecarlo-pi-parallel-java.git
   cd montecarlo-pi-parallel-java
