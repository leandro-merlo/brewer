package br.com.manzatech.brewer.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.manzatech.brewer.controller.page.PageWrapper;
import br.com.manzatech.brewer.model.Breadcrumb;
import br.com.manzatech.brewer.model.Estilo;
import br.com.manzatech.brewer.repositories.Estilos;
import br.com.manzatech.brewer.repositories.filter.EstiloFilter;
import br.com.manzatech.brewer.service.CadastroEstiloService;
import br.com.manzatech.brewer.service.exception.NomeEstiloJaCadastradoException;

@Controller
@RequestMapping("/estilos")
public class EstilosController {

	@Autowired
	private CadastroEstiloService cadastroEstiloService;
	
	@Autowired
	private Estilos estilos;
	
	@GetMapping("/novo")
	public ModelAndView novo(Estilo estilo, Model model) {
		ModelAndView mv = new ModelAndView("estilos/CadastroEstilo", model.asMap());
		ArrayList<Breadcrumb> breadcrumbs = new ArrayList<>();
		breadcrumbs.add(new Breadcrumb("Estilos", "/estilos"));
		breadcrumbs.add(new Breadcrumb("Novo Estilo", null));
		mv.addObject("title", "Cadastro de Estilos");
		mv.addObject("breadcrumb", breadcrumbs);
		return mv;
	}
	
	@PostMapping("/novo")
	public ModelAndView cadastrar(@Valid Estilo estilo, BindingResult result, Model model, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(estilo, model);
		}
		try {
			this.cadastroEstiloService.salvar(estilo);
		} catch (NomeEstiloJaCadastradoException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(estilo, model);
		}
		attributes.addFlashAttribute("message", "Estilo cadastrado com sucesso!");
		attributes.addFlashAttribute("messageType", "success");
		return new ModelAndView("redirect:/estilos/novo");
	}
	
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> salvar(@Valid @RequestBody Estilo estilo, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(result.getFieldError("nome").getDefaultMessage());
		}
		estilo = this.cadastroEstiloService.salvar(estilo);
		return ResponseEntity.ok(estilo);
	}
	
	@GetMapping
	public ModelAndView listar(EstiloFilter estiloFilter, @PageableDefault(size = 2) Pageable pageable ) {
		ModelAndView mv = new ModelAndView("estilos/ListarEstilos");
		mv.addObject("title", "Listagem de Estilos");
		ArrayList<Breadcrumb> breadcrumbs = new ArrayList<>();
		breadcrumbs.add(new Breadcrumb("Estilos", null));	
		mv.addObject("breadcrumb", breadcrumbs);
		PageWrapper<Estilo> wrapper = new PageWrapper<Estilo>(estilos.filtrar(estiloFilter, pageable));
		mv.addObject("pagina", wrapper);
		return mv;
	}
}
