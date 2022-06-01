package br.com.manzatech.brewer.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.manzatech.brewer.model.Breadcrumb;
import br.com.manzatech.brewer.model.TipoPessoa;
import br.com.manzatech.brewer.repositories.Estados;

@Controller
@RequestMapping("/clientes")
public class ClientesController {
		
	@Autowired
	private Estados estados;

	@GetMapping("/novo")
	public ModelAndView novo(Model model) {
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
}
