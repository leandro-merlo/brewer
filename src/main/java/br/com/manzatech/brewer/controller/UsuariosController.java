package br.com.manzatech.brewer.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.manzatech.brewer.controller.page.PageWrapper;
import br.com.manzatech.brewer.model.Breadcrumb;
import br.com.manzatech.brewer.model.Cliente;
import br.com.manzatech.brewer.model.Usuario;
import br.com.manzatech.brewer.repositories.Grupos;
import br.com.manzatech.brewer.repositories.Usuarios;
import br.com.manzatech.brewer.repositories.filter.UsuarioFilter;
import br.com.manzatech.brewer.service.CadastroUsuarioService;
import br.com.manzatech.brewer.service.exception.ServiceException;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;
	
	@Autowired
	private Usuarios usuarios;
	
	@Autowired
	private Grupos grupos;
	
	@GetMapping("/novo")
	public ModelAndView novo(Usuario usuario) {
		ModelAndView mv = new ModelAndView("usuarios/CadastroUsuario");
		ArrayList<Breadcrumb> breadcrumb = new ArrayList<Breadcrumb>();
		breadcrumb.add(new Breadcrumb("Usuários", "/usuarios"));
		breadcrumb.add(new Breadcrumb("Novo Usuário", null));
		mv.addObject("breadcrumb", breadcrumb);
		mv.addObject("title", "Cadastro de Usuários");
		mv.addObject("grupos", grupos.findAll());
		return mv;
	}
	
	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Usuario usuario, Errors errors, RedirectAttributes ra) {
		if (errors.hasErrors()) {
			return novo(usuario);
		}
		try {
			this.cadastroUsuarioService.salvar(usuario);
		} catch (ServiceException e) {
			errors.rejectValue(e.getField(), e.getMessage(), e.getMessage());
			return novo(usuario);
		} 
		ra.addFlashAttribute("message", "Cadastro efetuado com sucesso");
		ra.addFlashAttribute("messageType", "success");		
		return new ModelAndView("redirect:/usuarios/novo");
	}
	
	
	@GetMapping
	public ModelAndView listar(UsuarioFilter usuarioFilter, @PageableDefault(size = 5) Pageable pageable){
		ModelAndView mv = new ModelAndView("usuarios/ListarUsuarios");
		mv.addObject("title", "Listagem de Usuários");
		ArrayList<Breadcrumb> breadcrumb = new ArrayList<Breadcrumb>();
		breadcrumb.add(new Breadcrumb("Usuários", null));
		mv.addObject("breadcrumb", breadcrumb);
		PageWrapper<Usuario> pagina = new PageWrapper<Usuario>(this.usuarios.filtrar(usuarioFilter, pageable));
		mv.addObject("pagina", pagina);
		mv.addObject("grupos", grupos.findAll());
		return mv;
	}
	
}
