public class MonteCarloPiParallel {
    private final int totalPoints;
    private final int threadCount;

    public MonteCarloPiParallel(int totalPoints, int threadCount) {
        this.totalPoints = totalPoints;
        this.threadCount = threadCount;
    }

    public double calculatePi() throws InterruptedException {
        PiCalculatorThread[] threads = new PiCalculatorThread[threadCount];
        int basePointsPerThread = totalPoints / threadCount;
        int remainder = totalPoints % threadCount;

        // Distribui pontos restantes para as primeiras threads
        for (int i = 0; i < threadCount; i++) {
            int pointsForThisThread = basePointsPerThread + (i < remainder ? 1 : 0);
            threads[i] = new PiCalculatorThread(pointsForThisThread);
            threads[i].start();
        }

        long totalInsideCircle = 0;
        for (PiCalculatorThread thread : threads) {
            thread.join();
            totalInsideCircle += thread.getInsideCircle();
        }

        return 4.0 * totalInsideCircle / totalPoints;
    }
}