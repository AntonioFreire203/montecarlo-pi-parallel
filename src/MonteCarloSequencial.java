package com.uni.montecarlo;
public class MonteCarloSequencial {

    public static void main(String args[]) {
        int N = 100000;//numero de interacoes totais
        double x = 0,y = 0,//Par ordenado
            z = 0,//variavel auxiliar
            count = 0,//numero de pares dentro do circulo
            result;//resultado final
        
        for (int i = 0; i < N; i++) {
            x = (Math.random()* 2) - 1;
            y = (Math.random()* 2) - 1;
            z = Math.pow(x, 2)+ Math.pow(y, 2);//x^2 + y^2 = R^2 
            if (z <= 1) {//par dentro da circunferencia ?
                count++;
            }
        }
        result = 4*(count/N);//calculo do resultado final 
        System.out.println( result );
    }
}
