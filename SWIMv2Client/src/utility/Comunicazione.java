package utility;

public final class Comunicazione {
	
	private static final String EMAIL_NON_VALIDA = 
			"L'indirizzo di posta elettronica specificato non rispetta il formato email.";
	
	private static final String NICKNAME_NON_DISPONIBILE = 
			"Il nickname scelto è già in uso! Scegliere un altro nickname e ritentare.";
	
	private static final String REGISTRAZIONE_COMPLETATA = 
			"Congratulazioni! La registrazione è avvenuta con successo. Ti è stata inviata una mail di conferma.";
	
	private static final String ERRORE_SERVLET = 
			"Ops! Qualcosa è andato storto, ci scusiamo per il disagio, accedi per modificare il tuo profilo.";
	
	private static final String CREDENZIALI_NON_VALIDE = 
			"Le tue credenziali di accesso non sono valide. Controllale meglio!";
	
	private static final String ERRORE_CREAZIONE_ABILITA = 
			"Ops! Qualcosa è andato storto durante la creazione dell'abilità.";
	
	private static final String CONFERMA_CREAZIONE_ABILITA = 
			"Complimenti! L'abilità è stata creata con successo.";
	
	private static final String ERRORE_CARICAMENTO_ABILITA = 
			"Ops! Qualcosa è andato storto durante il caricamento delle abilità.";
	
	private static final String ERRORE_MODIFICA_ABILITA = 
			"Ops! Qualcosa è andato storto durante la modifica delle abilità.";
	
	private static final String ERRORE_INVIO_PROPOSTA = 
			"Ops! Qualcosa è andato storto durante l'invio della proposta di abilità.";
	
	private static final String CONFERMA_INVIO_PROPOSTA = 
			"La proposta di abilità è stata inviata correttamente all'admin.";
	
	private static final String ERRORE_CARICAMENTO_PROPOSTE = 
			"Ops! Qualcosa è andato storto durante il caricamento delle proposte.";
	
	private static final String ERRORE_MODIFICA_PROPOSTE = 
			"Ops! Qualcosa è andato storto durante la modifica delle proposte.";
	
	private static final String ERRORE_RICHIESTA_AMICIZIA = 
			"Ops! qualcosa è andato storto durante l'invio della richiesta di amicizia.";
	
	private static final String CONFERMA_RICHIESTA_AMICIZIA = 
			"La richiesta di amicizia è stata inviata correttamente.";
	
	private static final String ERRORE_MODIFICA_RICHIESTE_AMICIZIA = 
			"Ops! Qualcosa è andato storto durante la modifica delle richiesta di amicizia.";
	
	private static final String ERRORE_MODIFICA_AMICIZIE = 
			"Ops! Qualcosa è andato storto durante la modifica delle amicizie.";
	
	private static final String ERRORE_RICHIESTA_AIUTO = 
			"Ops! qualcosa è andato storto durante l'invio della richiesta di aiuto.";
	
	private static final String CONFERMA_RICHIESTA_AIUTO = 
			"La richiesta d'aiuto è stata inviata correttamente.";
	
	private static final String ERRORE_CARICAMENTO_RICHIESTE_AIUTO = 
			"Ops! Qualcosa è andato storto durante il caricamento delle richieste di aiuto.";
	
	private static final String ERRORE_CARICAMENTO_AIUTI = 
			"Ops! Qualcosa è andato storto durante il caricamento degli aiuti.";
	
	private static final String ERRORE_CARICAMENTO_AIUTO = 
			"Ops! Qualcosa è andato storto durante il caricamento dell'aiuto da valutare.";
	
	private static final String ERRORE_CARICAMENTO_MONITOR = 
			"Ops! Qualcosa è andato storto durante il caricamento dei dati.";
	
	private static final String ERRORE_RILASCIO_FEEDBACK = 
			"Ops! Qualcosa è andato storto durante il rilascio del feedback";
	
	private static final String ERRORE_RICERCA = 
			"Ops! Qualcosa è andato storto durante l'esecuzione della ricerca.";
	
	private static final String ERRORE_MODICA_INFORMAZIONI = 
			"Ops! Qualcosa è andato storto durante la modifica delle informazioni.";
	
	private static final String ERRORE_CARICAMENTO_INFORMAZIONI =
			"Ops! Qualcosa è andato storto durante il caricamento delle informazioni.";
	
	private static final String FILE_AVATAR_TROPPO_GRANDE = 
			"L'immagine caricata supera le dimensioni massime accettate. Ti è stato settato l'avatar di dafault.";
	
	private static final String FILE_AVATAR_TROPPO_GRANDE_MODIFICA = 
			"L'immagine caricata supera le dimensioni massime accettate. Ti è stato lasciato l'avatar precedente.";
	
	private static final String FILE_ICONA_TROPPO_GRANDE = 
			"L'immagine caricata supera le dimensioni massime accettate. L'abilità non è stata creata.";
	
	private static final String CONFERMA_AVATAR_ABILITA = 
			"L'avatar e le abilità scelti sono stati settati correttamente.";
	
	private static final String CONFERMA_AVATAR = 
			"L'avatar scelto è stato settato correttamente.";
	
	private static final String CONFERMA_ABILITA = 
			"Le abilità scelte sono state settate correttamente. Ti è stato assegnato l'avatar di default.";
	
	private static final String CONFERMA_AVATAR_DEFAULT = 
			"Ti è stato assegnato l'avater di default. Per modificarlo accedi alla tua pagina personale.";
	
