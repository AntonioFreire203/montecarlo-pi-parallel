import java.io.*;
import java.net.*;
import java.util.*;
import java.text.DecimalFormat;

/**
 * Servidor para cálculo distribuído de Pi usando o método Monte Carlo
 * Este servidor coordena o trabalho entre vários clientes, distribuindo
 * a carga de pontos a serem calculados e agregando os resultados.
 * 
 * O algoritmo funciona da seguinte forma:
 * 1. O servidor aceita conexões de múltiplos clientes
 * 2. Divide o número total de pontos entre os clientes conectados
 * 3. Envia para cada cliente o número de pontos que ele deve calcular
 * 4. Recebe os resultados parciais de cada cliente
 * 5. Agrega os resultados e calcula o valor final de Pi
 */
public class PiServer {
    // Define a porta de comunicação que o servidor usará para receber conexões
    private static final int PORT = 8000;
    
    // Número total de pontos a serem gerados para o cálculo de Pi (padrão: 1 bilhão)
    private static long totalPoints = 1_000_000_000L;
    
    // Número de clientes que participarão do cálculo
    private static int numClients = 0;
    
    // Contador de clientes que já terminaram seu trabalho
    private static int finishedClients = 0;
    
    // Contador global de pontos que caíram dentro do círculo (resultado parcial)
    private static long pointsInsideCircle = 0;
    
    // Tempo de início da execução para medir o desempenho
    private static long startTime;
    
    // Objeto de sincronização para acesso concorrente às variáveis compartilhadas
    private static final Object lock = new Object();

