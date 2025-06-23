public class MonteCarloPiParallel {
    private final int totalPoints;
    private final int threadCount;

    public MonteCarloPiParallel(int totalPoints, int threadCount) {
        this.totalPoints = totalPoints;
        this.threadCount = threadCount;
    }

    public double calculatePi() throws InterruptedException {
        PiCalculatorThread[] threads = new PiCalculatorThread[threadCount];
        int pointsPerThread = totalPoints / threadCount;

        for (int i = 0; i < threadCount; i++) {
            threads[i] = new PiCalculatorThread(pointsPerThread);
            threads[i].start();
        }

        int totalInsideCircle = 0;
        for (PiCalculatorThread thread : threads) {
            thread.join();
            totalInsideCircle += thread.getInsideCircle();
        }

        return 4.0 * totalInsideCircle / totalPoints;
    }
}
