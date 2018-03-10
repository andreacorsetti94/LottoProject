package numeri;

import ruote.RuotaID;

public class NumeroRuota {

	private Numero numero;
	private RuotaID id;
	
	@Override
	public String toString() {
		return "Ruota :" + id.toString() + ", Numero:" + numero;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		NumeroRuota other = (NumeroRuota) obj;
		if (id != other.id)
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}

	public Numero getNumero() {
		return numero;
	}
	public void setNumero(Numero numero) {
		this.numero = numero;
	}
	public RuotaID getId() {
		return id;
	}
	public void setId(RuotaID id) {
		this.id = id;
	}
	public NumeroRuota(Numero numero, RuotaID id) {
		super();
		this.numero = numero;
		this.id = id;
	}
	
	
}