    public static void main(String[] args) {
        // Verifica se foi fornecido um argumento para o número de pontos
        if (args.length > 0) {
            try {
                // Tenta converter o argumento para um número long
                totalPoints = Long.parseLong(args[0]);
            } catch (NumberFormatException e) {
                // Se o argumento não for um número válido, usa o valor padrão
                System.out.println("Argumento inválido. Usando valor padrão: " + totalPoints);
            }
        }

        // Registra o tempo de início para medir o desempenho
        startTime = System.currentTimeMillis();
        
        // Exibe informações iniciais
        System.out.println("Servidor iniciado na porta " + PORT);
        System.out.println("Total de pontos a serem calculados: " + totalPoints);

        // Usa try-with-resources para garantir que os recursos sejam fechados automaticamente
        try (
            // Cria um socket de servidor na porta especificada
            ServerSocket serverSocket = new ServerSocket(PORT);
            // Cria um scanner para ler entrada do usuário
            Scanner scanner = new Scanner(System.in)
        ) {
            // Solicita ao usuário o número de clientes esperados
            System.out.println("Aguardando conexões dos clientes...");
            System.out.print("Quantos clientes você espera? ");
            numClients = scanner.nextInt();

            // Listas para armazenar os sockets e handlers dos clientes
            List<Socket> clientSockets = new ArrayList<>();
            List<ClientHandler> handlers = new ArrayList<>();

            // Aceita conexões dos clientes até atingir o número esperado
            for (int i = 0; i < numClients; i++) {
                // Este método bloqueia até que um cliente se conecte
                Socket clientSocket = serverSocket.accept();
                clientSockets.add(clientSocket);
                System.out.println("Cliente " + (i + 1) + " conectado: " + clientSocket.getInetAddress());
            }

            System.out.println("Todos os clientes conectados. Iniciando cálculo...");

            // Calcula quantos pontos cada cliente deve processar (divisão equitativa)
            long pointsPerClient = totalPoints / numClients;
            
            // Cria e inicia threads para lidar com cada cliente
            for (int i = 0; i < numClients; i++) {
                long clientPoints = pointsPerClient;
                
                // Adiciona os pontos restantes ao último cliente para garantir que todos os pontos sejam calculados
                // (caso o número total de pontos não seja divisível pelo número de clientes)
                if (i == numClients - 1) {
                    clientPoints += totalPoints % numClients;
                }
                
                // Cria um handler para este cliente
                ClientHandler handler = new ClientHandler(clientSockets.get(i), clientPoints, i + 1);
                handlers.add(handler);
                
                // Inicia uma nova thread para processar este cliente
                // Isso permite que o servidor lide com múltiplos clientes simultaneamente
                new Thread(handler).start();
            }

            // Aguarda todos os clientes terminarem seus cálculos
            // Usa sincronização para evitar condições de corrida
            synchronized (lock) {
                while (finishedClients < numClients) {
                    try {
                        // Espera até ser notificado que um cliente terminou
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            // Todos os clientes terminaram, calcula o valor final de Pi
            // A fórmula é: Pi ≈ 4 * (pontos dentro do círculo) / (total de pontos)
            double pi = 4.0 * pointsInsideCircle / totalPoints;
            
            // Calcula o tempo total de execução
            long endTime = System.currentTimeMillis();
            double executionTime = (endTime - startTime) / 1000.0;

            // Formata a saída para exibir números com precisão adequada
            DecimalFormat df = new DecimalFormat("#.##########");
            
            // Exibe os resultados finais
            System.out.println("\nResultados finais:");
            System.out.println("Total de pontos: " + totalPoints);
            System.out.println("Pontos dentro do círculo: " + pointsInsideCircle);
            System.out.println("Valor calculado de Pi: " + df.format(pi));
            System.out.println("Valor real de Pi: " + df.format(Math.PI));
            System.out.println("Erro: " + df.format(Math.abs(pi - Math.PI)));
            System.out.println("Tempo de execução: " + executionTime + " segundos");

            // Fecha todas as conexões com os clientes
            for (Socket socket : clientSockets) {
                socket.close();
            }

        } catch (IOException e) {
            // Trata erros de entrada/saída (como problemas de rede)
            System.out.println("Erro no servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Classe interna para lidar com cada cliente em uma thread separada.
     * Cada instância desta classe é responsável por:
     * 1. Enviar o número de pontos para um cliente calcular
     * 2. Receber o resultado do cliente
     * 3. Atualizar o contador global de pontos dentro do círculo
     */
    private static class ClientHandler implements Runnable {
        // Socket para comunicação com este cliente específico
        private Socket clientSocket;
        
        // Número de pontos que este cliente deve calcular
        private long pointsToCalculate;
        
        // Identificador único deste cliente (para fins de log)
        private int clientId;

        /**
         * Construtor do ClientHandler
         * 
         * @param socket Socket de comunicação com o cliente
         * @param points Número de pontos que este cliente deve calcular
         * @param id Identificador único deste cliente
         */
        public ClientHandler(Socket socket, long points, int id) {
            this.clientSocket = socket;
            this.pointsToCalculate = points;
            this.clientId = id;
        }

        @Override
        public void run() {
            // Usa try-with-resources para garantir que os streams sejam fechados automaticamente
            try (
                // Stream para enviar objetos para o cliente
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                
                // Stream para receber objetos do cliente
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())
            ) {
                // Envia o número de pontos que o cliente deve calcular
                out.writeLong(pointsToCalculate);
                out.flush(); // Garante que os dados sejam enviados imediatamente
                
                System.out.println("Solicitando ao Cliente " + clientId + " para calcular " + pointsToCalculate + " pontos");

                // Recebe o resultado do cliente (número de pontos dentro do círculo)
                long clientPointsInside = in.readLong();
                
                System.out.println("Cliente " + clientId + " terminou. Pontos dentro do círculo: " + clientPointsInside);

                // Atualiza o contador global de forma sincronizada para evitar condições de corrida
                // Como múltiplas threads podem tentar atualizar esta variável simultaneamente,
                // usamos um bloco synchronized para garantir acesso exclusivo
                synchronized (lock) {
                    // Adiciona os pontos encontrados por este cliente ao contador global
                    pointsInsideCircle += clientPointsInside;
                    
                    // Incrementa o contador de clientes que terminaram
                    finishedClients++;
                    
                    // Notifica a thread principal que um cliente terminou
                    // Isso é importante para que a thread principal possa verificar
                    // se todos os clientes terminaram e calcular o resultado final
                    lock.notifyAll();
                }

            } catch (IOException e) {
                // Trata erros de comunicação com o cliente
                System.out.println("Erro na comunicação com o Cliente " + clientId + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
