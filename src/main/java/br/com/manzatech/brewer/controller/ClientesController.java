package br.com.manzatech.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/clientes")
public class ClientesController {

	@GetMapping("/novo")
	public String novo(Model model) {
		model.addAttribute("title", "Cadastro de Clientes");
		return "clientes/CadastroCliente";
	}
}
