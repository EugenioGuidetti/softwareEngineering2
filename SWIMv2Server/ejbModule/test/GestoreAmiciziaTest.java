package test;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.naming.Context;
import org.junit.BeforeClass;
import org.junit.Test;
import entity.Amicizia;
import session.GestoreAmiciziaRemote;
import session.GestoreUserRemote;

/**
 * Classe di test che verifica i singoli metodi definiti nella classe GestoreAmicizia del package session
 * 
 * @author Eugenio Guidetti - Claudio Fratto
 *
 */
public class GestoreAmiciziaTest {
	/*
	 * Definisco il contesto per poter agganciare il test alle session
	 */
	static private Context jndiContext;  
	static private GestoreAmiciziaRemote gestoreAmiciziaRemote;
	static private GestoreUserRemote gestoreUserRemote;

	/*
	 * Definisco il contesto, agganciando i gestori remoti attraverso le lookup
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		jndiContext = SupportoTest.getInitialContext();

		Object refAmicizia = jndiContext.lookup("GestoreAmiciziaJNDI");
		gestoreAmiciziaRemote = (GestoreAmiciziaRemote) refAmicizia;

		Object refUser = jndiContext.lookup("GestoreUserJNDI");
		gestoreUserRemote = (GestoreUserRemote) refUser;
	}

	/**
	 * Verifica il funzionamento dei metodi:
	 * 1) getRichiesteInviate(String nicknameRichiedente)
	 * 2) getRichiesteRicevute(String nicknameDestinatario)
	 * 3) inviaRichiesta(String nicknameRichiedente, String nicknameDestinatario, Calendar momentoRichiesta)
	 * definiti nella classe GestoreAmicizia del package session
	 */
	@Test
	public void testGetRichiesteEInviaRichiesta(){

		//Test: verifico che il database sia vuoto prima di iniziare il test
		assertEquals(true, SupportoTest.verificaDatabaseVuoto());
		
		//creo due user
		gestoreUserRemote.registra("toto", "prova", "toto@mail.com", "salvatore", "rossi", "path/toto.png", "palermo", "maschio", 1967);
		gestoreUserRemote.registra("pippo", "pwd", "pippo@mail.com", "filippo", "roi", "/image/pippo.png", "cagliari", "maschio", 1988);

		//Test: all'inzio nel sistema lo user "toto" non ha nessuna richiesta di amicizia inviata
		assertEquals(0, gestoreAmiciziaRemote.getRichiesteInviate("toto").size());

		//Test: all'inzio nel sistema lo user "pippo" non ha nessuna richiesta di amicizia ricevuta
		assertEquals(0, gestoreAmiciziaRemote.getRichiesteRicevute("pippo").size());

		//Test: lo user "toto" invia una richiesta di amicizia allo user "pippo"
		Calendar momentoRichiesta = new GregorianCalendar();
		assertEquals(true, gestoreAmiciziaRemote.inviaRichiesta("toto", "pippo", momentoRichiesta));

		//Test: dopo l'invio della richiesta lo user "toto" ha 1 richiesta di amicizia inviata
		assertEquals(1, gestoreAmiciziaRemote.getRichiesteInviate("toto").size());

		//Test: dopo l'invio della richiesta lo user "pippo" ha 1 richiesta di amicizia ricevuta
		assertEquals(1, gestoreAmiciziaRemote.getRichiesteRicevute("pippo").size());

		//Recupero la richiesta di amicizia inviata
		Amicizia amiciziaInviata = gestoreAmiciziaRemote.getRichiesteInviate("toto").get(0);

		//Test: verifico che il momento di richiesta sia diverso da null
		assertEquals(false, amiciziaInviata.getMomentoRichiesta() == null);
		//Test: verifico che il momento di accettazione sia null
		assertEquals(null, amiciziaInviata.getMomentoAccettazione());
		//Test: verifico che lo user richiedente dell'amicizia sia "toto"
		assertEquals("toto", amiciziaInviata.getUserRichiedente().getNickname());
		//Test: verifico che lo user destinatario dell'amicizia sia "pippo"
		assertEquals("pippo", amiciziaInviata.getUserDestinatario().getNickname());

		//Test: svuoto il database e verifico che non vi rimanga pi� alcuna informazione
		assertEquals(true, SupportoTest.svuotaDB());
	}

