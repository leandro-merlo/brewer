package br.com.manzatech.brewer.model;

public class Breadcrumb {

	private String name;
	private String link;

	public Breadcrumb() {
		// TODO Auto-generated constructor stub
	}
	
	public Breadcrumb(String name, String link) {
		super();
		this.name = name;
		this.link = link;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
