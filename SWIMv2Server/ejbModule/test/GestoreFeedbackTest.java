package test;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.naming.Context;
import org.junit.BeforeClass;
import org.junit.Test;
import entity.Aiuto;
import session.GestoreAbilitaRemote;
import session.GestoreAiutoRemote;
import session.GestoreFeedbackRemote;
import session.GestoreUserRemote;

/**
 * Classe di test che verifica i singoli metodi definiti nella classe GestoreFeedback del package session
 * 
 * @author Eugenio Guidetti - Claudio Fratto
 *
 */
public class GestoreFeedbackTest {
	/*
	 * Definisco il contesto per poter agganciare il test alle session
	 */
	static private Context jndiContext;  
	static private GestoreFeedbackRemote gestoreFeedbackRemote;
	static private GestoreAiutoRemote gestoreAiutoRemote;
	static private GestoreUserRemote gestoreUserRemote;
	static private GestoreAbilitaRemote gestoreAbilitaRemote;

	/*
	 * Definisco il contesto, agganciando i gestori remoti attraverso le lookup
	 * Definisco le condizioni iniziali del database inserendo l'utente admin
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		jndiContext = SupportoTest.getInitialContext();

		Object refFeedback = jndiContext.lookup("GestoreFeedbackJNDI");
		gestoreFeedbackRemote = (GestoreFeedbackRemote) refFeedback;

		Object refAiuto = jndiContext.lookup("GestoreAiutoJNDI");
		gestoreAiutoRemote = (GestoreAiutoRemote) refAiuto;

		Object refUser = jndiContext.lookup("GestoreUserJNDI");
		gestoreUserRemote = (GestoreUserRemote) refUser;

		Object refAbilita = jndiContext.lookup("GestoreAbilitaJNDI");
		gestoreAbilitaRemote = (GestoreAbilitaRemote) refAbilita;
	}


	/**
	 * Verifica il funzionamento del metodo rilascia(long idAiuto) definito nella classe GestoreFeedback del package session
	 */
	@Test
	public void testRilascia(){

		//Test: verifico che il database sia vuoto prima di iniziare il test
		assertEquals(true, SupportoTest.verificaDatabaseVuoto());

		//creo due user
		gestoreUserRemote.registra("toto", "prova", "toto@mail.com", "salvatore", "rossi", "path/toto.png", "palermo", "maschio", 1967);
		gestoreUserRemote.registra("pippo", "pwd", "pippo@mail.com", "filippo", "roi", "/image/pippo.png", "cagliari", "maschio", 1988);

		//aggiungo un'abilit� al sistema
		gestoreAbilitaRemote.crea("cameriere", "per serate di gale", "path");

		//recupero l'id dell'abilit� creata (unica nel sistema)
		long idAbilita = gestoreAbilitaRemote.getAbilitaSistema().get(0).getId();

		//lo user "toto" invia una richiesta di aiuto allo user "pippo"
		Calendar momentoRichiesta = new GregorianCalendar();
		gestoreAiutoRemote.inviaRichiesta("toto", "pippo", idAbilita, "urgentissimo", momentoRichiesta);

		//inserisco una sleep per far trascorrere qualche secondo
		try {
			Thread.sleep(2000);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}

		//recupero la richiesta di aiuto da accettare
		Aiuto aiutoDaAccettare = gestoreAiutoRemote.getRichiesteRicevute("pippo").get(0);

		//Test: lo user "pippo" accetta la richiesta di aiuto ricevuta dallo user "toto"
		Calendar momentoAccettazione = new GregorianCalendar();
		gestoreAiutoRemote.accettaRichiesta(aiutoDaAccettare.getId(), momentoAccettazione);

		//inserisco una sleep per far trascorrere qualche secondo
		try {
			Thread.sleep(2000);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}

		//Test: lo user "toto" rilascia il feedback per l'aiuto ricevuto
		Calendar momentoRilascio = new GregorianCalendar();
		Aiuto aiutoRicevuto = gestoreAiutoRemote.getAiutiRicevuti("toto").get(0);
		assertEquals(true, gestoreFeedbackRemote.rilascia(aiutoRicevuto.getId(), 3, "potevi fare meglio", momentoRilascio));

		//Test: svuoto il database e verifico che non vi rimanga pi� alcuna informazione
		assertEquals(true, SupportoTest.svuotaDB());
	}

