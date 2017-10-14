package org.manzatech.brewer.controller;

import org.manzatech.brewer.model.Cerveja;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class CervejasController {


    @GetMapping("/cervejas/novo")
    public String novo(Cerveja cerveja)
    {
        return "cerveja/CadastroCerveja";
    }

    @PostMapping("/cervejas/novo")
    public String cadastrar(@Valid Cerveja cerveja, BindingResult result, Model model, RedirectAttributes redirectAttributes){
        if (result.hasErrors()) {
            return novo(cerveja);
        }
        redirectAttributes.addFlashAttribute("mensagem", "Cerveja adicionada com sucesso!");
        return "redirect:/cervejas/novo";
    }
}
