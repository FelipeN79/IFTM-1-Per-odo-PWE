package br.edu.iftm.mvc_thymeleaf_demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("mensagem", "Olá, Spring + Thymeleaf!");
        return "index"; // templates/index.html
    }

    @GetMapping("/sorteio")
    public String sorteio(Model model) {
        // exibe a tela inicial do sorteio, sem números ainda
        model.addAttribute("numeros", null);
        return "sorteio"; // templates/sorteio.html
    }

    @GetMapping("/sortear")
    public String sortear(Model model) {
        List<Integer> numeros = gerarNumerosAleatorios();
        model.addAttribute("numeros", numeros);
        return "sorteio"; // reaproveita a mesma view, agora com números
    }

    private List<Integer> gerarNumerosAleatorios() {
        Random random = new Random();
        List<Integer> numeros = new ArrayList<>();
        while (numeros.size() < 6) {
            int numero = random.nextInt(60) + 1; // 1 a 60
            if (!numeros.contains(numero)) {
                numeros.add(numero);
            }
        }
        return numeros;
    }
}