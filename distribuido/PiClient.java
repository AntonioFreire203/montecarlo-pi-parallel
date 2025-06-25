import java.io.*;
import java.net.*;
import java.util.Random;

/**
 * Cliente para cálculo distribuído de Pi usando o método Monte Carlo
 * Este cliente recebe do servidor o número de pontos a calcular,
 * realiza o cálculo e retorna o número de pontos que caíram dentro do círculo.
 * 
 * O algoritmo funciona da seguinte forma:
 * 1. O cliente se conecta ao servidor
 * 2. Recebe o número de pontos a calcular
 * 3. Gera pontos aleatórios e verifica quantos caem dentro do círculo unitário
 * 4. Envia o resultado de volta para o servidor
 */
public class PiClient {
    // Define a porta de comunicação que será usada para conectar ao servidor
    private static final int PORT = 8000;
    
    // Endereço padrão do servidor (localhost para testes locais)
    private static final String SERVER_ADDRESS = "localhost"; // Altere para o IP do servidor se necessário

    public static void main(String[] args) {
        // Define o endereço do servidor, usando o padrão se não for especificado
        String serverAddress = SERVER_ADDRESS;
        
        // Permite especificar o endereço do servidor como argumento na linha de comando
        if (args.length > 0) {
            serverAddress = args[0];
        }

        System.out.println("Cliente iniciado. Conectando ao servidor " + serverAddress + ":" + PORT);

        // Usa try-with-resources para garantir que os recursos sejam fechados automaticamente
        try (
            // Cria um socket para conectar ao servidor no endereço e porta especificados
            Socket socket = new Socket(serverAddress, PORT);
            
            // Stream para enviar objetos para o servidor
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            
            // Stream para receber objetos do servidor
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
        ) {
            System.out.println("Conectado ao servidor. Aguardando instruções...");

            // Recebe do servidor o número de pontos que este cliente deve calcular
            long pointsToCalculate = in.readLong();
            System.out.println("Recebido: calcular " + pointsToCalculate + " pontos");

            // Registra o tempo de início para medir o desempenho
            long startTime = System.currentTimeMillis();
            
            // Realiza o cálculo de Monte Carlo para estimar Pi
            long pointsInsideCircle = calculatePoints(pointsToCalculate);
            
            // Calcula o tempo de execução
            long endTime = System.currentTimeMillis();
            double executionTime = (endTime - startTime) / 1000.0;
            
            System.out.println("Cálculo concluído em " + executionTime + " segundos");
            System.out.println("Pontos dentro do círculo: " + pointsInsideCircle);

            // Envia o resultado (número de pontos dentro do círculo) de volta para o servidor
            out.writeLong(pointsInsideCircle);
            out.flush(); // Garante que os dados sejam enviados imediatamente
            System.out.println("Resultado enviado ao servidor");

            System.out.println("Trabalho concluído. Desconectando...");

        } catch (IOException e) {
            // Trata erros de conexão ou comunicação
            System.out.println("Erro de conexão: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Implementa o método Monte Carlo para calcular quantos pontos caem dentro do círculo unitário.
     * 
     * O método Monte Carlo para calcular Pi funciona assim:
     * 1. Geramos pontos aleatórios no quadrado [-1,1] x [-1,1]
     * 2. Verificamos se cada ponto está dentro do círculo unitário (x² + y² ≤ 1)
     * 3. A proporção de pontos dentro do círculo em relação ao total, multiplicada por 4,
     *    nos dá uma aproximação de Pi
     * 
     * @param numPoints número de pontos a serem gerados
     * @return número de pontos que caíram dentro do círculo
     */
    private static long calculatePoints(long numPoints) {
        // Cria um gerador de números aleatórios
        Random random = new Random();
        
        // Contador de pontos que caem dentro do círculo
        long pointsInside = 0;
        
        // Contador de pontos processados (para exibir progresso)
        long pointsProcessed = 0;
        
        // Define o intervalo para reportar o progresso (a cada 10%)
        long reportInterval = numPoints / 10;

        // Loop principal que gera e verifica cada ponto
        for (long i = 0; i < numPoints; i++) {
            // Gera coordenadas aleatórias no quadrado [-1,1] x [-1,1]
            // random.nextDouble() gera um número entre 0 e 1, então multiplicamos por 2 e subtraímos 1
            // para obter um número entre -1 e 1
            double x = 2.0 * random.nextDouble() - 1.0;
            double y = 2.0 * random.nextDouble() - 1.0;
            
            // Verifica se o ponto está dentro do círculo unitário
            // Um ponto (x,y) está dentro do círculo unitário se x² + y² ≤ 1
            if (x*x + y*y <= 1.0) {
                // Incrementa o contador se o ponto estiver dentro do círculo
                pointsInside++;
            }
            
            // Atualiza o contador de pontos processados
            pointsProcessed++;
            
            // Exibe o progresso periodicamente
            if (pointsProcessed % reportInterval == 0 || pointsProcessed == numPoints) {
                // Calcula a porcentagem de conclusão
                double percentage = (100.0 * pointsProcessed) / numPoints;
                System.out.printf("Progresso: %.1f%% (%d de %d pontos)%n", 
                                 percentage, pointsProcessed, numPoints);
            }
        }
        
        // Retorna o número total de pontos que caíram dentro do círculo
        return pointsInside;
    }
}
