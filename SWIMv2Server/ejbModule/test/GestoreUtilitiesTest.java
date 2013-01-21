package test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.naming.Context;
import org.junit.BeforeClass;
import org.junit.Test;
import entity.Abilita;
import entity.Aiuto;
import entity.ReputazioneAbilita;
import entity.User;
import session.GestoreAbilitaRemote;
import session.GestoreAiutoRemote;
import session.GestoreFeedbackRemote;
import session.GestoreUserRemote;
import session.GestoreUtilitiesRemote;

public class GestoreUtilitiesTest {

	/*
	 * Definisco il contesto per poter agganciare il test alle session
	 */
	static private Context jndiContext;  
	static private GestoreFeedbackRemote gestoreFeedbackRemote;
	static private GestoreAiutoRemote gestoreAiutoRemote;
	static private GestoreUserRemote gestoreUserRemote;
	static private GestoreAbilitaRemote gestoreAbilitaRemote;
	static private GestoreUtilitiesRemote gestoreUtilitiesRemote;

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
		
		Object refUtilities = jndiContext.lookup("GestoreUtilitiesJNDI");
		gestoreUtilitiesRemote = (GestoreUtilitiesRemote) refUtilities;
	}



	/**
	 * Verifica il funzionamento del metodo getReputazioneAbilita(String nickname, long idAbilita) definito nella classe
	 * GestoreUtilities del package session
	 */
	@Test
	public void testgetReputazioneAbilita(){
		List<Abilita> listaAbilitaSistema;
		Set<Abilita> setAbilita = new HashSet<Abilita>();
		
		Calendar momentoRichiesta;
		Calendar momentoAccettazione;
		Calendar momentoRilascio;
		
		Aiuto aiutoDaAccettarePippo;
		Aiuto aiutoDaAccettareKikka;
		Aiuto aiutoRicevutoPippo;
		Aiuto aiutoRicevutoKikka;
		
		long idAiutoRicevuto;
		
		//aggiungo tre utenti al sistema
		gestoreUserRemote.registra("toto", "prova", "toto@mail.com", "salvatore", "rossi", "path/toto.png", "palermo", "maschio", 1967);
		gestoreUserRemote.registra("kikka", "mamma", "kikka@mail.com", "federica", "rossi", "path/kikka.png", "milano", "femmina", 1990);
		gestoreUserRemote.registra("pippo", "pwd", "pippo@mail.com", "filippo", "roi", "/image/pippo.png", "cagliari", "maschio", 1988);
		
		//aggiungo due abilita al sistema
		gestoreAbilitaRemote.crea("cameriere", "per serate di gale", "pathCameriere");
		gestoreAbilitaRemote.crea("bagnino", "per piscine olimpiche", "pathBagnino");
		//recupero l'id della prima abilità creata ("cameriere")
		long idAbilitaCameriere = gestoreAbilitaRemote.getAbilitaSistema().get(0).getId();
		long idAbilitaBagnino = gestoreAbilitaRemote.getAbilitaSistema().get(1).getId();
		
		//modifico l'insieme delle abilità dichiarate da "toto"
		listaAbilitaSistema = gestoreAbilitaRemote.getAbilitaSistema();
			for(Abilita abilita: listaAbilitaSistema) {
				setAbilita.add(abilita);
			}
		gestoreUserRemote.modificaAbilitaDichiarate("toto", setAbilita);

		//lo user "pippo" invia una richiesta di aiuto allo user "toto" per l'abilita "cameriere"
		momentoRichiesta = new GregorianCalendar();
		gestoreAiutoRemote.inviaRichiesta("pippo", "toto", idAbilitaCameriere, "urgentissimo", momentoRichiesta);
		//lo user "kikka" invia una richiesta di aiuto allo user "toto" per l'abilita "cameriere"
		momentoRichiesta = new GregorianCalendar();
		gestoreAiutoRemote.inviaRichiesta("kikka", "toto", idAbilitaCameriere, "bachetto", momentoRichiesta);
		
		//inserisco una sleep per far trascorrere qualche secondo
		try {
			Thread.sleep(1000);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		
		//recupero la richiesta di aiuto da accettare
		aiutoDaAccettarePippo = gestoreAiutoRemote.getRichiesteRicevute("toto").get(0);
		aiutoDaAccettareKikka = gestoreAiutoRemote.getRichiesteRicevute("toto").get(1);
		//lo user "toto" accetta le richieste di aiuto ricevute
		momentoAccettazione = new GregorianCalendar();
		gestoreAiutoRemote.accettaRichiesta(aiutoDaAccettarePippo.getId(), momentoAccettazione);
		momentoAccettazione = new GregorianCalendar();
		gestoreAiutoRemote.accettaRichiesta(aiutoDaAccettareKikka.getId(), momentoAccettazione);
		
		//inserisco una sleep per far trascorrere qualche secondo
		try {
			Thread.sleep(1000);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		
		//lo user "pippo" rilascia il feedback sull'aiuto ricevuto
		momentoRilascio = new GregorianCalendar();
		aiutoRicevutoPippo = gestoreAiutoRemote.getAiutiRicevuti("pippo").get(0);
		idAiutoRicevuto = aiutoRicevutoPippo.getId();
		gestoreFeedbackRemote.rilascia(idAiutoRicevuto, 5, "servizio superbo", momentoRilascio);

		//lo user "kikka" rilascia il feedback sull'aiuto ricevuto
		momentoRilascio = new GregorianCalendar();
		aiutoRicevutoKikka = gestoreAiutoRemote.getAiutiRicevuti("kikka").get(0);
		idAiutoRicevuto = aiutoRicevutoKikka.getId();
		gestoreFeedbackRemote.rilascia(idAiutoRicevuto, 1, "hai fatto pena", momentoRilascio);
		
		//recupero la reputazione dello user "toto" in merito all'abilità di "cameriere"
		ReputazioneAbilita reputazioneCameriere = gestoreUtilitiesRemote.getReputazioneAbilita("toto", idAbilitaCameriere);
		//recupero la reputazione dello user "toto" in merito all'abilità di "bagnino"
		ReputazioneAbilita reputazioneBagnino = gestoreUtilitiesRemote.getReputazioneAbilita("toto", idAbilitaBagnino);

		//Test: lo user "toto" ha 2 feedback in merito all'abilità di cameriere
		assertEquals(2, reputazioneCameriere.getNumeroFeedbackRicevuti());
		
		//Test: lo user "toto" ha 0 feedback in merito all'abilità di bagnino
		assertEquals(0, reputazioneBagnino.getNumeroFeedbackRicevuti());
		
		//Test: lo user "toto" ha una media valutazioni in merito all'abilità di cameriere pari a 3
		assertEquals(3, reputazioneCameriere.getMediaValutazioniFeedback());
		
		//Test: lo user "toto" ha una media valutazioni in merito all'abilità di bagnino pari a 0
		assertEquals(0, reputazioneBagnino.getMediaValutazioniFeedback());

		//elimino gli user
		for(User user: gestoreUserRemote.getUserSistema()){
			gestoreUserRemote.elimina(user.getNickname());
		}
		//elimino le abilità
		for(Abilita abilita: gestoreAbilitaRemote.getAbilitaSistema()){
			gestoreAbilitaRemote.elimina(abilita.getId());
		}
	}
	
	@Test
	public void test(){
		File tempDir = new File(System.getProperty("java.io.tmpdir")); // to get temp directory path
		String temp1;
		try {
			temp1 = tempDir.getCanonicalPath();
			System.out.println(temp1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
