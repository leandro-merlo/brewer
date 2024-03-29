package br.com.manzatech.brewer.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.manzatech.brewer.controller.page.PageWrapper;
import br.com.manzatech.brewer.model.Breadcrumb;
import br.com.manzatech.brewer.model.Cliente;
import br.com.manzatech.brewer.model.TipoPessoa;
import br.com.manzatech.brewer.repositories.Clientes;
import br.com.manzatech.brewer.repositories.Estados;
import br.com.manzatech.brewer.repositories.filter.ClienteFilter;
import br.com.manzatech.brewer.service.CadastroClienteService;
import br.com.manzatech.brewer.service.exception.CpfCnpjJaCadastradoException;

@Controller
@RequestMapping("/clientes")
public class ClientesController {
		
	@Autowired
	private Estados estados;
	
	@Autowired
	private Clientes clientes;

	@Autowired
	private CadastroClienteService cadastroClienteService;
	
	@GetMapping("/novo")
	public ModelAndView novo(Cliente cliente, Model model) {
		ModelAndView mv = new ModelAndView("clientes/CadastroCliente");
		ArrayList<Breadcrumb> breadcrumb = new ArrayList<Breadcrumb>();
		breadcrumb.add(new Breadcrumb("Clientes", "/clientes"));
		breadcrumb.add(new Breadcrumb("Novo Cliente", null));
		model.addAttribute("breadcrumb", breadcrumb);
		model.addAttribute("title", "Cadastro de Clientes");
		model.addAttribute("tiposPessoa", TipoPessoa.values());
		model.addAttribute("estados", estados.findAll());
		return mv;
	}
	
	@PostMapping("/novo")
	public ModelAndView cadastrar(@Valid Cliente cliente, Errors errors, Model model, RedirectAttributes ra) {
		if (errors.hasErrors()) {
			return novo(cliente, model);
		}
		try {
			this.cadastroClienteService.salvar(cliente);			
		} catch (CpfCnpjJaCadastradoException e) {
			errors.rejectValue("documento", e.getMessage(), e.getMessage());
			return novo(cliente, model);
		}
		ra.addFlashAttribute("message", "Cadastro efetuado com sucesso");
		ra.addFlashAttribute("messageType", "success");		
		ModelAndView mv = new ModelAndView("redirect:/clientes/novo");
		return mv;
	}
	
	@GetMapping
	public ModelAndView listar(ClienteFilter clienteFilter, @PageableDefault(size = 2) Pageable pageable){
		ModelAndView mv = new ModelAndView("clientes/ListarClientes");
		mv.addObject("title", "Listagem de Cliente");
		ArrayList<Breadcrumb> breadcrumb = new ArrayList<Breadcrumb>();
		breadcrumb.add(new Breadcrumb("Clientes", null));
		mv.addObject("breadcrumb", breadcrumb);
		PageWrapper<Cliente> pagina = new PageWrapper<Cliente>(this.clientes.filtrar(clienteFilter, pageable));
		mv.addObject("pagina", pagina);
		return mv;
	}
}