	private static final String CONFERMA_MODIFICA_INFORMAZIONI = 
			"La modifica è stata eseguita correttamente. Ti è stata inviata una mail di conferma.";
	
	private static final String CONFERMA_MODIFICA_ABILITA = 
			"La modifica delle abilità dichiarate è stata completata correttamente.";
	
	private static final String ERRORE_RICERCA_ABILITA = 
			"Non hai selezionato alcuna abilità! Per poter effettuare la ricerca per abilità devi sceglierne una.";
	
	
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
	
	public static Messaggio erroreCaricamentoAbilita() {
		return new Messaggio(TipoMessaggio.AVVISO, ERRORE_CARICAMENTO_ABILITA);
	}
	
	public static Messaggio erroreModificaAbilita() {
		return new Messaggio(TipoMessaggio.AVVISO, ERRORE_MODIFICA_ABILITA);
	}
	
	public static Messaggio erroreInvioProposta() {
		return new Messaggio(TipoMessaggio.AVVISO, ERRORE_INVIO_PROPOSTA);
	}
	
	public static Messaggio confermaInvioProposta() {
		return new Messaggio(TipoMessaggio.CONFERMA, CONFERMA_INVIO_PROPOSTA);
	}

	public static Messaggio erroreCaricamentoProposte() {
		return new Messaggio(TipoMessaggio.AVVISO, ERRORE_CARICAMENTO_PROPOSTE);
	}
	
	public static Messaggio erroreModificaProposte() {
		return new Messaggio(TipoMessaggio.AVVISO, ERRORE_MODIFICA_PROPOSTE);
	}
	
	public static Messaggio erroreRichiestaAmicizia() {
		return new Messaggio(TipoMessaggio.AVVISO, ERRORE_RICHIESTA_AMICIZIA);
	}
	
	public static Messaggio confermaRichiestaAmicizia() {
		return new Messaggio(TipoMessaggio.CONFERMA, CONFERMA_RICHIESTA_AMICIZIA);
	}
	
	public static Messaggio erroreModificaRichiesteAmicizia() {
		return new Messaggio(TipoMessaggio.AVVISO, ERRORE_MODIFICA_RICHIESTE_AMICIZIA);
	}
	
	public static Messaggio erroreModficaAmicizie() {
		return new Messaggio(TipoMessaggio.AVVISO, ERRORE_MODIFICA_AMICIZIE);
	}
	
	public static Messaggio erroreRichiestaAiuto() {
		return new Messaggio(TipoMessaggio.AVVISO, ERRORE_RICHIESTA_AIUTO);
	}
	
	public static Messaggio confermaRichiestaAiuto() {
		return new Messaggio(TipoMessaggio.CONFERMA, CONFERMA_RICHIESTA_AIUTO);
	}
	
	public static Messaggio erroreCaricamentoRichiesteAiuto() {
		return new Messaggio(TipoMessaggio.AVVISO, ERRORE_CARICAMENTO_RICHIESTE_AIUTO);
	}
	
	public static Messaggio erroreCaricamentoAiuti() {
		return new Messaggio(TipoMessaggio.AVVISO, ERRORE_CARICAMENTO_AIUTI);
	}
	
	public static Messaggio erroreCaricamentoAiuto() {
		return new Messaggio(TipoMessaggio.AVVISO, ERRORE_CARICAMENTO_AIUTO);
	}

	public static Messaggio erroreCaricamentoMonitor() {
		return new Messaggio(TipoMessaggio.AVVISO, ERRORE_CARICAMENTO_MONITOR);
	}
	
	public static Messaggio erroreRilascioFeedback() {
		return new Messaggio(TipoMessaggio.AVVISO, ERRORE_RILASCIO_FEEDBACK);
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
	
	public static Messaggio fileAvatarTroppoGrandeModifica() {
		return new Messaggio(TipoMessaggio.ERRORE, FILE_AVATAR_TROPPO_GRANDE_MODIFICA);
	}
	
	public static Messaggio fileIconaTroppoGrande() {
		return new Messaggio(TipoMessaggio.ERRORE, FILE_ICONA_TROPPO_GRANDE);
	}
	
	public static Messaggio confermaAvatarAbilita() {
		return new Messaggio(TipoMessaggio.CONFERMA, CONFERMA_AVATAR_ABILITA);
	}
	
	public static Messaggio confermaAvatar() {
		return new Messaggio(TipoMessaggio.CONFERMA, CONFERMA_AVATAR);
	}
	
	public static Messaggio confermaAbilita() {
		return new Messaggio(TipoMessaggio.CONFERMA, CONFERMA_ABILITA);
	}
	
	public static Messaggio confermaAvatarDefault() {
		return new Messaggio(TipoMessaggio.CONFERMA, CONFERMA_AVATAR_DEFAULT);
	}
	public static Messaggio confermaModificaInformazioni(){
		return new Messaggio(TipoMessaggio.CONFERMA, CONFERMA_MODIFICA_INFORMAZIONI);
	}
	
	public static Messaggio confermaModificaAbilita() {
		return new Messaggio(TipoMessaggio.CONFERMA, CONFERMA_MODIFICA_ABILITA);
	}
	
	public static Messaggio erroreRicercaAbilita() {
		return new Messaggio(TipoMessaggio.ERRORE, ERRORE_RICERCA_ABILITA);
	}
	
}
