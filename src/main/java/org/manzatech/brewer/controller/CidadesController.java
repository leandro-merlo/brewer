package org.manzatech.brewer.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.manzatech.brewer.model.Cidade;
import org.manzatech.brewer.model.Estado;
import org.manzatech.brewer.repository.Cidades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cidades")
public class CidadesController {

    @Autowired
    private Cidades cidades;

    @GetMapping("/nova")
    public String nova(){
        return "cidade/CadastroCidade";
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Cidade> pesquisarPorCodigoEstado(@RequestParam(name = "estado", defaultValue = "-1") Long estadoId){
        List<Cidade> ret = cidades.findByEstadoId(estadoId);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ret == null ? new ArrayList<>() : ret;
    }
}
