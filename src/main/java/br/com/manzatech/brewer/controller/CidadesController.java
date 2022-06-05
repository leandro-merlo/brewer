package br.com.manzatech.brewer.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.manzatech.brewer.model.Breadcrumb;
import br.com.manzatech.brewer.model.Cidade;
import br.com.manzatech.brewer.repositories.Cidades;

@Controller
@RequestMapping("/cidades")
public class CidadesController {
	
	@Autowired
	private Cidades cidades;
	
	@GetMapping("/nova")
	public String nova(Model model) {
		ArrayList<Breadcrumb> breadcrumbs = new ArrayList<>();
		breadcrumbs.add(new Breadcrumb("Cidades", "/cidades"));
		breadcrumbs.add(new Breadcrumb("Nova Cidade", null));
		model.addAttribute("title", "Cadastro de Cidades");
		model.addAttribute("breadcrumb", breadcrumbs);
		
		return "cidades/CadastroCidade";
	}
	
	@ResponseBody
	@GetMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> listarPorCodigoEstadoAjax(@RequestParam(name = "estado", defaultValue = "-1") Long estadoId){
		List<Cidade> cidades = this.cidades.findByEstadoId(estadoId);
		return ResponseEntity.ok(cidades);
	}
}
