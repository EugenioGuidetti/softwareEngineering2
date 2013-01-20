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
import session.GestoreUserRemote;

public class GestoreAiutoTest {
	/*
	 * Definisco il contesto per poter agganciare il test alle session
	 */
	static private Context jndiContext;  
	static private GestoreAiutoRemote gestoreAiutoRemote;
	static private GestoreUserRemote gestoreUserRemote;
	static private GestoreAbilitaRemote gestoreAbilitaRemote;

	/*
	 * Definisco il contesto, agganciando i gestori remoti attraverso le lookup
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		jndiContext = SupportoTest.getInitialContext();

		Object refAiuto = jndiContext.lookup("GestoreAiutoJNDI");
		gestoreAiutoRemote = (GestoreAiutoRemote) refAiuto;

		Object refUser = jndiContext.lookup("GestoreUserJNDI");
		gestoreUserRemote = (GestoreUserRemote) refUser;

		Object refAbilita = jndiContext.lookup("GestoreAbilitaJNDI");
		gestoreAbilitaRemote = (GestoreAbilitaRemote) refAbilita;
	}

	/**
	 * Verifica il funzionamento dei metodi:
	 * 1) getRichiesteInviate(String nicknameRichiedente)
	 * 2) getRichiesteRicevute(String nicknameDestinatario)
	 * 3) inviaRichiesta(String nicknameRichiedente, String nicknameDestinatario, long idAbilitaRichiesta, String descrizione, Calendar momentoRichiesta)
	 * definiti nella classe GestoreAiuto del package session
	 */
	@Test
	public void testGetRichiesteEInviaRichiesta(){

		//creo due user
		gestoreUserRemote.registra("toto", "prova", "toto@mail.com", "salvatore", "rossi", "path/toto.png", "palermo", "maschio", 1967);
		gestoreUserRemote.registra("pippo", "pwd", "pippo@mail.com", "filippo", "roi", "/image/pippo.png", "cagliari", "maschio", 1988);
		//aggiungo un'abilità al sistema
		gestoreAbilitaRemote.crea("cameriere", "per serate di gale", "path");

		//recupero l'id dell'abilità creata (unica nel sistema)
		long idAbilita = gestoreAbilitaRemote.getAbilitaSistema().get(0).getId();

		//Test: all'inzio nel sistema lo user "toto" non ha nessuna richiesta di aiuto inviata
		assertEquals(0, gestoreAiutoRemote.getRichiesteInviate("toto").size());

		//Test: all'inzio nel sistema lo user "pippo" non ha nessuna richiesta di aiuto ricevuta
		assertEquals(0, gestoreAiutoRemote.getRichiesteRicevute("pippo").size());

		//Test: lo user "toto" invia una richiesta di aiuto allo user "pippo"
		Calendar momentoRichiesta = new GregorianCalendar();
		assertEquals(true, gestoreAiutoRemote.inviaRichiesta("toto", "pippo", idAbilita, "urgentissimo", momentoRichiesta));

		//Test: dopo l'invio della richiesta lo user "toto" ha 1 richiesta di aiuto inviata
		assertEquals(1, gestoreAiutoRemote.getRichiesteInviate("toto").size());

		//Test: dopo l'invio della richiesta lo user "pippo" ha 1 richiesta di aiuto ricevuta
		assertEquals(1, gestoreAiutoRemote.getRichiesteRicevute("pippo").size());

		//Recupero la richiesta di aiuto inviata
		Aiuto richiestaAiutoInviata = gestoreAiutoRemote.getRichiesteInviate("toto").get(0);

		//Test: verifico che il momento di richiesta sia diverso da null
		assertEquals(false, richiestaAiutoInviata.getMomentoRichiesta() == null);
		//Test: verifico che il momento di accettazione sia null
		assertEquals(null, richiestaAiutoInviata.getMomentoAccettazione());
		//Test: verifico che lo user richiedente dell'aiuto sia "toto"
		assertEquals("toto", richiestaAiutoInviata.getUserRichiedente().getNickname());
		//Test: verifico che lo user destinatario dell'aiuto sia "pippo"
		assertEquals("pippo", richiestaAiutoInviata.getUserDestinatario().getNickname());
		//Test: verifico che l'abilità della richiesta corrisponda a quella identificata da idAbilita
		assertEquals(idAbilita, richiestaAiutoInviata.getAbilitaRichiesta().getId());

		//Elimino gli utenti e di conseguenza tutte le richieste di aiuto o amicizie già allacciate da essi
		gestoreUserRemote.elimina("toto");
		gestoreUserRemote.elimina("pippo");		
		//Elimino tutte le abilità create per il test successivo
		for(Abilita a: gestoreAbilitaRemote.getAbilitaSistema()){
			gestoreAbilitaRemote.elimina(a.getId());
		}
	}

