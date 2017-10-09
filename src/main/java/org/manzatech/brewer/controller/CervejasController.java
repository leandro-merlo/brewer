package org.manzatech.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CervejasController {

    @GetMapping("/cervejas/novo")
    public String novo(){
        return "cerveja/CadastroCerveja";
    }

    @PostMapping("/cervejas/novo")
    public String cadastrar(){
        System.out.print(">>>>>>> cadastrar");
        return "cerveja/CadastroCerveja";
    }
}
