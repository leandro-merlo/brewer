package br.com.manzatech.brewer.utils;

import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;

public class MenuUtils {

	private static HashSet<Object> menuEstoque;
	private static HashSet<Object> menuCadastro;

	public static final String MENU_ESTOQUE = "estoque";
	public static final String MENU_CADASTRO = "cadastro";

	static {
		menuEstoque = new HashSet<Object>();
		menuEstoque.add("/estilos");
		menuEstoque.add("/cervejas");

		menuCadastro = new HashSet<Object>();
		menuCadastro.add("/cidades");
		menuCadastro.add("/clientes");
		menuCadastro.add("/usuarios");
	}

	public static boolean menuIsOpen(String menuName, HttpServletRequest request) {
		String contextPath = request.getContextPath();
		String uri = request.getRequestURI();

		switch (menuName) {
			case MENU_ESTOQUE: {
				return check(menuEstoque, contextPath, uri);
			}
			case MENU_CADASTRO: {
				return check(menuCadastro, contextPath, uri);
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	private static boolean check(HashSet<Object> menu, String contextPath, String uri) {
		for (Object obj : menu) {
			if (obj instanceof String) {
				String starts = contextPath + (String) obj;
				if (uri.startsWith(starts)) {
					return true;					
				}
			}
			if (obj instanceof HashSet) {
				return check((HashSet<Object>) obj, contextPath, uri);
			}
		}
		return false;
	}
}
