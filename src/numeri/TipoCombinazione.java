package numeri;

public enum TipoCombinazione {
	AMBETTO(1), AMBO(2), TERNO(3), QUATERNA(4), CINQUINA(5);

	private int len;
	
	private TipoCombinazione(int i){
		this.len = i;
	}
	
	public int len(){
		return this.len;
	}
}

