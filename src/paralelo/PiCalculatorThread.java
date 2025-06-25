import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class PiCalculatorThread extends Thread {
    private final int pointsToGenerate;
    private long insideCircle = 0;

    public PiCalculatorThread(int pointsToGenerate) {
        this.pointsToGenerate = pointsToGenerate;
    }

    public long getInsideCircle() {
        return insideCircle;
    }

    @Override
    public void run() {
        // Usa ThreadLocalRandom para melhor performance em ambiente multi-thread
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < pointsToGenerate; i++) {
            double x = random.nextDouble();
            double y = random.nextDouble();
            if (x * x + y * y <= 1.0) {
                insideCircle++;
            }
        }
    }
}