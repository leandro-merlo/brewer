package org.manzatech.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/estilos")
public class EstilosController {

    @GetMapping("/novo")
    public String novo(){
        return "estilo/CadastroEstilo";
    }
}
