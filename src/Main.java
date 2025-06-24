import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.nio.file.Paths;
import com.sun.management.OperatingSystemMXBean;



public class Main {
    public static void main(String[] args) {
        try {
            runBenchmark();
        } catch (Exception e) {
            System.err.println("Erro durante benchmark: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void runBenchmark() throws IOException, InterruptedException {
        System.out.println("Iniciando benchmark Monte Carlo Pi...");

        // Warmup da JVM
        performWarmup();

        createOutputDirectory();

        try (PrintWriter writer = new PrintWriter(new FileWriter(getOutputPath()))) {
            writer.println("N,threads,pi,time_seconds,memory_MB,cpu_load_percent");

            for (int totalPoints : Config.N_VALUES) {
                for (int threads : Config.THREAD_COUNTS) {
                    PerformanceMetrics metrics = measurePerformance(totalPoints, threads);
                    writer.println(metrics.toCsvLine());
                    System.out.println(metrics);
                }
            }
            System.out.println("Resultados salvos em " + Config.OUTPUT_FILE);
        }
    }

    private static void performWarmup() throws InterruptedException {
        System.out.println("Aquecendo JVM...");
        for (int i = 0; i < Config.WARMUP_ITERATIONS; i++) {
            new MonteCarloPiParallel(10_000, 2).calculatePi();
        }
        System.gc();
        Thread.sleep(1000); // Aguarda GC
    }

    private static PerformanceMetrics measurePerformance(int totalPoints, int threads)
            throws InterruptedException {
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        Thread.sleep(100); // Aguarda GC

        long beforeMemory = runtime.totalMemory() - runtime.freeMemory();
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        long startTime = System.nanoTime();
        MonteCarloPiParallel calculator = new MonteCarloPiParallel(totalPoints, threads);
        double piEstimate = calculator.calculatePi();
        long endTime = System.nanoTime();

        long afterMemory = runtime.totalMemory() - runtime.freeMemory();
        long usedMemory = Math.max(0, afterMemory - beforeMemory) / (1024 * 1024);
        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
        double cpuPercent = osBean.getProcessCpuLoad() * 100.0;

        return new PerformanceMetrics(totalPoints, threads, piEstimate,
                durationInSeconds, usedMemory, cpuPercent);
    }

    private static void createOutputDirectory() {
        File resultsDir = new File(Config.OUTPUT_DIR);
        if (!resultsDir.exists()) {
            resultsDir.mkdirs();
        }
    }

    private static String getOutputPath() {
        return Paths.get(Config.OUTPUT_DIR, Config.OUTPUT_FILE).toString();
    }
}