package br.com.manzatech.brewer.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.manzatech.brewer.controller.page.PageWrapper;
import br.com.manzatech.brewer.model.Breadcrumb;
import br.com.manzatech.brewer.model.Cidade;
import br.com.manzatech.brewer.repositories.Cidades;
import br.com.manzatech.brewer.repositories.Estados;
import br.com.manzatech.brewer.repositories.filter.CidadeFilter;
import br.com.manzatech.brewer.service.CadastroCidadeService;
import br.com.manzatech.brewer.service.exception.CidadeJaCadastradaException;

@Controller
@RequestMapping("/cidades")
public class CidadesController {
	
	@Autowired
	private Cidades cidades;
	
	@Autowired
	private Estados estados;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@GetMapping("/nova")
	public ModelAndView nova(Cidade cidade) {
		ModelAndView mv = new ModelAndView("cidades/CadastroCidade");
		ArrayList<Breadcrumb> breadcrumbs = new ArrayList<>();
		breadcrumbs.add(new Breadcrumb("Cidades", "/cidades"));
		breadcrumbs.add(new Breadcrumb("Nova Cidade", null));
		mv.addObject("title", "Cadastro de Cidades");
		mv.addObject("breadcrumb", breadcrumbs);
		mv.addObject("estados", estados.findAll());
		return mv;
	}
	
	@CacheEvict(value = "cidades", key = "#cidade.estado.id", condition = "#cidade.temEstado()")
	@PostMapping("/nova")
	public ModelAndView salvar(@Valid Cidade cidade, Errors errors, RedirectAttributes ra) {
		if (errors.hasErrors()) {
			return nova(cidade);
		} 
		try {
			this.cadastroCidadeService.salvar(cidade);
		} catch (CidadeJaCadastradaException e) {
			errors.rejectValue("nome", e.getMessage(), e.getMessage());
			return nova(cidade);
		}
		ra.addFlashAttribute("message", "Cidade cadastrada com sucesso");
		ra.addFlashAttribute("messageType", "success");
		return new ModelAndView("redirect:/cidades/nova");
	}
	
	@GetMapping
	public ModelAndView listar(CidadeFilter cidadeFilter, @PageableDefault(size = 5) Pageable pageable) {
		ModelAndView mv = new ModelAndView("cidades/ListarCidades");
		ArrayList<Breadcrumb> breadcrumbs = new ArrayList<>();
		breadcrumbs.add(new Breadcrumb("Cidades", null));
		mv.addObject("title", "Listagem de Cidades");
		mv.addObject("estados", estados.findAll());
		PageWrapper<Cidade> pagina = new PageWrapper<Cidade>(cidades.filtrar(cidadeFilter, pageable));
		mv.addObject("pagina", pagina);
		return mv;
	}
	
	@Cacheable(value = "cidades", key = "#estadoId")
	@ResponseBody
	@GetMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> listarPorCodigoEstadoAjax(@RequestParam(name = "estado", defaultValue = "-1") Long estadoId){
		List<Cidade> cidades = this.cidades.findByEstadoId(estadoId);
		return ResponseEntity.ok(cidades);
	}
}