	/**
	 * Verifica il funzionamento del metodo getFeedback(long idAiuto) definito nella classe GestoreFeedback del package sessione  
	 */
	@Test
	public void testGetFeedback(){

		//Test: verifico che il database sia vuoto prima di iniziare il test
		assertEquals(true, SupportoTest.verificaDatabaseVuoto());

		//creo due user
		gestoreUserRemote.registra("toto", "prova", "toto@mail.com", "salvatore", "rossi", "path/toto.png", "palermo", "maschio", 1967);
		gestoreUserRemote.registra("pippo", "pwd", "pippo@mail.com", "filippo", "roi", "/image/pippo.png", "cagliari", "maschio", 1988);

		//aggiungo un'abilit� al sistema
		gestoreAbilitaRemote.crea("cameriere", "per serate di gale", "path");

		//recupero l'id dell'abilit� creata (unica nel sistema)
		long idAbilita = gestoreAbilitaRemote.getAbilitaSistema().get(0).getId();

		//lo user "toto" invia una richiesta di aiuto allo user "pippo"
		Calendar momentoRichiesta = new GregorianCalendar();
		gestoreAiutoRemote.inviaRichiesta("toto", "pippo", idAbilita, "urgentissimo", momentoRichiesta);

		//inserisco una sleep per far trascorrere qualche secondo
		try {
			Thread.sleep(2000);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}

		//recupero la richiesta di aiuto da accettare
		Aiuto aiutoDaAccettare = gestoreAiutoRemote.getRichiesteRicevute("pippo").get(0);

		//Test: lo user "pippo" accetta la richiesta di aiuto ricevuta dallo user "toto"
		Calendar momentoAccettazione = new GregorianCalendar();
		gestoreAiutoRemote.accettaRichiesta(aiutoDaAccettare.getId(), momentoAccettazione);

		//inserisco una sleep per far trascorrere qualche secondo
		try {
			Thread.sleep(2000);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}

		//lo user "toto" rilascia il feedback per l'aiuto ricevuto
		Calendar momentoRilascio = new GregorianCalendar();
		Aiuto aiutoRicevuto = gestoreAiutoRemote.getAiutiRicevuti("toto").get(0);
		long idAiutoRicevuto = aiutoRicevuto.getId();
		gestoreFeedbackRemote.rilascia(idAiutoRicevuto, 3, "potevi fare meglio", momentoRilascio);

		//Test: esiste il feedback associato all'aiuto ricevuto dallo user "toto"
		assertEquals(false, gestoreFeedbackRemote.getFeedback(aiutoRicevuto.getId()) == null  );

		//recupero l'aiuto associato al feedback
		Aiuto aiutoValutato = gestoreFeedbackRemote.getFeedback(idAiutoRicevuto).getAiutoValutato();

		//Test: verifico che l'aiuto valutato recuperato si riferisca all'aiuto ricevuto
		assertEquals(idAiutoRicevuto, aiutoValutato.getId());
		assertEquals(aiutoRicevuto.getAbilitaRichiesta().getId(), aiutoValutato.getAbilitaRichiesta().getId());
		assertEquals(aiutoRicevuto.getDescrizione(), aiutoValutato.getDescrizione());
		assertEquals(aiutoRicevuto.getMomentoRichiesta(), aiutoValutato.getMomentoRichiesta());
		assertEquals(aiutoRicevuto.getMomentoAccettazione(), aiutoValutato.getMomentoAccettazione());
		assertEquals(aiutoRicevuto.getUserRichiedente().getNickname(), aiutoValutato.getUserRichiedente().getNickname());
		assertEquals(aiutoRicevuto.getUserDestinatario().getNickname(), aiutoValutato.getUserDestinatario().getNickname());

		//Test: svuoto il database e verifico che non vi rimanga pi� alcuna informazione
		assertEquals(true, SupportoTest.svuotaDB());	
	}

}
