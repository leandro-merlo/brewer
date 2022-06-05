package br.com.manzatech.brewer.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.manzatech.brewer.model.Cliente;
import br.com.manzatech.brewer.repositories.Clientes;
import br.com.manzatech.brewer.service.exception.CpfCnpjJaCadastradoException;

@Service
public class CadastroClienteService {

	@Autowired
	private Clientes clientes;
	
	@Transactional
	public void salvar(Cliente cliente) {
		Optional<Cliente> c = clientes.findByDocumento(cliente.getDocumentoSemFormatacao());
		if (c.isPresent()) {
			throw new CpfCnpjJaCadastradoException();
		}
		this.clientes.save(cliente);
	}
}
