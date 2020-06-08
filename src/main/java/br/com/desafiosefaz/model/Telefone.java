package br.com.desafiosefaz.model;

import br.com.desafiosefaz.enums.TipoTelefone;

public class Telefone extends Model{

	private static final long serialVersionUID = 1L;
	
	private String ddd;
	private String numero;
	private Integer tipo;
	
	public Telefone() {
		super();
	}

	public Telefone(String ddd, String numero, TipoTelefone tipo) {
		super();
		this.ddd = ddd;
		this.numero = numero;
		this.tipo = tipo.getCod();
	}

	public String getDdd() {
		return ddd;
	}
	public void setDdd(String ddd) {
		this.ddd = ddd;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public TipoTelefone getTipo() {
		return TipoTelefone.toEnum(this.tipo);
	}
	public void setTipo(TipoTelefone tipo) {
		this.tipo = tipo.getCod();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ddd == null) ? 0 : ddd.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Telefone other = (Telefone) obj;
		if (ddd == null) {
			if (other.ddd != null)
				return false;
		} else if (!ddd.equals(other.ddd))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Telefone [ddd=" + ddd + ", numero=" + numero + ", tipo=" + tipo + "]";
	}
	
	
}
