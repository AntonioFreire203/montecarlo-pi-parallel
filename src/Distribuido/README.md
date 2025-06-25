# Cálculo de Pi usando Método Monte Carlo

Este projeto implementa o cálculo do valor de Pi usando o método Monte Carlo em três versões:
1. **Sequencial**: Execução em uma única thread
2. **Paralela**: Execução em múltiplas threads na mesma máquina
3. **Distribuída**: Execução em múltiplas máquinas usando sockets

## Método Monte Carlo para cálculo de Pi

O método Monte Carlo para calcular Pi baseia-se na relação entre a área de um círculo e a área de um quadrado que o circunscreve.

1. Consideramos um círculo de raio 1 inscrito em um quadrado de lado 2.
2. A área do círculo é π × r² = π × 1² = π.
3. A área do quadrado é (2r)² = 4.
4. A razão entre a área do círculo e a área do quadrado é π/4.

Gerando pontos aleatórios dentro do quadrado e contando quantos caem dentro do círculo, podemos estimar π:
- π ≈ 4 × (número de pontos dentro do círculo) / (número total de pontos)

Quanto mais pontos gerarmos, mais precisa será nossa aproximação.

## Requisitos

- Java Development Kit (JDK) 8 ou superior

## Como Compilar

```bash
javac PiSequential.java
javac PiParallel.java
javac PiServer.java
javac PiClient.java
```

## Como Executar

### Versão Sequencial

```bash
java PiSequential [número_de_pontos]
```

Exemplo:
```bash
java PiSequential 1000000000
```

### Versão Paralela

```bash
java PiParallel [número_de_pontos] [número_de_threads]
```

Exemplo:
```bash
java PiParallel 1000000000 8
```

### Versão Distribuída

1. Inicie o servidor:
```bash
java PiServer [número_de_pontos]
```

2. Inicie os clientes em cada máquina:
```bash
java PiClient [endereço_do_servidor]
```

Se o cliente estiver na mesma máquina que o servidor, você pode usar:
```bash
java PiClient localhost
```

## Comparação de Desempenho

Para comparar o desempenho das três implementações, execute cada versão com o mesmo número de pontos e anote os tempos de execução. Recomenda-se testar com diferentes quantidades de pontos para observar como a escalabilidade se comporta.

Exemplo de tabela comparativa:

| Implementação | Número de Pontos | Tempo de Execução (s) | Valor de Pi Calculado | Erro |
|---------------|------------------|----------------------|----------------------|------|
| Sequencial    | 1.000.000.000    | X                    | X                    | X    |
| Paralela (8 threads) | 1.000.000.000 | X                | X                    | X    |
| Distribuída (4 máquinas) | 1.000.000.000 | X            | X                    | X    |

## Considerações sobre Escalabilidade

- **Versão Sequencial**: O tempo de execução cresce linearmente com o número de pontos.
- **Versão Paralela**: O speedup teórico é limitado pelo número de núcleos disponíveis (Lei de Amdahl).
- **Versão Distribuída**: Pode escalar além dos limites de uma única máquina, mas introduz overhead de comunicação.

## Possíveis Melhorias

1. Implementar geração de números aleatórios mais eficiente
2. Otimizar a comunicação na versão distribuída
3. Implementar balanceamento dinâmico de carga
4. Utilizar GPU para paralelização massiva
