package br.com.manzatech.brewer.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorage {

	public String salvarTemporariamente(MultipartFile[] files);
		
	public byte[] recuperarFoto(String nome, boolean temp);

	public void salvar(String foto);
	
}
