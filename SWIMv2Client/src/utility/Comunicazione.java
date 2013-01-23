package utility;

public final class Comunicazione {
	
	private static final String EMAIL_NON_VALIDA = 
			"L'indirizzo di posta elettronica specificato non rispetta il formato email.";
	private static final String NICKNAME_NON_DISPONIBILE = 
			"Il nickname scelto è già in uso! Scegliere un altro nickname e ritentare.";
	private static final String REGISTRAZIONE_COMPLETATA = 
			"Congratulazioni! La registrazione è terminata con successo.";
	private static final String ERRORE_SERVLET = 
			"Ops! Qualcosa è andato storto, ci scusiamo per il disagio, accedi per modificare il tuo profilo.";
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
	private static final String ERRORE_MODIFICA_RICHIESTE_AMICIZIA = 
			"Ops! Qualcosa è andato storto durante la modifica delle richiesta di amicizia.";
	private static final String ERRORE_CARICAMENTO_MONITOR = 
			"Ops! Qualcosa è andato storto durante il caricamento dei dati.";
	private static final String ERRORE_CARICAMENTO_ABILITA = 
			"Ops! Qualcosa è andato storto durante il caricamento delle abilita.";
	private static final String ERRORE_RICERCA = 
			"Ops! Qualcosa è andato storto durante l'esecuzione della ricerca.";
	private static final String ERRORE_MODICA_INFORMAZIONI = 
			"Ops! Qualcosa è andato storto durante la modifica delle informazioni.";
	private static final String ERRORE_CARICAMENTO_INFORMAZIONI =
			"Ops! Qualcosa è andato storto durante il caricamento delle informazioni.";
	private static final String FILE_AVATAR_TROPPO_GRANDE = 
			"L'immagine caricata supera le dimensioni massime accettate, ti è stato settato l'avatar di dafault.";
	private static final String FILE_ICONA_TROPPO_GRANDE = 
			"L'immagine caricata supera le dimensioni massime accettate, l'abilità non è stata creata.";
	private static final String CONFERMA_AVATAR_ABILITA = 
			"L'avatar e le abilità scelta sono stati settati correttamente";
	
	
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
	
	public static Messaggio erroreModificaRichiesteAmicizia() {
		return new Messaggio(TipoMessaggio.AVVISO, ERRORE_MODIFICA_RICHIESTE_AMICIZIA);
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
	
	public static Messaggio erroreModificaInformazioni() {
		return new Messaggio(TipoMessaggio.AVVISO, ERRORE_MODICA_INFORMAZIONI);
	}
	
	public static Messaggio erroreCaricamentoInformazioni() {
		return new Messaggio(TipoMessaggio.AVVISO, ERRORE_CARICAMENTO_INFORMAZIONI);
	}
	
	public static Messaggio fileAvatarTroppoGrande() {
		return new Messaggio(TipoMessaggio.ERRORE, FILE_AVATAR_TROPPO_GRANDE);
	}
	
	public static Messaggio fileIconaTroppoGrande() {
		return new Messaggio(TipoMessaggio.ERRORE, FILE_ICONA_TROPPO_GRANDE);
	}
	
	public static Messaggio confermaAvatarAbilita() {
		return new Messaggio(TipoMessaggio.CONFERMA, CONFERMA_AVATAR_ABILITA);
	}
	
}