	/**
	 * Verifica il funzionamento dei metodi:
	 * 1) getAiutiRicevuti(String nickname)
	 * 2) getAiutiForniti(String nickname)
	 * 3) accettaRichiesta(long id, Calendar momentoAccettazione) 
	 * definiti nella classe GestoreAiuto del package session
	 */
	@Test
	public void testAccettaRichiesta(){
		//creo due user
		gestoreUserRemote.registra("toto", "prova", "toto@mail.com", "salvatore", "rossi", "path/toto.png", "palermo", "maschio", 1967);
		gestoreUserRemote.registra("pippo", "pwd", "pippo@mail.com", "filippo", "roi", "/image/pippo.png", "cagliari", "maschio", 1988);

		//aggiungo un'abilità al sistema
		gestoreAbilitaRemote.crea("cameriere", "per serate di gale", "path");

		//recupero l'id dell'abilità creata (unica nel sistema)
		long idAbilita = gestoreAbilitaRemote.getAbilitaSistema().get(0).getId();
		
		//Test: verifico che il numero di aiuti ricevuti dallo user "toto" sia pari a 0
		assertEquals(0, gestoreAiutoRemote.getAiutiRicevuti("toto").size());
		//Test: verifico che il numero di aiuti forniti dallo lo user "pippo" sia pari a 0
		assertEquals(0, gestoreAiutoRemote.getAiutiForniti("pippo").size());

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
		assertEquals(true, gestoreAiutoRemote.accettaRichiesta(aiutoDaAccettare.getId(), momentoAccettazione));

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
	
	/**
	 * Verifica del funzionamento del metodo rifiutaRichiesta(long id) definito nella classe GestoreAiuto
	 * del package sessione
	 */
	@Test
	public void testRifiutaRichiesta(){

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
		
		//recupero la richiesta di aiuto da rifiutare
		Aiuto aiutoDaRifiutare = gestoreAiutoRemote.getRichiesteRicevute("pippo").get(0);
		
		//Test: lo user "pippo" rifiuta la richiesta di aiuto ricevuta dallo user "toto"
		assertEquals(true, gestoreAiutoRemote.rifiutaRichiesta(aiutoDaRifiutare.getId()));

		//Test: verifico che il numero delle richieste di aiuto inviate dallo user "toto" sia pari a 0
		assertEquals(0, gestoreAiutoRemote.getRichiesteInviate("toto").size());

		//Test: verifico che il numero delle richieste di aiuto ricevute dallo user "pippo" sia pari a 0
		assertEquals(0, gestoreAiutoRemote.getRichiesteRicevute("pippo").size());

		//Elimino gli utenti e di conseguenza tutte le richieste di amicizia o amicizie già allacciate da essi
		gestoreUserRemote.elimina("toto");
		gestoreUserRemote.elimina("pippo");
		//Elimino tutte le abilità create per il test successivo
		for(Abilita a: gestoreAbilitaRemote.getAbilitaSistema()){
			gestoreAbilitaRemote.elimina(a.getId());
		}
	}


}
