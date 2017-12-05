package org.manzatech.brewer.controller;

import org.manzatech.brewer.model.Estado_;
import org.manzatech.brewer.model.TipoPessoa;
import org.manzatech.brewer.repository.Estados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/clientes")
public class ClientesController {

    @Autowired
    private Estados estados;

    @GetMapping("/novo")
    public ModelAndView novo()
    {
        ModelAndView vm = new ModelAndView("cliente/CadastroCliente");
        vm.addObject("tiposPessoa", TipoPessoa.values());
        vm.addObject("estados", estados.findAll(Sort.by(Sort.Order.asc(Estado_.nome.getName()))));
        return vm;
    }
}
