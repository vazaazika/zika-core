package br.les.opus.gamification.domain;

public class IBGEInfo{
	private String nome;
	
	private String uf;
	

	public IBGEInfo(String nome, String uf) {
		super();
		this.nome = nome;
		this.uf = uf;
	}

	public IBGEInfo() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

}
