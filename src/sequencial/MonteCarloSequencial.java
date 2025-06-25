public class MonteCarloSequencial {

    public static void main(String[] args) {
        int[] Nvalores = {100_000, 1_000_000, 10_000_000, 100_000_000};//Numero de interacoes

        for (int N : Nvalores) {
            double x, y, z;// coordenadar para o par ordenado x,y / Variavel auxiliar z 
            int count = 0;//numero de pontos dentro do circulo

            // Limpa o garbage collector antes de medir a memória
            System.gc();

            long startTime = System.nanoTime();//iniciar cronometro

            long memoriaAntes = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();//memoria no inicio da execucao 

            for (int i = 0; i < N; i++) {
                x = (Math.random() * 2) - 1;
                y = (Math.random() * 2) - 1;
                z = x * x + y * y;//x^2 + y^2 = R^2  ?
                if (z <= 1) {// par ordenado dentro do raio do circulo ?
                    count++;
                }
            }

            long memoriaDepois = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();// memoria no final da execucao 

            double pi = 4.0 * (count / N);// calculando Pi
            long endTime = System.nanoTime();//parar o cronometro
            double duracaoSegundos = (endTime - startTime) / 1_000_000_000.0;// tempo de execucao estimado
            double memoriaUsadaMB = (memoriaDepois - memoriaAntes) / (1024.0 * 1024.0);//Memoria usada estima (em MegaBits)

            System.out.printf("N = %,d | Pi = %.6f | Tempo = %.3f segundos | Memória = %.3f MB%n",
                    N, pi, duracaoSegundos, memoriaUsadaMB);
        }
    }
}