public class Config {
    public static final int[] N_VALUES = {100_000, 1_000_000, 10_000_000, 100_000_000};
    public static final int[] THREAD_COUNTS = {1, 2, 4, 8, 16};
    public static final String OUTPUT_DIR = "dados";
    public static final String OUTPUT_FILE = "dados.csv";
    public static final int WARMUP_ITERATIONS = 3;
    public static final int MEASUREMENT_ITERATIONS = 5;
}