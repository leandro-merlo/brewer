package org.manzatech.brewer.controller;

import org.manzatech.brewer.model.Cerveja;
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
    public String novo(){
        return "cerveja/CadastroCerveja";
    }

    @PostMapping("/cervejas/novo")
    public String cadastrar(@Valid Cerveja cerveja, BindingResult result, Model model, RedirectAttributes redirectAttributes){
        if (result.hasErrors()) {
            model.addAttribute("mensagem", "Erro no formulário");
        }
        System.out.println("SKU: " + cerveja.getSku());
        System.out.println("Nome: " + cerveja.getNome());
        redirectAttributes.addFlashAttribute("mensagem", "Cerveja adicionada com sucesso!");
        return "redirect:/cervejas/novo";
    }
}
