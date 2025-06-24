public class PerformanceMetrics {
    private final int totalPoints;
    private final int threads;
    private final double piEstimate;
    private final double durationSeconds;
    private final long memoryUsedMB;
    private final double cpuPercent;

    public PerformanceMetrics(int totalPoints, int threads, double piEstimate,
                              double durationSeconds, long memoryUsedMB, double cpuPercent) {
        this.totalPoints = totalPoints;
        this.threads = threads;
        this.piEstimate = piEstimate;
        this.durationSeconds = durationSeconds;
        this.memoryUsedMB = memoryUsedMB;
        this.cpuPercent = cpuPercent;
    }

    public String toCsvLine() {
        return String.format("%d,%d,%.6f,%.3f,%d,%.1f",
                totalPoints, threads, piEstimate, durationSeconds, memoryUsedMB, cpuPercent);
    }

    @Override
    public String toString() {
        return String.format("N = %,d | Threads: %d | Pi = %.6f | Tempo: %.3f s | Memória: %d MB | CPU: %.1f%%",
                totalPoints, threads, piEstimate, durationSeconds, memoryUsedMB, cpuPercent);
    }
}