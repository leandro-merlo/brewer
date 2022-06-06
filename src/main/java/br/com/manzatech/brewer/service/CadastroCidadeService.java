package br.com.manzatech.brewer.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.manzatech.brewer.model.Cidade;
import br.com.manzatech.brewer.repositories.Cidades;
import br.com.manzatech.brewer.service.exception.CidadeJaCadastradaException;

@Service
public class CadastroCidadeService {

	@Autowired
	private Cidades cidades;
	
	@Transactional
	public void salvar(Cidade cidade) {
		Optional<Cidade> c = cidades.findByNomeIgnoreCaseAndEstadoId(cidade.getNome(), cidade.getEstado().getId());
		if (c.isPresent()) {
			throw new CidadeJaCadastradaException();
		}
		cidades.save(cidade);
	}
}
