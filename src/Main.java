import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int totalPoints = 10_000_000;
        int[] threadCounts = {1, 2, 4, 8, 16};

        // Cria a pasta results se não existir
        File resultsDir = new File("C:\\Users\\anton\\IdeaProjects\\pi-montecarlo-parallel\\dados");
        if (!resultsDir.exists()) {
            resultsDir.mkdir();
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter("C:\\Users\\anton\\IdeaProjects\\pi-montecarlo-parallel\\dados\\dados.csv"))) {
            writer.println("threads,pi,time_seconds,memory_MB,cpu_load_percent");

            for (int threads : threadCounts) {
                // Prepara medição de memória
                Runtime runtime = Runtime.getRuntime();
                runtime.gc(); // Solicita coleta de lixo para limpar memória antes do teste
                long beforeMemory = (runtime.totalMemory() - runtime.freeMemory());

                // Medição de CPU antes
                OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
                double cpuBefore = osBean.getProcessCpuLoad();

                // Medição de tempo
                long startTime = System.nanoTime();
                MonteCarloPiParallel calculator = new MonteCarloPiParallel(totalPoints, threads);
                double piEstimate = calculator.calculatePi();
                long endTime = System.nanoTime();
                double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;

                // Medição de memória depois
                long afterMemory = (runtime.totalMemory() - runtime.freeMemory());
                long usedMemory = (afterMemory - beforeMemory) / (1024 * 1024); // MB

                // Medição de CPU depois
                double cpuAfter = osBean.getProcessCpuLoad();

                // CPU média (como aproximação)
                double cpuPercent = ((cpuBefore + cpuAfter) / 2) * 100.0;

                // Registra os resultados
                writer.printf("%d,%.6f,%.3f,%d,%.1f%n", threads, piEstimate, durationInSeconds, usedMemory, cpuPercent);
                System.out.printf("Threads: %d | Pi: %.6f | Tempo: %.3f s | Memória: %d MB | CPU: %.1f%%%n", threads, piEstimate, durationInSeconds, usedMemory, cpuPercent);
            }
            System.out.println("Resultados salvos em results/results.csv");
        } catch (IOException e) {
            System.out.println("Erro ao salvar os resultados: " + e.getMessage());
        }
    }
}
