import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int totalPoints = 10_000_000;
        int[] threadCounts = {1, 2, 4, 8};


        File resultsDir = new File("C:\\Users\\anton\\IdeaProjects\\pi-montecarlo-parallel\\dados");
        if (!resultsDir.exists()) {
            resultsDir.mkdir();
        }


        try (PrintWriter writer = new PrintWriter(new FileWriter("C:\\Users\\anton\\IdeaProjects\\pi-montecarlo-parallel\\dados\\dados.csv"))) {
            writer.println("threads,pi,time_seconds");

            for (int threads : threadCounts) {
                MonteCarloPiParallel calculator = new MonteCarloPiParallel(totalPoints, threads);
                long startTime = System.nanoTime();
                double piEstimate = calculator.calculatePi();
                long endTime = System.nanoTime();

                double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
                writer.printf("%d,%.6f,%.3f%n", threads, piEstimate, durationInSeconds);
                System.out.printf("Threads: %d | Pi: %.6f | Time: %.3f s%n", threads, piEstimate, durationInSeconds);
            }
            System.out.println("Resultados salvos em dados/dados.csv");
        } catch (IOException e) {
            System.out.println("Erro ao salvar os  dados: " + e.getMessage());
        }
    }
}
