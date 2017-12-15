package org.manzatech.brewer.controller;

import org.manzatech.brewer.model.Cliente;
import org.manzatech.brewer.model.Estado_;
import org.manzatech.brewer.model.TipoPessoa;
import org.manzatech.brewer.repository.Estados;
import org.manzatech.brewer.service.ClienteService;
import org.manzatech.brewer.service.exception.DocumentoClienteJaCadastradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/clientes")
public class ClientesController {

    @Autowired
    private Estados estados;

    @Autowired
    private ClienteService service;

    @GetMapping("/novo")
    public ModelAndView novo(Cliente cliente)
    {
        ModelAndView vm = new ModelAndView("cliente/CadastroCliente");
        vm.addObject("tiposPessoa", TipoPessoa.values());
        vm.addObject("estados", estados.findAll(Sort.by(Sort.Order.asc(Estado_.nome.getName()))));
        return vm;
    }

    @PostMapping("/novo")
    public ModelAndView cadastrar(@Valid Cliente cliente, BindingResult result, RedirectAttributes attributes){
        if (result.hasErrors()){
            return novo(cliente);
        }
        try {
            service.salvar(cliente);
        }catch (DocumentoClienteJaCadastradoException ex){
            result.rejectValue("cpfCnpj", ex.getMessage(), ex.getMessage());
            return novo(cliente);
        }
        attributes.addFlashAttribute("mensagem", "Cliente salvo com sucesso");
        return new ModelAndView("redirect:/clientes/novo");
    }

}
