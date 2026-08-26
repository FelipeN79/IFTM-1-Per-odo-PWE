public class Divisao {

    int a, b;

    public Divisao(int x, int y) {
        a = x;
        b = y;
    }

    public double dividir() {
        if (b == 0) {
            System.out.println("Erro: divisão por zero!");
            return 0;
        }
        return (double) a / b;
    }

}