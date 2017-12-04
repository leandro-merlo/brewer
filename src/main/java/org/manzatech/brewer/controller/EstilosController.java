package org.manzatech.brewer.controller;

import org.manzatech.brewer.controller.page.PageWrapper;
import org.manzatech.brewer.model.Cerveja;
import org.manzatech.brewer.model.Estilo;
import org.manzatech.brewer.repository.Estilos;
import org.manzatech.brewer.repository.filters.EstiloFilter;
import org.manzatech.brewer.service.EstiloService;
import org.manzatech.brewer.service.exception.NomeEstiloJaCadastradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
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
        try {
            service.salvar(estilo);
        } catch (NomeEstiloJaCadastradoException ex){
            result.rejectValue("nome", ex.getMessage(), ex.getMessage());
            return novo(estilo);
        }
        attributes.addFlashAttribute("mensagem", "Cerveja adicionada com sucesso!");
        return new ModelAndView("redirect:/estilos/novo");
    }

    @GetMapping("")
    public ModelAndView pesquisar(EstiloFilter estiloFilter, BindingResult bindingResult, @PageableDefault(size = 10) Pageable pageable, HttpServletRequest request){
        ModelAndView mv = new ModelAndView("estilo/PesquisaEstilos");
        mv.addObject("pagina", new PageWrapper<Estilo>(estilos.filtrar(estiloFilter, pageable), request));
        return mv;
    }

    @PostMapping(value = "", consumes =  { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> cadastroRapido(@RequestBody @Valid Estilo estilo, BindingResult result) {
        if (result.hasErrors()){
            return ResponseEntity.badRequest().body(result.getFieldError("nome").getDefaultMessage());
        }
        estilo = service.salvar(estilo);
        return ResponseEntity.ok(estilo);
    }

}
