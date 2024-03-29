package br.com.manzatech.brewer.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

import br.com.manzatech.brewer.controller.page.PageWrapper;
import br.com.manzatech.brewer.model.Breadcrumb;
import br.com.manzatech.brewer.model.Cerveja;
import br.com.manzatech.brewer.model.Origem;
import br.com.manzatech.brewer.model.Sabor;
import br.com.manzatech.brewer.repositories.Cervejas;
import br.com.manzatech.brewer.repositories.Estilos;
import br.com.manzatech.brewer.repositories.filter.CervejaFilter;
import br.com.manzatech.brewer.service.CadastroCervejaService;
import br.com.manzatech.brewer.utils.MenuUtils;;

@Controller
@RequestMapping("/cervejas")
public class CervejasController {
	
	@Autowired
	private Estilos estilos;
	
	@Autowired
	private Cervejas cervejas;
	
	@Autowired
	private CadastroCervejaService cadastroCervejaService;
			
	@GetMapping("/nova")
	public ModelAndView nova(Cerveja cerveja, Model model) {
		ModelAndView mv = new ModelAndView("cervejas/CadastroCerveja", model.asMap());
		mv.addObject("title", "Cadastro de Cerveja");
		List<Breadcrumb> breadcrumb = new ArrayList<>();
		breadcrumb.add(new Breadcrumb("Cervejas", "/cervejas"));
		breadcrumb.add(new Breadcrumb("Nova Cerveja", null));
		mv.addObject("breadcrumb", breadcrumb);
		mv.addObject("sabores", Sabor.values());
		mv.addObject("estilos", estilos.findAll());
		mv.addObject("origens", Origem.values());
		return mv;
	}
	
	@PostMapping("/nova")
	public ModelAndView cadastrar(@Valid Cerveja cerveja, BindingResult result, Model model, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return nova(cerveja, model);
		}
		this.cadastroCervejaService.salvar(cerveja);
		attributes.addFlashAttribute("message", "Cadastro efetuado com sucesso");
		attributes.addFlashAttribute("messageType", "success");
		return new ModelAndView("redirect:/cervejas/nova");
	}
	
	@GetMapping
	public ModelAndView listar(CervejaFilter cervejaFilter, @PageableDefault(size = 2) Pageable pageable) {
		ModelAndView mv = new ModelAndView("cervejas/ListarCervejas");
		mv.addObject("title", "Listagem de Cervejas");
		ArrayList<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb("Cervejas", null));
		mv.addObject("breadcrumb", breadcrumbs);
		mv.addObject("sabores", Sabor.values());
		mv.addObject("estilos", this.estilos.findAll());
		mv.addObject("origens", Origem.values());
		PageWrapper<Cerveja> wrapper = new PageWrapper<Cerveja>(cervejas.filtrar(cervejaFilter, pageable));
		mv.addObject("pagina", wrapper);
		return mv;
	}
		
}
