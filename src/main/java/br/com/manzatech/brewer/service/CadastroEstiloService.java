package br.com.manzatech.brewer.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.manzatech.brewer.model.Estilo;
import br.com.manzatech.brewer.repositories.Estilos;
import br.com.manzatech.brewer.service.exception.NomeEstiloJaCadastradoException;

@Service
public class CadastroEstiloService {

	@Autowired
	private Estilos estilos;
	
	@Transactional
	public Estilo salvar(Estilo estilo) {
		Optional<Estilo> opt = this.estilos.findByNomeIgnoreCase(estilo.getNome());
		if (opt.isPresent()) {
			throw new NomeEstiloJaCadastradoException("Nome do estilo já cadastrado");
		}
		return this.estilos.saveAndFlush(estilo);
	}
}