	/**
	 * Verifica che uno user che abbia gi� inoltrato una richiesta di amicizia ad un altro user non ne possa inoltrare un'altra
	 * mentre la prima � ancora in sospeso
	 */
	@Test
	public void testInviaRichiestaGiaPresente(){

		//Test: verifico che il database sia vuoto prima di iniziare il test
		assertEquals(true, SupportoTest.verificaDatabaseVuoto());

		//creo due user
		gestoreUserRemote.registra("toto", "prova", "toto@mail.com", "salvatore", "rossi", "path/toto.png", "palermo", "maschio", 1967);
		gestoreUserRemote.registra("pippo", "pwd", "pippo@mail.com", "filippo", "roi", "/image/pippo.png", "cagliari", "maschio", 1988);

		//Test: lo user "toto" invia una richiesta di amicizia allo user "pippo"
		Calendar momentoRichiesta = new GregorianCalendar();
		assertEquals(true, gestoreAmiciziaRemote.inviaRichiesta("toto", "pippo", momentoRichiesta));

		//Recupero la richiesta di amicizia inviata
		Amicizia amiciziaInviata = gestoreAmiciziaRemote.getRichiesteInviate("toto").get(0);

		//Test: verifico che il momento di accettazione sia null (richiesta in sospeso)
		assertEquals(null, amiciziaInviata.getMomentoAccettazione());

		//Test: lo user "toto" invia una nuova richiesta di amicizia allo user "pippo" nonostante ci sia una richiesta in sospeso
		momentoRichiesta = new GregorianCalendar();
		assertEquals(false, gestoreAmiciziaRemote.inviaRichiesta("toto", "pippo", momentoRichiesta));

		//Test: svuoto il database e verifico che non vi rimanga pi� alcuna informazione
		assertEquals(true, SupportoTest.svuotaDB());
	}


	/**
	 * Verifica il funzionamento dei metodi:
	 * 1) getAmicizieAllacciate(String nickname)
	 * 2) accettaRichiesta(long id, Calendar momentoAccettazione) definito nella classe
	 * GestoreAmicizia del package session
	 */
	@Test
	public void testGetAmicizieAllacciateEAccettaRichiesta(){
	
		//Test: verifico che il database sia vuoto prima di iniziare il test
		assertEquals(true, SupportoTest.verificaDatabaseVuoto());

		//creo due user
		gestoreUserRemote.registra("toto", "prova", "toto@mail.com", "salvatore", "rossi", "path/toto.png", "palermo", "maschio", 1967);
		gestoreUserRemote.registra("pippo", "pwd", "pippo@mail.com", "filippo", "roi", "/image/pippo.png", "cagliari", "maschio", 1988);

		//Test: verifico che lo user "toto" non abbia amicizia allacciate con altri user
		assertEquals(0, gestoreAmiciziaRemote.getAmicizieAllacciate("toto").size());
		//Test: verifico che lo user "pippo" non abbia amicizia allacciate con altri user
		assertEquals(0, gestoreAmiciziaRemote.getAmicizieAllacciate("pippo").size());

		//lo user "toto" invia una richiesta di amicizia allo user "pippo"
		Calendar momentoRichiesta = new GregorianCalendar();
		gestoreAmiciziaRemote.inviaRichiesta("toto", "pippo", momentoRichiesta);

		//inserisco una sleep per far trascorrere qualche secondo
		try {
			Thread.sleep(2000);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}

		//recupero la richiesta di amicizia da accettare
		Amicizia amiciziaDaAccettare = gestoreAmiciziaRemote.getRichiesteRicevute("pippo").get(0);

		//Test: lo user "pippo" accetta la richiesta di amicizia ricevuta dallo user "toto"
		Calendar momentoAccettazione = new GregorianCalendar();
		assertEquals(true, gestoreAmiciziaRemote.accettaRichiesta(amiciziaDaAccettare.getId(), momentoAccettazione));

		//Test: verifico che lo user "toto" abbia un'amicizia allacciata
		assertEquals(1, gestoreAmiciziaRemote.getAmicizieAllacciate("toto").size());
		//Test: verifico che lo user "pippo" non abbia alcuna amicizia allacciata (TEST AMICIZIA NON BIDIREZIONALE)
		assertEquals(0, gestoreAmiciziaRemote.getAmicizieAllacciate("pippo").size());


		//recupero la richiesta di amicizia accettata
		Amicizia amiciziaAccettata = gestoreAmiciziaRemote.getAmicizieAllacciate("toto").get(0);

		//Test: verifico che il momento di accettazione della richiesta di amicizia accettata sia diverso da null
		assertEquals(false, amiciziaAccettata.getMomentoAccettazione() == null);

		//Test: svuoto il database e verifico che non vi rimanga pi� alcuna informazione
		assertEquals(true, SupportoTest.svuotaDB());
	}

