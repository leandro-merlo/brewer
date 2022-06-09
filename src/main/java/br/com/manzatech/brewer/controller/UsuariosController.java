package br.com.manzatech.brewer.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.manzatech.brewer.model.Breadcrumb;
import br.com.manzatech.brewer.model.Usuario;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

	@GetMapping("/novo")
	public ModelAndView novo(Usuario usuario) {
		ModelAndView mv = new ModelAndView("usuarios/CadastroUsuario");
		ArrayList<Breadcrumb> breadcrumb = new ArrayList<Breadcrumb>();
		breadcrumb.add(new Breadcrumb("Usuários", "/usuarios"));
		breadcrumb.add(new Breadcrumb("Novo Usuário", null));
		mv.addObject("breadcrumb", breadcrumb);
		mv.addObject("title", "Cadastro de Usuários");
		return mv;
	}
	
	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Usuario usuario, Errors errors, RedirectAttributes ra) {
		if (!StringUtils.hasText(usuario.getSenha())) {
			errors.rejectValue("senha", "Campo obrigatório", "Campo obrigatório");
		}
		if (errors.hasErrors()) {
			return novo(usuario);
		}
		return new ModelAndView("redirect:/usuarios");
	}
}
