package utility;

public enum TipoMessaggio {
	
	CONFERMA,
	AVVISO,
	ERRORE;

	@Override
	public String toString() {
		String toString;
		switch (ordinal()) {
		case 0:
			toString = "Conferma";
			break;
		case 1:
			toString = "Avviso";
			break;
		case 2:
			toString = "Errore";
			break;
		default:
			toString = "";
			break;
		}
		return toString;
	}
}
