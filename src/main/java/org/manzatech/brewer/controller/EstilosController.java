package org.manzatech.brewer.controller;

import org.manzatech.brewer.model.Estilo;
import org.manzatech.brewer.repository.Estilos;
import org.manzatech.brewer.service.EstiloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/estilos")
public class EstilosController {

    @Autowired
    private Estilos estilos;

    @Autowired
    private EstiloService service;

    @GetMapping("/novo")
    public ModelAndView novo(Estilo estilo)
    {
        estilos.findAll();
        ModelAndView mv = new ModelAndView("estilo/CadastroEstilo");
        mv.addObject(estilo);
        return mv;
    }

    @PostMapping("/novo")
    public ModelAndView cadastrar(@Valid Estilo estilo, BindingResult result, RedirectAttributes attributes){
        if (result.hasErrors()){
            return novo(estilo);
        }
        service.salvar(estilo);
        attributes.addFlashAttribute("mensagem", "Cerveja adicionada com sucesso!");
        return new ModelAndView("redirect:/estilos/novo");
    }
}
