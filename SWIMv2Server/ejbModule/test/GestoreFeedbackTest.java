package test;

import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.naming.Context;
import org.junit.BeforeClass;
import org.junit.Test;
import entity.Abilita;
import entity.Aiuto;
import session.GestoreAbilitaRemote;
import session.GestoreAiutoRemote;
import session.GestoreFeedbackRemote;
import session.GestoreUserRemote;

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

	@Test
	public void test(){
		assert(true);
	}
	/*
	@Test
	public void testRilascia(){
		
		//creo due user
		gestoreUserRemote.registra("toto", "prova", "toto@mail.com", "salvatore", "rossi", "path/toto.png", "palermo", "maschio", 1967);
		gestoreUserRemote.registra("pippo", "pwd", "pippo@mail.com", "filippo", "roi", "/image/pippo.png", "cagliari", "maschio", 1988);

		//aggiungo un'abilità al sistema
		gestoreAbilitaRemote.crea("cameriere", "per serate di gale", "path");

		//recupero l'id dell'abilità creata (unica nel sistema)
		long idAbilita = gestoreAbilitaRemote.getAbilitaSistema().get(0).getId();

		//lo user "toto" invia una richiesta di aiuto allo user "pippo"
		Calendar momentoRichiesta = new GregorianCalendar();
		gestoreAiutoRemote.inviaRichiesta("toto", "pippo", idAbilita, "urgentissimo", momentoRichiesta);

		//inserisco una sleep per far trascorrere qualche secondo
		try {
			Thread.sleep(4000);
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
			Thread.sleep(4000);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}

		//Test: lo user "toto" rilascia il feedback per l'aiuto ricevuto
		Calendar momentoRilascio = new GregorianCalendar();
		Aiuto aiutoRicevuto = gestoreAiutoRemote.getAiutiRicevuti("toto").get(0);
		assertEquals(true, gestoreFeedbackRemote.rilascia(aiutoRicevuto.getId(), 3, "potevi fare meglio", momentoRilascio));

		
		
		/*
		
		//Test: verifico che il numero di aiuti ricevuti dallo user "toto" sia pari ad 1
		assertEquals(1, gestoreAiutoRemote.getAiutiRicevuti("toto").size());
		//Test: verifico che il numero di aiuti forniti dallo user "pippo" sia pari ad 1
		assertEquals(1, gestoreAiutoRemote.getAiutiForniti("pippo").size());


		//recupero la richiesta di aiuto accettata
		Aiuto richiestaAiutoAccettata = gestoreAiutoRemote.getAiutiRicevuti("toto").get(0);

		//Test: verifico che il momento di accettazione della richiesta di aiuto accettata sia diverso da null
		assertEquals(false, richiestaAiutoAccettata.getMomentoAccettazione() == null);

		//Elimino gli utenti e di conseguenza tutte le richieste di amicizia o amicizie già allacciate da essi
		gestoreUserRemote.elimina("toto");
		gestoreUserRemote.elimina("pippo");
		//Elimino tutte le abilità create per il test successivo
		for(Abilita a: gestoreAbilitaRemote.getAbilitaSistema()){
			gestoreAbilitaRemote.elimina(a.getId());
		}
		
	}
	@Test
	public void test(){
		assertEquals(true, true);
	}
	*/
}
