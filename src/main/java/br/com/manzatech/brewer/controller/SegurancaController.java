package br.com.manzatech.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SegurancaController {

	@GetMapping("/login")
	public String loginPage() {
		return "Login";
	}
	
	@GetMapping("/403")
	public String acessoNegado() {
		return "403";
	}
}
