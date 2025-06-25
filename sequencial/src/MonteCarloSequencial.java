public class MonteCarloSequencial {

    public static void main(String[] args) {
        int[] Nvalores = {100_000, 1_000_000, 10_000_000, 100_000_000};

        for (int N : Nvalores) {
            double x, y, z;
            int count = 0;

            // Limpa o garbage collector antes de medir a memória
            System.gc();

            long startTime = System.nanoTime();

            long memoriaAntes = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            for (int i = 0; i < N; i++) {
                x = (Math.random() * 2) - 1;
                y = (Math.random() * 2) - 1;
                z = x * x + y * y;
                if (z <= 1) {
                    count++;
                }
            }

            long memoriaDepois = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            double pi = 4.0 * count / N;
            long endTime = System.nanoTime();
            double duracaoSegundos = (endTime - startTime) / 1_000_000_000.0;
            double memoriaUsadaMB = (memoriaDepois - memoriaAntes) / (1024.0 * 1024.0);

            System.out.printf("N = %,d | Pi = %.6f | Tempo = %.3f segundos | Memória = %.3f MB%n",
                    N, pi, duracaoSegundos, memoriaUsadaMB);
        }
    }
}
