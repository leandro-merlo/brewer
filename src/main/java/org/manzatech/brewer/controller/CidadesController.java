package org.manzatech.brewer.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.manzatech.brewer.controller.page.PageWrapper;
import org.manzatech.brewer.model.Cidade;
import org.manzatech.brewer.model.Estado;
import org.manzatech.brewer.model.Estado_;
import org.manzatech.brewer.repository.Cidades;
import org.manzatech.brewer.repository.Estados;
import org.manzatech.brewer.repository.filters.CidadeFilter;
import org.manzatech.brewer.service.CidadeService;
import org.manzatech.brewer.service.exception.NomeCidadeJaCadastradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cidades")
public class CidadesController {

    @Autowired
    private Cidades cidades;

    @Autowired
    private Estados estados;

    @Autowired
    private CidadeService service;

    @GetMapping("/nova")
    public ModelAndView nova(Cidade cidade){
        ModelAndView mv = new ModelAndView("cidade/CadastroCidade");
        mv.addObject("estados", estados.findAll(Sort.by(Sort.Direction.ASC, Estado_.nome.getName())));
        return mv;
    }

    @GetMapping
    public ModelAndView listar(CidadeFilter cidadeFilter, @PageableDefault(size = 10) Pageable pageable, HttpServletRequest servletRequest){
        ModelAndView mv = new ModelAndView("cidade/PesquisaCidade");
        mv.addObject("estados", estados.findAll());
        mv.addObject("pagina", new PageWrapper<Cidade>(cidades.filtrar(cidadeFilter, pageable), servletRequest));
        return mv;
    }

    @CacheEvict(value = "cidades", key = "#cidade.estado.id", condition = "#cidade.temEstado()")
    @PostMapping("/nova")
    public ModelAndView cadastrar(@Valid Cidade cidade, BindingResult result, RedirectAttributes attributes){
        if (result.hasErrors()){
            return nova(cidade);
        }
        try {
            service.salvar(cidade);
        } catch (NomeCidadeJaCadastradaException ex){
            result.rejectValue("nome", ex.getMessage(), ex.getMessage());
            return nova(cidade);
        }
        attributes.addFlashAttribute("mensagem", "Cidade salva com sucesso!");
        return new ModelAndView("redirect:/cidades/nova");
    }

    @Cacheable(value = "cidades", key = "#estadoId")
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Cidade> pesquisarPorCodigoEstado(@RequestParam(name = "estado", defaultValue = "-1") Long estadoId){
        List<Cidade> ret = cidades.findByEstadoId(estadoId);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ret == null ? new ArrayList<>() : ret;
    }
}
