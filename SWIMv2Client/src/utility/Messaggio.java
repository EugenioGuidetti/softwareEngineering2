package utility;

public class Messaggio {
	
	private TipoMessaggio tipo;
	private String testo;
	
	public Messaggio(TipoMessaggio tipo, String testo) {
		this.tipo = tipo;
		this.testo = testo;
	}
	
	public TipoMessaggio getTipo() {
		return tipo;
	}
	
	public String getTesto() {
		return testo;
	}

}
