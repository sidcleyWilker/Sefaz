package br.com.desafiosefaz.enums;


public enum TipoTelefone {
	
	RESIDENCIAL(1,"Residencial"),
	COMERCIAL(2,"Comercial");

	private Integer cod;
	private String descricao;
	
	private TipoTelefone(Integer cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public Integer getCod() {
		return cod;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static TipoTelefone toEnum (Integer cod) {
		if (cod == null) {
			return null;
		}
		
		for (TipoTelefone tel : TipoTelefone.values()) {
			if(tel.getCod().equals(cod)) {
				return tel;
			}
		}
		
		throw new IllegalArgumentException("Código inválido: " + cod);
	}
}