	/**
	 * Verifica che uno user che abbia gi� allacciato un rapporto di amicizia con un altro user non possa inviare a quest'ultimo
	 * un'altra richiesta di amicizia
	 */
	@Test
	public void testInviaRichiestaGiaAmici(){

		//Test: verifico che il database sia vuoto prima di iniziare il test
		assertEquals(true, SupportoTest.verificaDatabaseVuoto());

		//creo due user
		gestoreUserRemote.registra("toto", "prova", "toto@mail.com", "salvatore", "rossi", "path/toto.png", "palermo", "maschio", 1967);
		gestoreUserRemote.registra("pippo", "pwd", "pippo@mail.com", "filippo", "roi", "/image/pippo.png", "cagliari", "maschio", 1988);

		//lo user "toto" invia una richiesta di amicizia allo user "pippo"
		Calendar momentoRichiesta = new GregorianCalendar();
		gestoreAmiciziaRemote.inviaRichiesta("toto", "pippo", momentoRichiesta);

		//inserisco una sleep per far trascorrere qualche secondo
		try {
			Thread.sleep(2000);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}

		//recupero la richiesta di amicizia da accettare
		Amicizia amiciziaDaAccettare = gestoreAmiciziaRemote.getRichiesteRicevute("pippo").get(0);

		//Test: lo user "pippo" accetta la richiesta di amicizia ricevuta dallo user "toto"
		Calendar momentoAccettazione = new GregorianCalendar();
		gestoreAmiciziaRemote.accettaRichiesta(amiciziaDaAccettare.getId(), momentoAccettazione);

		//recupero la richiesta di amicizia accettata
		Amicizia amiciziaAccettata = gestoreAmiciziaRemote.getAmicizieAllacciate("toto").get(0);

		//Test: verifico che il momento di accettazione della richiesta di amicizia accettata sia diverso da null
		assertEquals(false, amiciziaAccettata.getMomentoAccettazione() == null);

		//lo user "toto" invia una nuova richiesta di amicizia allo user "pippo"
		momentoRichiesta = new GregorianCalendar();
		assertEquals(false, gestoreAmiciziaRemote.inviaRichiesta("toto", "pippo", momentoRichiesta));

		//Test: svuoto il database e verifico che non vi rimanga pi� alcuna informazione
		assertEquals(true, SupportoTest.svuotaDB());
	}


	/**
	 * Verifica il funzionamento del metodo rimuovi(long id) definito nella classe GestoreAmicizia del package session
	 * utilizzato per non accettare una richiesta di amicizia ricevuta
	 */
	@Test
	public void testRimuovi(){

		//Test: verifico che il database sia vuoto prima di iniziare il test
		assertEquals(true, SupportoTest.verificaDatabaseVuoto());

		//creo due user
		gestoreUserRemote.registra("toto", "prova", "toto@mail.com", "salvatore", "rossi", "path/toto.png", "palermo", "maschio", 1967);
		gestoreUserRemote.registra("pippo", "pwd", "pippo@mail.com", "filippo", "roi", "/image/pippo.png", "cagliari", "maschio", 1988);

		//Test: all'inzio nel sistema lo user "toto" non ha nessuna richiesta di amicizia inviata
		assertEquals(0, gestoreAmiciziaRemote.getRichiesteInviate("toto").size());

		//Test: all'inzio nel sistema lo user "pippo" non ha nessuna richiesta di amicizia ricevuta
		assertEquals(0, gestoreAmiciziaRemote.getRichiesteRicevute("pippo").size());

		//lo user "toto" invia una richiesta di amicizia allo user "pippo"
		Calendar momentoRichiesta = new GregorianCalendar();
		gestoreAmiciziaRemote.inviaRichiesta("toto", "pippo", momentoRichiesta);


		//recupero la richiesta di amicizia da non accettare
		Amicizia amiciziaDaNonAccettare = gestoreAmiciziaRemote.getRichiesteRicevute("pippo").get(0);

		//Test: lo user "pippo" rifiuta la richiesta di amicizia ricevuta dallo user "toto"
		assertEquals(true, gestoreAmiciziaRemote.rimuovi(amiciziaDaNonAccettare.getId()));

		//Test: verifico che lo user "toto" non abbia richieste di amicizia inviate in sospeso
		assertEquals(0, gestoreAmiciziaRemote.getRichiesteInviate("toto").size());

		//Test: verifico che lo user "pippo" non abbia nessuna richiesta di amicizia ricevuta in sospeso
		assertEquals(0, gestoreAmiciziaRemote.getRichiesteRicevute("pippo").size());

		//Test: svuoto il database e verifico che non vi rimanga pi� alcuna informazione
		assertEquals(true, SupportoTest.svuotaDB());
	}
}
