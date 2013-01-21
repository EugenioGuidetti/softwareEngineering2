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
	
	
	private Comunicazione() {
		super();
	}

	public static Messaggio emailNonValida() {
		Messaggio messaggio = new Messaggio(TipoMessaggio.ERRORE, EMAIL_NON_VALIDA);
		return messaggio;
	}
	
	public static Messaggio nicknameNonDisponibile() {
		Messaggio messaggio = new Messaggio(TipoMessaggio.AVVISO, NICKNAME_NON_DISPONIBILE);
		return messaggio;
	}
	
	public static Messaggio registrazioneCompletata() {
		Messaggio messaggio = new Messaggio(TipoMessaggio.CONFERMA, REGISTRAZIONE_COMPLETATA);
		return messaggio;
	}
	
	public static Messaggio erroreServlet() {
		Messaggio messaggio = new Messaggio(TipoMessaggio.AVVISO, ERRORE_SERVLET);
		return messaggio;
	}
	
	public static Messaggio credenzialiNonValide() {
		Messaggio messaggio = new Messaggio(TipoMessaggio.ERRORE, CREDENZIALI_NON_VALIDE);
		return messaggio;
	}
	
	public static Messaggio erroreCreazioneAbilita() {
		Messaggio messaggio = new Messaggio(TipoMessaggio.AVVISO, ERRORE_CREAZIONE_ABILITA);
		return messaggio;
	}
	
	public static Messaggio confermaCreazioneAbilita() {
		Messaggio messaggio = new Messaggio(TipoMessaggio.CONFERMA, CONFERMA_CREAZIONE_ABILITA);
		return messaggio;
	}

	public static Messaggio erroreCaricamentoProposte() {
		Messaggio messaggio = new Messaggio(TipoMessaggio.AVVISO, ERRORE_CARICAMENTO_PROPOSTE);
		return messaggio;
	}
	
	public static Messaggio erroreModificaProposte() {
		Messaggio messaggio = new Messaggio(TipoMessaggio.AVVISO, ERRORE_MODIFICA_PROPOSTE);
		return messaggio;
	}

	public static Messaggio erroreCaricamentoMonitor() {
		Messaggio messaggio = new Messaggio(TipoMessaggio.AVVISO, ERRORE_CARICAMENTO_MONITOR);
		return messaggio;
	}
	
}
