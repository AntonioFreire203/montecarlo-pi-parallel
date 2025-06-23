public class Main {
    public static void main(String[] args) throws InterruptedException {
        int totalPoints = 10_000_000; // Número total de pontos a serem gerados
        int[] threadCounts = {1, 2, 4, 8};

        for (int threads : threadCounts) {
            MonteCarloPiParallel calculator = new MonteCarloPiParallel(totalPoints, threads);
            long startTime = System.nanoTime();
            double piEstimate = calculator.calculatePi();
            long endTime = System.nanoTime();

            double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
            System.out.printf("Threads: %d | Pi: %.6f | Time: %.3f s%n", threads, piEstimate, durationInSeconds);
        }
    }
}
