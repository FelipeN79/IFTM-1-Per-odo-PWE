import javax.swing.JOptionPane;

public class Calculadora {

    public static void main(String[] args) {
        int x = Integer.parseInt(JOptionPane.showInputDialog("Digite o primeiro número:"));
        int y = Integer.parseInt(JOptionPane.showInputDialog("Digite o segundo número:"));

        String opcao = JOptionPane.showInputDialog("""
                Escolha a opera\u00e7\u00e3o:
                1 - Soma
                2 - Subtra\u00e7\u00e3o
                3 - Multiplica\u00e7\u00e3o
                4 - Divis\u00e3o""");

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
                Divisao div = new Divisao(x, y);
                JOptionPane.showMessageDialog(null, String.format("Divisão: %.2f", div.dividir()));
            }

            default -> JOptionPane.showMessageDialog(null, "Opção inválida!");
        }
    }

}