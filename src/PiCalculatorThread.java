import java.util.Random;

public class PiCalculatorThread extends Thread {
    private final int pointsToGenerate;
    private int insideCircle = 0;

    public PiCalculatorThread(int pointsToGenerate) {
        this.pointsToGenerate = pointsToGenerate;
    }

    public int getInsideCircle() {
        return insideCircle;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 0; i < pointsToGenerate; i++) {
            double x = random.nextDouble();
            double y = random.nextDouble();
            if (x * x + y * y <= 1.0) {
                insideCircle++;
            }
        }
    }
}
