import javax.swing.JOptionPane;

public class Lista1 {

    // ================================================================
    // EXERCICIO 1 - Compara dois numeros inteiros
    // 3 funcoes: exe1() [controla o fluxo], le() [le um numero],
    // exibe() [mostra o resultado da comparacao]
    // ================================================================
    public static void exe1() {
        int x, y;
        x = le(1);
        y = le(2);

        if (x > y)
            exibe(x, y, 1);
        else if (x < y)
            exibe(x, y, 2);
        else
            exibe(x, y, 3);
    }

    static int le(int qual) {
        String rotulo = (qual == 1) ? "primeiro" : "segundo";
        return Integer.parseInt(
            JOptionPane.showInputDialog("Digite o " + rotulo + " numero inteiro:")
        );
    }

    static void exibe(int x, int y, int tipo) {
        switch (tipo) {
            case 1:
                JOptionPane.showMessageDialog(null, "O numero " + x + " e maior que o numero " + y);
                break;
            case 2:
                JOptionPane.showMessageDialog(null, "O numero " + x + " e menor que o numero " + y);
                break;
            case 3:
                JOptionPane.showMessageDialog(null, "O numero " + x + " e igual ao numero " + y);
                break;
        }
    }

    // ================================================================
    // EXERCICIO 2 - Raizes da equacao do 2 grau (ax^2 + bx + c = 0)
    // 4 funcoes: exe2() [controla o fluxo], leCoeficiente() [le a, b ou c],
    // calcularDelta() [calcula o discriminante], exibeRaizes() [mostra o resultado]
    // ================================================================
    public static void exe2() {
        double a = leCoeficiente("a");
        double b = leCoeficiente("b");
        double c = leCoeficiente("c");

        double delta = calcularDelta(a, b, c);

        exibeRaizes(a, b, delta);
    }

    static double leCoeficiente(String nome) {
        return Double.parseDouble(
            JOptionPane.showInputDialog("Digite o coeficiente " + nome + ":")
        );
    }

    static double calcularDelta(double a, double b, double c) {
        return (b * b) - (4 * a * c);
    }

    static void exibeRaizes(double a, double b, double delta) {
        if (a == 0) {
            JOptionPane.showMessageDialog(null, "O coeficiente 'a' nao pode ser zero (nao e equacao do 2 grau).");
        } else if (delta < 0) {
            JOptionPane.showMessageDialog(null, "A equacao nao possui raizes reais (delta < 0).");
        } else if (delta == 0) {
            double raiz = -b / (2 * a);
            JOptionPane.showMessageDialog(null, "A equacao possui uma raiz real: x = " + raiz);
        } else {
            double raiz1 = (-b + Math.sqrt(delta)) / (2 * a);
            double raiz2 = (-b - Math.sqrt(delta)) / (2 * a);
            JOptionPane.showMessageDialog(null, "As raizes sao:\nx1 = " + raiz1 + "\nx2 = " + raiz2);
        }
    }

    // ================================================================
    // FUNCOES AUXILIARES COMPARTILHADAS
    // Reaproveitadas por varios exercicios (mesmo espirito do le() do exe1)
    // ================================================================
    static int leInteiro(String mensagem) {
        return Integer.parseInt(JOptionPane.showInputDialog(mensagem));
    }

    static double leDecimal(String mensagem) {
        return Double.parseDouble(JOptionPane.showInputDialog(mensagem));
    }

