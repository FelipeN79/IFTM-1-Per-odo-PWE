import javax.swing.JOptionPane;

public class Calculadora {

    public static void main(String[] args) {
        boolean continuar = true;

        while (continuar) {
            int x = Integer.parseInt(JOptionPane.showInputDialog("Digite o primeiro número:"));
            int y = Integer.parseInt(JOptionPane.showInputDialog("Digite o segundo número:"));

            String opcao = JOptionPane.showInputDialog("""
                    Escolha a operacão:
                    1 - Soma
                    2 - Subtração
                    3 - Multiplicacão
                    4 - Divisão
                    5 - Sair""");

            int escolha = Integer.parseInt(opcao);

            switch (escolha) {
                case 1 -> {
                    Adicao ad = new Adicao(x, y);
                    JOptionPane.showMessageDialog(null, "Soma: " + ad.soma());
                }
                case 2 -> {
                    Subtracao sub = new Subtracao(x, y);
                    JOptionPane.showMessageDialog(null, "Subtração: " + sub.subtrai());
                }
                case 3 -> {
                    Multiplicacao mult = new Multiplicacao(x, y);
                    JOptionPane.showMessageDialog(null, "Multiplicação: " + mult.produto());
                }
                case 4 -> {
                    if (y == 0) {
                        JOptionPane.showMessageDialog(null, "Erro: não é possível dividir por zero!");
                    } else {
                        Divisao div = new Divisao(x, y);
                        JOptionPane.showMessageDialog(null, String.format("Divisão: %.2f", div.dividir()));
                    }
                }
                case 5 -> {
                    continuar = false;
                    JOptionPane.showMessageDialog(null, "Encerrando o programa...");
                }
                default -> JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        }
    }

}