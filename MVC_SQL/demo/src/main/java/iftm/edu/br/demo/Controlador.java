package iftm.edu.br.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class Controlador {
    @GetMapping("alo")
    public String getMethodName() {
        return "home";
    }
    
}
