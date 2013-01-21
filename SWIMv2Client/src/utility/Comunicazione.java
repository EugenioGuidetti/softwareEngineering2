package utility;

public final class Comunicazione {
	
	private static final String EMAIL_NON_VALIDA = 
			"L'indirizzo di posta elettronica specificato non rispetta il formato email.";
	private static final String NICKNAME_NON_DISPONIBILE = 
			"Il nickname scelto è già in uso! Scegliere un altro nickname e ritentare.";
	private static final String REGISTRAZIONE_COMPLETATA = 
			"Congratulazioni! La registrazione è terminata con successo.";
	private static final String ERRORE_SERVLET = 
			"Ops! Qualcosa è andato storto, ci scusiamo per il disagio. Accedi di nuovo tra qualche istante.";
	private static final String CREDENZIALI_NON_VALIDE = 
			"Le tue credenziali di accesso non sono valide. Controllale meglio!";
	private static final String ERRORE_CREAZIONE_ABILITA = 
			"Ops! Qualcosa è andato storto durante la creazione dell'abilita.";
	private static final String CONFERMA_CREAZIONE_ABILITA = 
			"Complimenti! L'abilità è stata creata con successo.";
	private static final String ERRORE_CARICAMENTO_PROPOSTE = 
			"Ops! Qualcosa è andato storto durante il caricamento delle proposte.";
	private static final String ERRORE_MODIFICA_PROPOSTE = 
			"Ops! Qualcosa è andato storto durante la modifica delle proposte.";
	private static final String ERRORE_CARICAMENTO_MONITOR = 
			"Ops! Qualcosa è andato storto durante il caricamento dei dati.";
	private static final String ERRORE_CARICAMENTO_ABILITA = 
			"Ops! Qualcosa è andato storto durante il caricamento delle abilita.";
	private static final String ERRORE_RICERCA = 
			"Ops! Qualcosa è andato storto durante l'esecuzione della ricerca.";
	
	
	private Comunicazione() {
		super();
	}

	public static Messaggio emailNonValida() {
		return new Messaggio(TipoMessaggio.ERRORE, EMAIL_NON_VALIDA);
	}
	
	public static Messaggio nicknameNonDisponibile() {
		return new Messaggio(TipoMessaggio.AVVISO, NICKNAME_NON_DISPONIBILE);
	}
	
	public static Messaggio registrazioneCompletata() {
		return new Messaggio(TipoMessaggio.CONFERMA, REGISTRAZIONE_COMPLETATA);
	}
	
	public static Messaggio erroreServlet() {
		return new Messaggio(TipoMessaggio.AVVISO, ERRORE_SERVLET);
	}
	
	public static Messaggio credenzialiNonValide() {
		return new Messaggio(TipoMessaggio.ERRORE, CREDENZIALI_NON_VALIDE);
	}
	
	public static Messaggio erroreCreazioneAbilita() {
		return new Messaggio(TipoMessaggio.AVVISO, ERRORE_CREAZIONE_ABILITA);
	}
	
	public static Messaggio confermaCreazioneAbilita() {
		return new Messaggio(TipoMessaggio.CONFERMA, CONFERMA_CREAZIONE_ABILITA);
	}

	public static Messaggio erroreCaricamentoProposte() {
		return new Messaggio(TipoMessaggio.AVVISO, ERRORE_CARICAMENTO_PROPOSTE);
	}
	
	public static Messaggio erroreModificaProposte() {
		return new Messaggio(TipoMessaggio.AVVISO, ERRORE_MODIFICA_PROPOSTE);
	}

	public static Messaggio erroreCaricamentoMonitor() {
		return new Messaggio(TipoMessaggio.AVVISO, ERRORE_CARICAMENTO_MONITOR);
	}
	
	public static Messaggio erroreCaricamentoAbilita() {
		return new Messaggio(TipoMessaggio.AVVISO, ERRORE_CARICAMENTO_ABILITA);
	}
	
	public static Messaggio erroreRicerca() {
		return new Messaggio(TipoMessaggio.AVVISO, ERRORE_RICERCA);
	}
	
}
