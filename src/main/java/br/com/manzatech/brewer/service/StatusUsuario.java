package br.com.manzatech.brewer.service;

import br.com.manzatech.brewer.repositories.Usuarios;

public enum StatusUsuario {
	
	ATIVAR()
	{
		@Override
		public void executar(Long[] ids, Usuarios usuarios) {
			usuarios.findByIdIn(ids).stream().forEach(u -> u.setAtivo(true));			
		}
	},	
	DESATIVAR
	
	{
		@Override
		public void executar(Long[] ids, Usuarios usuarios) {
			usuarios.findByIdIn(ids).stream().forEach(u -> u.setAtivo(false));			
		}
	};
	
	public abstract void executar(Long[] ids, Usuarios usuarios);

}
