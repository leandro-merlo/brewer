package org.manzatech.brewer.controller;

import org.manzatech.brewer.model.Usuario;
import org.manzatech.brewer.repository.Grupos;
import org.manzatech.brewer.service.UsuarioService;
import org.manzatech.brewer.service.exception.EmailUsuarioJaCadastradoException;
import org.manzatech.brewer.service.exception.SenhaObrigatoriaParaUsuarioNovoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Locale;

@RequestMapping("/usuarios")
@Controller
public class UsuariosController {

    @Autowired
    private UsuarioService service;

    @Autowired
    private Grupos grupos;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/novo")
    public ModelAndView novo(Usuario usuario){
        ModelAndView mv = new ModelAndView("usuario/CadastroUsuario");
        mv.addObject("grupos", grupos.findAll());
        return mv;
    }

    @PostMapping("/novo")
    public ModelAndView salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes){
        System.out.println(usuario.getGrupos());
        try {
            service.salvar(usuario);
        } catch (EmailUsuarioJaCadastradoException ex){
            result.rejectValue("email", ex.getMessage(), ex.getMessage());
        } catch (SenhaObrigatoriaParaUsuarioNovoException ex){
            result.rejectValue("senha", ex.getMessage(), ex.getMessage());
        }
        if (result.hasErrors()){
            return novo(usuario);
        }
        attributes.addFlashAttribute("mensagem", "Usuário cadastrado com sucesso!");
        return new ModelAndView("redirect:/usuarios/novo");
    }
}
