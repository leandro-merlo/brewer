package br.com.manzatech.brewer.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.manzatech.brewer.model.Breadcrumb;

@Controller
@RequestMapping("/cidades")
public class CidadesController {

	@GetMapping("/nova")
	public String nova(Model model) {
		ArrayList<Breadcrumb> breadcrumbs = new ArrayList<>();
		breadcrumbs.add(new Breadcrumb("Cidades", "/cidades"));
		breadcrumbs.add(new Breadcrumb("Nova Cidade", null));
		model.addAttribute("title", "Cadastro de Cidades");
		model.addAttribute("breadcrumb", breadcrumbs);
		return "cidades/CadastroCidade";
	}
}
