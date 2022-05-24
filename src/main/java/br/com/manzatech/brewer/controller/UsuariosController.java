package br.com.manzatech.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

	@GetMapping("/novo")
	public String novo(Model model) {
		model.addAttribute("title", "Cadastro de Usuários");
		return "usuarios/CadastroUsuario";
	}
}