    static void mostrar(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem);
    }

    // ================================================================
    // EXERCICIO 3 - Media aritmetica entre dois numeros inteiros
    // 3 funcoes: exe3(), leInteiro() [compartilhada], calcularMedia()
    // ================================================================
    public static void exe3() {
        int a = leInteiro("Digite o primeiro numero inteiro:");
        int b = leInteiro("Digite o segundo numero inteiro:");

        double media = calcularMedia(a, b);

        mostrar("A media aritmetica entre " + a + " e " + b + " e: " + media);
    }

    static double calcularMedia(int a, int b) {
        return (a + b) / 2.0;
    }

    // ================================================================
    // EXERCICIO 4 - Tipo de triangulo a partir de 3 lados
    // 3 funcoes proprias: exe4(), formaTriangulo(), classificarTriangulo()
    // ================================================================
    public static void exe4() {
        double a = leDecimal("Digite o 1 lado do triangulo:");
        double b = leDecimal("Digite o 2 lado do triangulo:");
        double c = leDecimal("Digite o 3 lado do triangulo:");

        if (formaTriangulo(a, b, c)) {
            mostrar("Os lados formam um triangulo " + classificarTriangulo(a, b, c) + ".");
        } else {
            mostrar("Os lados informados NAO formam um triangulo.");
        }
    }

    static boolean formaTriangulo(double a, double b, double c) {
        return (a + b > c) && (a + c > b) && (b + c > a);
    }

    static String classificarTriangulo(double a, double b, double c) {
        if (a == b && b == c)
            return "EQUILATERO";
        else if (a == b || a == c || b == c)
            return "ISOSCELES";
        else
            return "ESCALENO";
    }

    // ================================================================
    // EXERCICIO 5 - Somas positivas e negativas (finaliza com 0)
    // 3 funcoes proprias: exe5(), contarSoma(), exibirResultadoSomas()
    // ================================================================
    public static void exe5() {
        int numero, soma = 0;
        int positivas = 0, negativas = 0;

        do {
            numero = leInteiro("Digite um numero inteiro (0 para finalizar):");
            if (numero != 0) {
                soma += numero;
                if (contarSoma(soma) == 1)
                    positivas++;
                else
                    negativas++;
            }
        } while (numero != 0);

        exibirResultadoSomas(positivas, negativas);
    }

    // retorna 1 se a soma atual e positiva (ou zero), 0 se negativa
    static int contarSoma(int soma) {
        return (soma >= 0) ? 1 : 0;
    }

    static void exibirResultadoSomas(int positivas, int negativas) {
        mostrar("Somas positivas = " + positivas + "\nSomas negativas = " + negativas);
    }

    // ================================================================
    // EXERCICIO 6 - Fatores de um numero natural (com opcao de repetir)
    // 2 funcoes proprias: exe6() [controla o fluxo, inclusive a repeticao],
    // exibirFatores() -- mais leInteiro()/mostrar() compartilhadas
    // ================================================================
    public static void exe6() {
        String repetir;
        do {
            int n = leInteiro("Digite um numero natural:");
            exibirFatores(n);
            repetir = JOptionPane.showInputDialog("Deseja digitar novamente? (S/N)");
        } while (repetir != null && repetir.equalsIgnoreCase("s"));
    }

    static void exibirFatores(int n) {
        StringBuilder fatores = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            if (n % i == 0) {
                fatores.append(i).append(" ");
            }
        }
        mostrar("Fatores de " + n + ": " + fatores.toString());
    }

    // ================================================================
    // EXERCICIO 7 - Pares, impares, positivos e negativos (finaliza com 0)
    // 3 funcoes proprias: exe7(), atualizarContadores(), exibirResultado7()
    // ================================================================
    static int qtdPares, qtdImpares, qtdPositivos, qtdNegativos;
    static int somaPares, somaImpares, somaPositivos, somaNegativos;

    public static void exe7() {
        qtdPares = qtdImpares = qtdPositivos = qtdNegativos = 0;
        somaPares = somaImpares = somaPositivos = somaNegativos = 0;

        int numero;
        do {
            numero = leInteiro("Digite um numero inteiro (0 para finalizar):");
            if (numero != 0) {
                atualizarContadores(numero);
            }
        } while (numero != 0);

        exibirResultado7();
    }

    static void atualizarContadores(int n) {
        if (n % 2 == 0) {
            qtdPares++;
            somaPares += n;
        } else {
            qtdImpares++;
            somaImpares += n;
        }

        if (n > 0) {
            qtdPositivos++;
            somaPositivos += n;
        } else {
            qtdNegativos++;
            somaNegativos += n;
        }
    }

    static void exibirResultado7() {
        mostrar(
            "Pares: qtd=" + qtdPares + " soma=" + somaPares + "\n" +
            "Impares: qtd=" + qtdImpares + " soma=" + somaImpares + "\n" +
            "Positivos: qtd=" + qtdPositivos + " soma=" + somaPositivos + "\n" +
            "Negativos: qtd=" + qtdNegativos + " soma=" + somaNegativos
        );
    }

    // ================================================================
    // EXERCICIO 8 - Verifica se um numero e primo
    // 3 funcoes proprias: exe8(), verificaPrimo(), formatarMensagemPrimo()
    // ================================================================
    public static void exe8() {
        int n = leInteiro("Digite um numero natural:");
        boolean primo = verificaPrimo(n);
        mostrar(formatarMensagemPrimo(n, primo));
    }

    static boolean verificaPrimo(int n) {
        if (n < 2) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    static String formatarMensagemPrimo(int n, boolean primo) {
        return primo ? (n + " e PRIMO.") : (n + " NAO e primo.");
    }

    // ================================================================
    // EXERCICIO 9 - Fatorial de um numero natural
    // 3 funcoes proprias: exe9(), calcularFatorial(), formatarMensagemFatorial()
    // ================================================================
    public static void exe9() {
        int n = leInteiro("Digite um numero natural:");
        long fatorial = calcularFatorial(n);
        mostrar(formatarMensagemFatorial(n, fatorial));
    }

    static long calcularFatorial(int n) {
        long resultado = 1;
        for (int i = 2; i <= n; i++) {
            resultado *= i;
        }
        return resultado;
    }

    static String formatarMensagemFatorial(int n, long fatorial) {
        return n + "! = " + fatorial;
    }

    // ================================================================
    // EXERCICIO 10 - MMC de dois numeros naturais
    // 3 funcoes proprias: exe10(), calcularMDC(), calcularMMC()
    // ================================================================
    public static void exe10() {
        int a = leInteiro("Digite o 1 numero natural:");
        int b = leInteiro("Digite o 2 numero natural:");

        int mmc = calcularMMC(a, b);

        mostrar("O MMC entre " + a + " e " + b + " e: " + mmc);
    }

    static int calcularMDC(int a, int b) {
        while (b != 0) {
            int resto = a % b;
            a = b;
            b = resto;
        }
        return a;
    }

    static int calcularMMC(int a, int b) {
        return (a * b) / calcularMDC(a, b);
    }

    // ================================================================
    // EXERCICIO 11 - MDC de dois numeros naturais
    // 3 funcoes: exe11(), calcularMDC() [reaproveitada do exe10], formatarMensagemMDC()
    // ================================================================
    public static void exe11() {
        int a = leInteiro("Digite o 1 numero natural:");
        int b = leInteiro("Digite o 2 numero natural:");

        int mdc = calcularMDC(a, b);

        mostrar(formatarMensagemMDC(a, b, mdc));
    }

    static String formatarMensagemMDC(int a, int b, int mdc) {
        return "O MDC entre " + a + " e " + b + " e: " + mdc;
    }

    // ================================================================
    // EXERCICIO 12 - Sequencia de Fibonacci
    // 3 funcoes proprias: exe12(), gerarFibonacci(), formatarSequencia()
    // ================================================================
    public static void exe12() {
        int n = leInteiro("Quantos termos da sequencia de Fibonacci deseja gerar?");

        int[] sequencia = gerarFibonacci(n);

        mostrar("Sequencia de Fibonacci:\n" + formatarSequencia(sequencia));
    }

    static int[] gerarFibonacci(int n) {
        int[] seq = new int[n];
        for (int i = 0; i < n; i++) {
            if (i == 0) seq[i] = 0;
            else if (i == 1) seq[i] = 1;
            else seq[i] = seq[i - 1] + seq[i - 2];
        }
        return seq;
    }

    static String formatarSequencia(int[] seq) {
        StringBuilder sb = new StringBuilder();
        for (int valor : seq) {
            sb.append(valor).append(" ");
        }
        return sb.toString();
    }

    // ================================================================
    // EXERCICIO 13 - Progressao Aritmetica (PA)
    // 3 funcoes: exe13(), gerarPA(), formatarSequencia() [reaproveitada do exe12]
    // ================================================================
    public static void exe13() {
        int primeiroTermo = leInteiro("Digite o primeiro termo da PA:");
        int razao = leInteiro("Digite a razao da PA:");
        int qtdTermos = leInteiro("Quantos termos deseja gerar?");

        int[] pa = gerarPA(primeiroTermo, razao, qtdTermos);

        mostrar("Progressao Aritmetica:\n" + formatarSequencia(pa));
    }

    static int[] gerarPA(int primeiroTermo, int razao, int qtdTermos) {
        int[] pa = new int[qtdTermos];
        for (int i = 0; i < qtdTermos; i++) {
            pa[i] = primeiroTermo + (i * razao);
        }
        return pa;
    }

    // ================================================================
    // EXERCICIO 14 - Soma dos elementos da Progressao Geometrica (PG)
    // 3 funcoes proprias: exe14(), calcularSomaPG(), formatarMensagemPG()
    // ================================================================
    public static void exe14() {
        double primeiroTermo = leDecimal("Digite o primeiro termo da PG:");
        double razao = leDecimal("Digite a razao da PG:");
        int qtdTermos = leInteiro("Quantos termos deseja somar?");

        double soma = calcularSomaPG(primeiroTermo, razao, qtdTermos);

        mostrar(formatarMensagemPG(soma));
    }

    static double calcularSomaPG(double primeiroTermo, double razao, int qtdTermos) {
        double soma = 0;
        double termoAtual = primeiroTermo;
        for (int i = 0; i < qtdTermos; i++) {
            soma += termoAtual;
            termoAtual *= razao;
        }
        return soma;
    }

    static String formatarMensagemPG(double soma) {
        return "A soma dos elementos da PG e: " + soma;
    }

    // ================================================================
    // EXERCICIO 15 - Tabuada de multiplicacao de 1 a 10
    // 3 funcoes proprias: exe15(), gerarTabuada(), formatarLinhaTabuada()
    // ================================================================
    public static void exe15() {
        StringBuilder tabuadaCompleta = new StringBuilder();
        for (int numero = 1; numero <= 10; numero++) {
            tabuadaCompleta.append(gerarTabuada(numero)).append("\n");
        }
        mostrar(tabuadaCompleta.toString());
    }

    static String gerarTabuada(int numero) {
        StringBuilder sb = new StringBuilder();
        for (int multiplicador = 1; multiplicador <= 10; multiplicador++) {
            sb.append(formatarLinhaTabuada(numero, multiplicador)).append("  ");
        }
        return sb.toString();
    }

    static String formatarLinhaTabuada(int numero, int multiplicador) {
        return numero + "x" + multiplicador + "=" + (numero * multiplicador);
    }

    // ================================================================
    // EXERCICIO 16 - Verifica se um numero e perfeito
    // 3 funcoes proprias: exe16(), somarDivisores(), verificarPerfeito()
    // ================================================================
    public static void exe16() {
        int n = leInteiro("Digite um numero natural:");
        boolean perfeito = verificarPerfeito(n);
        mostrar(n + (perfeito ? " e um numero PERFEITO." : " NAO e um numero perfeito."));
    }

    static int somarDivisores(int n) {
        int soma = 0;
        for (int i = 1; i < n; i++) {
            if (n % i == 0) {
                soma += i;
            }
        }
        return soma;
    }

    static boolean verificarPerfeito(int n) {
        return n > 0 && somarDivisores(n) == n;
    }

    public static void main(String[] args) {
        int op;

        do {
            op = Integer.parseInt(JOptionPane.showInputDialog(
                "    M E N U\n\n" +
                "1- Compara Numeros\n" +
                "2- Equacao do Segundo Grau\n" +
                "3- Media Aritmetica\n" +
                "4- Tipo de Triangulo\n" +
                "5- Somas Positivas e Negativas\n" +
                "6- Fatores de um Numero\n" +
                "7- Pares, Impares, Positivos e Negativos\n" +
                "8- Numero Primo\n" +
                "9- Fatorial\n" +
                "10- MMC\n" +
                "11- MDC\n" +
                "12- Sequencia de Fibonacci\n" +
                "13- Progressao Aritmetica (PA)\n" +
                "14- Soma da Progressao Geometrica (PG)\n" +
                "15- Tabuada de 1 a 10\n" +
                "16- Numero Perfeito\n\n" +
                "17- SAIR\n\n" +
                "DIGITE A OPCAO:"
            ));

            switch (op) {
                case 1: exe1(); break;
                case 2: exe2(); break;
                case 3: exe3(); break;
                case 4: exe4(); break;
                case 5: exe5(); break;
                case 6: exe6(); break;
                case 7: exe7(); break;
                case 8: exe8(); break;
                case 9: exe9(); break;
                case 10: exe10(); break;
                case 11: exe11(); break;
                case 12: exe12(); break;
                case 13: exe13(); break;
                case 14: exe14(); break;
                case 15: exe15(); break;
                case 16: exe16(); break;
                case 17: break; // sair
                default:
                    JOptionPane.showMessageDialog(null, "Opcao invalida!");
            }

        } while (op != 17);
    }
}