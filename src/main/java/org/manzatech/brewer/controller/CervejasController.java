package org.manzatech.brewer.controller;

import org.manzatech.brewer.controller.page.PageWrapper;
import org.manzatech.brewer.model.Cerveja;
import org.manzatech.brewer.model.Origem;
import org.manzatech.brewer.model.Sabor;
import org.manzatech.brewer.repository.Cervejas;
import org.manzatech.brewer.repository.Estilos;
import org.manzatech.brewer.repository.filters.CervejaFilter;
import org.manzatech.brewer.service.CervejaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequestMapping("/cervejas")
@Controller
public class CervejasController {

    @Autowired
    private Estilos estilos;

    @Autowired
    private CervejaService service;

    @Autowired
    private Cervejas repository;

    @GetMapping("/nova")
    public ModelAndView novo(Cerveja cerveja)
    {
        ModelAndView mv = new ModelAndView("cerveja/CadastroCerveja");
        mv.addObject("sabores", Sabor.values());
        mv.addObject("firstOption", Origem.NACIONAL);
        mv.addObject("origens", Origem.values());
        mv.addObject("estilos", estilos.findAll());
        return mv;
    }

    @PostMapping("/nova")
    public ModelAndView cadastrar(@Valid Cerveja cerveja, BindingResult result, RedirectAttributes redirectAttributes){
        if (result.hasErrors()) {
            return novo(cerveja);
        }
        service.salvar(cerveja);
        redirectAttributes.addFlashAttribute("mensagem", "Cerveja adicionada com sucesso!");
        return new ModelAndView("redirect:/cervejas/nova");
    }

    @GetMapping
    public ModelAndView pesquisar(CervejaFilter cervejaFilter, BindingResult result, @PageableDefault(size = 10) Pageable pageable, HttpServletRequest request){
        ModelAndView mv = new ModelAndView("cerveja/PesquisaCervejas");
        mv.addObject("estilos", estilos.findAll());
        mv.addObject("sabores", Sabor.values());
        mv.addObject("origens", Origem.values());
        mv.addObject("pagina", new PageWrapper<Cerveja>(repository.filtrar(cervejaFilter, pageable), request));
        return mv;
    }
}
