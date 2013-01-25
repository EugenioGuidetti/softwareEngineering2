package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import org.junit.BeforeClass;
import org.junit.Test;

import entity.Abilita;
import entity.Amicizia;
import entity.User;
import session.GestoreAbilitaRemote;
import session.GestoreAmiciziaRemote;
import session.GestoreUserRemote;

/**
 * Classe di test che verifica i singoli metodi definiti nella classe GestoreUser del package session
 * 
 * @author Eugenio Guidetti - Claudio Fratto
 *
 */
public class GestoreUserTest {

	/*
	 * Definisco il contesto per poter agganciare il test alle session
	 */
	static private Context jndiContext;  
	static private GestoreAbilitaRemote gestoreAbilitaRemote;
	static private GestoreUserRemote gestoreUserRemote;
	static private GestoreAmiciziaRemote gestoreAmiciziaRemote;

	/*
	 * Definisco il contesto, agganciando i gestori remoti attraverso le lookup
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		jndiContext = SupportoTest.getInitialContext();

		Object refAbilita = jndiContext.lookup("GestoreAbilitaJNDI");
		gestoreAbilitaRemote = (GestoreAbilitaRemote) refAbilita;

		Object refUser = jndiContext.lookup("GestoreUserJNDI");
		gestoreUserRemote = (GestoreUserRemote) refUser;

		Object refAmicizia = jndiContext.lookup("GestoreAmiciziaJNDI");
		gestoreAmiciziaRemote = (GestoreAmiciziaRemote) refAmicizia;
	}


	/**
	 * Verifica il funzionamento del metodo registra(String nickname, String password, String email, String nome, 
	 * 										String cognome,	String avatarPath, String citta, String sesso, int annoNascita);
	 * definito nella classe GestoreUser del package session
	 */
	@Test
	public void testRegistraElimina(){		

		//Test: verifico che il database sia vuoto prima di iniziare il test
		assertEquals(true, SupportoTest.verificaDatabaseVuoto());
		
		//Test: lo user "toto" si registra al sistema (nickname disponibile)
		assertEquals(true, gestoreUserRemote.registra("toto", "prova", "toto@mail.com", "salvatore", "rossi", "path/toto.png", "palermo", "maschio", 1967));

		//Test: lo user "toto" si elimina dal sistema e quindi non esiste pi� alcuno user con quel nickname
		assertEquals(true, gestoreUserRemote.elimina("toto"));
		assertEquals(null, gestoreUserRemote.getUser("toto"));
		
		//Test: svuoto il database e verifico che non vi rimanga pi� alcuna informazione
		assertEquals(true, SupportoTest.svuotaDB());
	}

	/**
	 * Verifica il funzionamento del metodo modificaPassword(String nickname, String password) definito 
	 * nella classe GestoreUser del package session
	 */
	@Test
	public void testModificaPassword(){
		
		//Test: verifico che il database sia vuoto prima di iniziare il test
		assertEquals(true, SupportoTest.verificaDatabaseVuoto());
		
		gestoreUserRemote.registra("pippo", "pwd", "pippo@mail.com", "filippo", "roi", "/image/pippo.png", "cagliari", "maschio", 1988);

		User user = gestoreUserRemote.getUser("pippo");

		//Test: modifico la password da "pwd" a "password" dello user associato al nickname "pippo"
		assertEquals(true, user.getPassword().equals("pwd"));
		assertEquals(true, gestoreUserRemote.modificaPassword("pippo", "password"));

		user = gestoreUserRemote.getUser("pippo");
		assertEquals(true, user.getPassword().equals("password"));

		//Test: svuoto il database e verifico che non vi rimanga pi� alcuna informazione
		assertEquals(true, SupportoTest.svuotaDB());
	}


	/**
	 * Verifica il funzionamento del metodo modificaEmail(String nickname, String email) definito 
	 * nella classe GestoreUser del package session
	 */
	@Test
	public void testModificaEmail(){
		
		//Test: verifico che il database sia vuoto prima di iniziare il test
		assertEquals(true, SupportoTest.verificaDatabaseVuoto());
		
		gestoreUserRemote.registra("pippo", "pwd", "pippo@mail.com", "filippo", "roi", "/image/pippo.png", "cagliari", "maschio", 1988);

		User user = gestoreUserRemote.getUser("pippo");

		//Test: modifico l'indirizzo email da "pippo@mail.com" a "pippo.pippo@mail.com" dello user associato al nickname "pippo"
		assertEquals(true, user.getEmail().equals("pippo@mail.com"));
		assertEquals(true, gestoreUserRemote.modificaEmail("pippo", "pippo.pippo@mail.com"));

		user = gestoreUserRemote.getUser("pippo");
		assertEquals(true, user.getEmail().equals("pippo.pippo@mail.com"));

		//Test: svuoto il database e verifico che non vi rimanga pi� alcuna informazione
		assertEquals(true, SupportoTest.svuotaDB());
	}



	/**
	 * Verifica il funzionamento del metodo modificaNome(String nickname, String nome) definito 
	 * nella classe GestoreUser del package session
	 */
	@Test
	public void testModificaNome(){
		
		//Test: verifico che il database sia vuoto prima di iniziare il test
		assertEquals(true, SupportoTest.verificaDatabaseVuoto());
		
		gestoreUserRemote.registra("pippo", "pwd", "pippo@mail.com", "filippo", "roi", "/image/pippo.png", "cagliari", "maschio", 1988);

		User user = gestoreUserRemote.getUser("pippo");

		//Test: modifico il nome da "filippo" a "antonio" dello user associato al nickname "pippo"
		assertEquals(true, user.getNome().equals("filippo"));
		assertEquals(true, gestoreUserRemote.modificaNome("pippo", "antonio"));

		user = gestoreUserRemote.getUser("pippo");
		assertEquals(true, user.getNome().equals("antonio"));

		//Test: svuoto il database e verifico che non vi rimanga pi� alcuna informazione
		assertEquals(true, SupportoTest.svuotaDB());
	}


	/**
	 * Verifica il funzionamento del metodo modificaCognome(String nickname, String cognome) definito 
	 * nella classe GestoreUser del package session
	 */
	@Test
	public void testModificaCognome(){
		
		//Test: verifico che il database sia vuoto prima di iniziare il test
		assertEquals(true, SupportoTest.verificaDatabaseVuoto());
		
		gestoreUserRemote.registra("pippo", "pwd", "pippo@mail.com", "filippo", "roi", "/image/pippo.png", "cagliari", "maschio", 1988);

		User user = gestoreUserRemote.getUser("pippo");

		//Test: modifico il cognome da "roi" a "la russa" dello user associato al nickname "pippo"
		assertEquals(true, user.getCognome().equals("roi"));
		assertEquals(true, gestoreUserRemote.modificaCognome("pippo", "la russa"));

		user = gestoreUserRemote.getUser("pippo");
		assertEquals(true, user.getCognome().equals("la russa"));

		//Test: svuoto il database e verifico che non vi rimanga pi� alcuna informazione
		assertEquals(true, SupportoTest.svuotaDB());
	}


	/**
	 * Verifica il funzionamento del metodo modificaAvatar(String nickname, String avatarPath) definito nella classe
	 * GestoreUser del package session
	 */
	@Test
	public void testModificaPathAvatar(){
		
		//Test: verifico che il database sia vuoto prima di iniziare il test
		assertEquals(true, SupportoTest.verificaDatabaseVuoto());
		
		gestoreUserRemote.registra("pippo", "pwd", "pippo@mail.com", "filippo", "roi", "/image/pippo.png", "cagliari", "maschio", 1988);

		User user = gestoreUserRemote.getUser("pippo");


		//Test: modifico il path dell'avatar da "/image/pippo.png" a "/image/pippo2.png" dello user associato al nickname "pippo"
		assertEquals(true, user.getAvatarPath().equals("/image/pippo.png"));
		assertEquals(true, gestoreUserRemote.modificaAvatar("pippo", "/image/pippo2.png"));

		user = gestoreUserRemote.getUser("pippo");
		assertEquals(true, user.getAvatarPath().equals("/image/pippo2.png"));

		//Test: svuoto il database e verifico che non vi rimanga pi� alcuna informazione
		assertEquals(true, SupportoTest.svuotaDB());
	}

	/**
	 * Verifica il funzionamento del metodo modificaCitta(String nickname, String citta) definito nella classe
	 * GestoreUser del package session
	 */
	@Test
	public void testModificaCitta(){
		
		//Test: verifico che il database sia vuoto prima di iniziare il test
		assertEquals(true, SupportoTest.verificaDatabaseVuoto());
		
		gestoreUserRemote.registra("pippo", "pwd", "pippo@mail.com", "filippo", "roi", "/image/pippo.png", "cagliari", "maschio", 1988);

		User user = gestoreUserRemote.getUser("pippo");

		//Test: modifico la citt� da "cagliari" a "palermo" dello user associato al nickname "pippo"
		assertEquals(true, user.getCitta().equals("cagliari"));
		assertEquals(true, gestoreUserRemote.modificaCitta("pippo", "palermo"));

		user = gestoreUserRemote.getUser("pippo");
		assertEquals(true, user.getCitta().equals("palermo"));

		//Test: svuoto il database e verifico che non vi rimanga pi� alcuna informazione
		assertEquals(true, SupportoTest.svuotaDB());
	}

	/**
	 * Verifica il funzionamento del metodo modificaAnnoNascita(String nickname, int annoNascita) definito nella classe
	 * GestoreUser del package session
	 */
	@Test
	public void testModificaAnnoNascita(){
		
		//Test: verifico che il database sia vuoto prima di iniziare il test
		assertEquals(true, SupportoTest.verificaDatabaseVuoto());
		
		gestoreUserRemote.registra("pippo", "pwd", "pippo@mail.com", "filippo", "roi", "/image/pippo.png", "cagliari", "maschio", 1988);

		User user = gestoreUserRemote.getUser("pippo");

		//Test: modifico l'anno di nascita da "1988" a "1970" dello user associato al nickname "pippo"
		assertEquals(true, user.getAnnoNascita() == 1988);
		assertEquals(true, gestoreUserRemote.modificaAnnoNascita("pippo", 1970));

		user = gestoreUserRemote.getUser("pippo");
		assertEquals(true, user.getAnnoNascita() == 1970);

		//Test: svuoto il database e verifico che non vi rimanga pi� alcuna informazione
		assertEquals(true, SupportoTest.svuotaDB());
	}


	/**
	 * Verifica il funzionamento del metodo modificaSesso(String nickname, String sesso) definito nella classe GestoreUser
	 * del package session
	 */
	@Test
	public void testModificaSesso(){
		
		//Test: verifico che il database sia vuoto prima di iniziare il test
		assertEquals(true, SupportoTest.verificaDatabaseVuoto());
		
		gestoreUserRemote.registra("pippo", "pwd", "pippo@mail.com", "filippo", "roi", "/image/pippo.png", "cagliari", "maschio", 1988);

		User user = gestoreUserRemote.getUser("pippo");

		//Test: modifico il sesso da "maschio" a "femmina"dello user associato al nickname "pippo"
		assertEquals(true, user.getSesso().equals("maschio"));
		assertEquals(true, gestoreUserRemote.modificaSesso("pippo", "femmina"));

		user = gestoreUserRemote.getUser("pippo");
		assertEquals(true, user.getSesso().equals("femmina"));

		//Test: svuoto il database e verifico che non vi rimanga pi� alcuna informazione
		assertEquals(true, SupportoTest.svuotaDB());
	}


	/**
	 * Verifica il funzionamento del metodo  modificaAbilitaDichiarate(String nickname, Set<Abilita> abilitaDichiarate) definito
	 * nella classe GestoreUser del package session
	 */
	@Test
	public void testModificaAbilitaDichiarate(){

		List<Abilita> listaAbilitaSistema;
		Set<Abilita> abilitaUser;
		Set<Abilita> setAbilita = new HashSet<Abilita>();

		//Test: verifico che il database sia vuoto prima di iniziare il test
		assertEquals(true, SupportoTest.verificaDatabaseVuoto());
		
		//Aggiungo 3 abilit� nel sistema (inizialmente vuoto)
		gestoreAbilitaRemote.crea("gigol�", "per serate da favola", "gigol�.png");
		gestoreAbilitaRemote.crea("bagnino", "piscina", "bagnino.png");
		gestoreAbilitaRemote.crea("meccanio", "pulizia carburatori", "meccanico.png");

		//Creo uno user
		gestoreUserRemote.registra("vercingetorige", "pwd", "vercingetorige@mail.com", "filippo", "rossi", "vercingetorige.png", "milano", "maschio", 1987);

		//Test: verifico che l'insieme delle abilit� dichiarate dallo user � vuoto
		assertEquals(true, gestoreUserRemote.getUser("vercingetorige").getAbilitaDichiarate().isEmpty());

		// Test: modifico l'insieme delle abilit� dello user associato al nickname "vercingetorige".
		listaAbilitaSistema = gestoreAbilitaRemote.getAbilitaSistema();
		for(Abilita abilita: listaAbilitaSistema) {
			setAbilita.add(abilita);
		}
		assertEquals(true, gestoreUserRemote.modificaAbilitaDichiarate("vercingetorige", setAbilita));

		//recupero le abilit� dichiarate dallo user
		abilitaUser = gestoreUserRemote.getUser("vercingetorige").getAbilitaDichiarate();

		//Test: dopo la modifica l'insieme delle abilit� dichiarate dalla user coincider� con quello delle abilit� disponibili nel sistema
		assertEquals(3, abilitaUser.size());

		//Test: svuoto il database e verifico che non vi rimanga pi� alcuna informazione
		assertEquals(true, SupportoTest.svuotaDB());	
		}

	/**
	 * Verifica il funzionamento del metodo getUserSistema() definito nella classe GestoreUser del package session
	 */
	@Test
	public void testUserSistema(){
		
		//Test: verifico che il database sia vuoto prima di iniziare il test
		assertEquals(true, SupportoTest.verificaDatabaseVuoto());
		
		//Test: all'inizio nel sistema non sono presenti user
		assertEquals(0, gestoreUserRemote.getUserSistema().size());

		//aggiungo due utenti al sistema
		gestoreUserRemote.registra("toto", "prova", "toto@mail.com", "salvatore", "rossi", "path/toto.png", "palermo", "maschio", 1967);
		gestoreUserRemote.registra("pippo", "pwd", "pippo@mail.com", "filippo", "roi", "/image/pippo.png", "cagliari", "maschio", 1988);

		//Test: nel sistema ora sono presenti 2 user
		assertEquals(2, gestoreUserRemote.getUserSistema().size());

		//Test: svuoto il database e verifico che non vi rimanga pi� alcuna informazione
		assertEquals(true, SupportoTest.svuotaDB());
	}

	/**
	 * Verifica il funzionamento del metodo ricercaPerAbilita(long idAbilita) definito nella classe GestoreUser del
	 * package session
	 */
	@Test
	public void testRicercaPerAbilita(){

		//Test: verifico che il database sia vuoto prima di iniziare il test
		assertEquals(true, SupportoTest.verificaDatabaseVuoto());
		
		//aggiungo tre utenti al sistema
		gestoreUserRemote.registra("toto", "prova", "toto@mail.com", "salvatore", "rossi", "path/toto.png", "palermo", "maschio", 1967);
		gestoreUserRemote.registra("kikka", "mamma", "kikka@mail.com", "federica", "rossi", "path/kikka.png", "milano", "femmina", 1990);
		gestoreUserRemote.registra("pippo", "pwd", "pippo@mail.com", "filippo", "roi", "/image/pippo.png", "cagliari", "maschio", 1988);

		//aggiungo un'abilit� al sistema
		gestoreAbilitaRemote.crea("cameriere", "per serate di gale", "path");

		//recupero l'abilit� creata
		Abilita abilita = gestoreAbilitaRemote.getAbilitaSistema().get(0);

		//modifico l'insieme delle abilit� dichiarate degli user "pippo" e "kikka"
		Set<Abilita> abilitaDichiarate = new HashSet<Abilita>();
		abilitaDichiarate.add(abilita);
		gestoreUserRemote.modificaAbilitaDichiarate("pippo", abilitaDichiarate);
		gestoreUserRemote.modificaAbilitaDichiarate("kikka", abilitaDichiarate);

		//Test: nel sistema esistono 2 user che hanno tra le abilit� dichiarate "abilita"; 
		assertEquals(2, gestoreUserRemote.ricercaPerAbilita(abilita.getId()).size());

		//recupero i risultati della ricerca estraendone i nickname
		List<User> risultatoRicerca = gestoreUserRemote.ricercaPerAbilita(abilita.getId());
		List<String> nicknameRicerca = new ArrayList<String>();
		//recupero i nickname degli user che appartengono al risultato della ricerca
		for(User user: risultatoRicerca){
			nicknameRicerca.add(user.getNickname());
		}
		//Test: verifico se tra i risultati della ricerca ci sono solo gli user che hanno dichiarato "abilita"
		assertEquals(true, nicknameRicerca.contains("pippo"));
		assertEquals(true, nicknameRicerca.contains("kikka"));
		assertEquals(false, nicknameRicerca.contains("toto"));

		//Test: svuoto il database e verifico che non vi rimanga pi� alcuna informazione
		assertEquals(true, SupportoTest.svuotaDB());	
		}

	/**
	 * Verifica il funzionamento del metodo ricercaPerNome(String nome) definito nella classe GestoreUser del package session
	 */
	@Test
	public void testRicercaPerNome(){
		
		//Test: verifico che il database sia vuoto prima di iniziare il test
		assertEquals(true, SupportoTest.verificaDatabaseVuoto());
		
		//aggiungo tre utenti al sistema
		gestoreUserRemote.registra("toto", "prova", "toto@mail.com", "antonio", "rossi", "path/toto.png", "palermo", "maschio", 1967);
		gestoreUserRemote.registra("kikka", "mamma", "kikka@mail.com", "federica", "rossi", "path/kikka.png", "milano", "femmina", 1990);
		gestoreUserRemote.registra("tonino", "pwd", "tonino@mail.com", "antonio", "roi", "/image/tonino.png", "cagliari", "maschio", 1988);

		//Test: nel sistema esistono 2 user che si chiamano "antonio" 
		assertEquals(2, gestoreUserRemote.ricercaPerNome("antonio").size());

		//recupero i risultati della ricerca estraendone i nickname
		List<User> risultatoRicerca = gestoreUserRemote.ricercaPerNome("antonio");
		List<String> nicknameRicerca = new ArrayList<String>();
		//recupero i nickname degli user che appartengono al risultato della ricerca
		for(User user: risultatoRicerca){
			nicknameRicerca.add(user.getNickname());
		}
		//Test: verifico se tra i risultati della ricerca ci sono solo gli user che hanno come nome "antonio"
		assertEquals(true, nicknameRicerca.contains("tonino"));
		assertEquals(false, nicknameRicerca.contains("kikka"));
		assertEquals(true, nicknameRicerca.contains("toto"));

		//Test: svuoto il database e verifico che non vi rimanga pi� alcuna informazione
		assertEquals(true, SupportoTest.svuotaDB());
	}

	/**
	 * Verifica il funzionamento del metodo ricercaPerCognome(String cognome) definito nella classe GestoreUser del package session
	 */
	@Test
	public void testRicercaPerCognome(){
		
		//Test: verifico che il database sia vuoto prima di iniziare il test
		assertEquals(true, SupportoTest.verificaDatabaseVuoto());
		
		//aggiungo tre utenti al sistema
		gestoreUserRemote.registra("toto", "prova", "toto@mail.com", "antonio", "rossi", "path/toto.png", "palermo", "maschio", 1967);
		gestoreUserRemote.registra("kikka", "mamma", "kikka@mail.com", "federica", "rossi", "path/kikka.png", "milano", "femmina", 1990);
		gestoreUserRemote.registra("pippo", "pwd", "pippo@mail.com", "filippo", "roi", "/image/pippo.png", "cagliari", "maschio", 1988);

		//Test: nel sistema esistono 2 user che hanno come cognome "rossi" 
		assertEquals(2, gestoreUserRemote.ricercaPerCognome("rossi").size());

		//recupero i risultati della ricerca estraendone i nickname
		List<User> risultatoRicerca = gestoreUserRemote.ricercaPerCognome("rossi");
		List<String> nicknameRicerca = new ArrayList<String>();
		//recupero i nickname degli user che appartengono al risultato della ricerca
		for(User user: risultatoRicerca){
			nicknameRicerca.add(user.getNickname());
		}
		//Test: verifico se tra i risultati della ricerca ci sono solo gli user che hanno il cognome uguale a "rossi"
		assertEquals(false, nicknameRicerca.contains("pippo"));
		assertEquals(true, nicknameRicerca.contains("kikka"));
		assertEquals(true, nicknameRicerca.contains("toto"));

		//Test: svuoto il database e verifico che non vi rimanga pi� alcuna informazione
		assertEquals(true, SupportoTest.svuotaDB());
	}

	/**
	 * Verifica il funzionamento del metodo ricercaPerNomeCognome(String nome, String cognome) definito nella 
	 * classe GestoreUser del package session
	 */
	@Test
	public void testRicercaPerNomeCognome(){
		
		//Test: verifico che il database sia vuoto prima di iniziare il test
		assertEquals(true, SupportoTest.verificaDatabaseVuoto());
		
		//aggiungo tre utenti al sistema
		gestoreUserRemote.registra("toto", "prova", "toto@mail.com", "antonio", "rossi", "path/toto.png", "palermo", "maschio", 1967);
		gestoreUserRemote.registra("kikka", "mamma", "kikka@mail.com", "federica", "rossi", "path/kikka.png", "milano", "femmina", 1990);
		gestoreUserRemote.registra("pippo", "pwd", "pippo@mail.com", "filippo", "roi", "/image/pippo.png", "cagliari", "maschio", 1988);

		//Test: nel sistema esiste 1 user che ha per nome "antonio" e per cognome "rossi" 
		assertEquals(1, gestoreUserRemote.ricercaPerNomeCognome("antonio", "rossi").size());

		//recupero i risultati della ricerca estraendone i nickname
		List<User> risultatoRicerca = gestoreUserRemote.ricercaPerNomeCognome("antonio", "rossi");
		List<String> nicknameRicerca = new ArrayList<String>();
		//recupero i nickname degli user che appartengono al risultato della ricerca
		for(User user: risultatoRicerca){
			nicknameRicerca.add(user.getNickname());
		}
		//Test: verifico se tra i risultati della ricerca ci sono solo gli user che si chiamano "anotio" "rossi"
		assertEquals(false, nicknameRicerca.contains("pippo"));
		assertEquals(false, nicknameRicerca.contains("kikka"));
		assertEquals(true, nicknameRicerca.contains("toto"));

		//Test: svuoto il database e verifico che non vi rimanga pi� alcuna informazione
		assertEquals(true, SupportoTest.svuotaDB());
	}

	/**
	 * Verifica il funzionamento del metodo ricercaAmiciPerAbilita(String nickname, long idAbilita) definito nella classe GestoreUser del
	 * package session
	 */
	@Test
	public void testRicercaAmiciPerAbilita(){

		//Test: verifico che il database sia vuoto prima di iniziare il test
		assertEquals(true, SupportoTest.verificaDatabaseVuoto());
		
		//aggiungo tre utenti al sistema
		gestoreUserRemote.registra("toto", "prova", "toto@mail.com", "salvatore", "rossi", "path/toto.png", "palermo", "maschio", 1967);
		gestoreUserRemote.registra("kikka", "mamma", "kikka@mail.com", "federica", "rossi", "path/kikka.png", "milano", "femmina", 1990);
		gestoreUserRemote.registra("pippo", "pwd", "pippo@mail.com", "filippo", "roi", "/image/pippo.png", "cagliari", "maschio", 1988);

		//aggiungo un'abilit� al sistema
		gestoreAbilitaRemote.crea("cameriere", "per serate di gale", "path");

		//recupero l'abilit� creata
		Abilita abilita = gestoreAbilitaRemote.getAbilitaSistema().get(0);

		//modifico l'insieme delle abilit� dichiarate degli user "pippo" e "kikka"
		Set<Abilita> abilitaDichiarate = new HashSet<Abilita>();
		abilitaDichiarate.add(abilita);
		gestoreUserRemote.modificaAbilitaDichiarate("pippo", abilitaDichiarate);
		gestoreUserRemote.modificaAbilitaDichiarate("kikka", abilitaDichiarate);

		//instauro un rapporto di amicizia tra "kikka" (richiedente) e "toto" (destinatario)
		Calendar momentoRichiesta = new GregorianCalendar();
		gestoreAmiciziaRemote.inviaRichiesta("kikka", "toto", momentoRichiesta);

		try {
			Thread.sleep(2000);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}

		Amicizia amiciziaDaAccettare = gestoreAmiciziaRemote.getRichiesteRicevute("toto").get(0);

		Calendar momentoAccettazione = new GregorianCalendar();
		gestoreAmiciziaRemote.accettaRichiesta(amiciziaDaAccettare.getId(), momentoAccettazione);


		//Test: nel sistema tra gli amicici di kikka non esiste nessuno che ha tra le abilit� dichiarate "abilita" 
		assertEquals(0, gestoreUserRemote.ricercaAmiciPerAbilita("kikka", abilita.getId()).size());

		//recupero i risultati della ricerca estraendone i nickname
		List<User> risultatoRicerca = gestoreUserRemote.ricercaAmiciPerAbilita("kikka", abilita.getId());
		List<String> nicknameRicerca = new ArrayList<String>();
		//recupero i nickname degli user che appartengono al risultato della ricerca
		for(User user: risultatoRicerca){
			nicknameRicerca.add(user.getNickname());
		}
		//Test: verifico se tra i risultati della ricerca ci sono solo gli user che hanno dichiarato "abilita"
		assertEquals(false, nicknameRicerca.contains("pippo"));
		assertEquals(false, nicknameRicerca.contains("toto"));

		//Test: svuoto il database e verifico che non vi rimanga pi� alcuna informazione
		assertEquals(true, SupportoTest.svuotaDB());
	}

	/**
	 * Verifica il funzionamento del metodo ricercaAmiciPerNome(String nickname, String nome) definito nella classe GestoreUser del package session
	 */
	@Test
	public void testRicercaAmiciPerNome(){
		Amicizia amiciziaDaAccettare;
		Calendar momentoAccettazione;
		List<User> risultatoRicerca;
		List<String> nicknameRicerca;

		//Test: verifico che il database sia vuoto prima di iniziare il test
		assertEquals(true, SupportoTest.verificaDatabaseVuoto());
		
		//aggiungo tre utenti al sistema
		gestoreUserRemote.registra("toto", "prova", "toto@mail.com", "antonio", "rossi", "path/toto.png", "palermo", "maschio", 1967);
		gestoreUserRemote.registra("kikka", "mamma", "kikka@mail.com", "federica", "rossi", "path/kikka.png", "milano", "femmina", 1990);
		gestoreUserRemote.registra("tonino", "pwd", "tonino@mail.com", "antonio", "roi", "/image/tonino.png", "cagliari", "maschio", 1988);

		/*
		 * instauro un rapporto di amicizia tra "kikka" (richiedente) e "toto" (destinatario)
		 * instauro un rapporto di amicizia tra "toto" (richiedente) e "tonino" (destinatario)
		 */
		Calendar momentoRichiesta = new GregorianCalendar();
		gestoreAmiciziaRemote.inviaRichiesta("kikka", "toto", momentoRichiesta);
		gestoreAmiciziaRemote.inviaRichiesta("toto", "kikka", momentoRichiesta);

		try {
			Thread.sleep(2000);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}

		amiciziaDaAccettare = gestoreAmiciziaRemote.getRichiesteRicevute("toto").get(0);
		momentoAccettazione = new GregorianCalendar();
		gestoreAmiciziaRemote.accettaRichiesta(amiciziaDaAccettare.getId(), momentoAccettazione);

		amiciziaDaAccettare = gestoreAmiciziaRemote.getRichiesteRicevute("kikka").get(0);
		momentoAccettazione = new GregorianCalendar();
		gestoreAmiciziaRemote.accettaRichiesta(amiciziaDaAccettare.getId(), momentoAccettazione);

		//Test: nel sistema tra gli amici di "kikka" esiste un solo user che si chiama "antonio"
		assertEquals(1, gestoreUserRemote.ricercaAmiciPerNome("kikka", "antonio").size());

		//recupero i risultati della ricerca effettuata da "kikka" estraendone i nickname
		risultatoRicerca = gestoreUserRemote.ricercaAmiciPerNome("kikka", "antonio");
		nicknameRicerca = new ArrayList<String>();
		//recupero i nickname degli user che appartengono al risultato della ricerca effettuata da "kikka"
		for(User user: risultatoRicerca){
			nicknameRicerca.add(user.getNickname());
		}

		//Test: verifico se tra i risultati della ricerca effettuata da "kikka" ci sono solo gli user che hanno come nome "antonio"
		assertEquals(false, nicknameRicerca.contains("tonino"));
		assertEquals(true, nicknameRicerca.contains("toto"));


		//Test: nel sistema tra gli amici di "toto" non esiste nessuno user che si chima "antonio"
		assertEquals(0, gestoreUserRemote.ricercaAmiciPerNome("toto", "antonio").size());

		//recupero i risultati della ricerca effettuata da "toto" estraendone i nickname
		risultatoRicerca = gestoreUserRemote.ricercaAmiciPerNome("toto", "antonio");
		nicknameRicerca = new ArrayList<String>();
		//recupero i nickname degli user che appartengono al risultato della ricerca effettuata da "toto"
		for(User user: risultatoRicerca){
			nicknameRicerca.add(user.getNickname());
		}
		//Test: verifico se tra i risultati della ricerca effettuata da "toto" ci sono solo gli user che hanno come nome "antonio"
		assertEquals(false, nicknameRicerca.contains("kikka"));
		assertEquals(false, nicknameRicerca.contains("tonino"));

		//Test: svuoto il database e verifico che non vi rimanga pi� alcuna informazione
		assertEquals(true, SupportoTest.svuotaDB());
	}

	/**
	 * Verifica il funzionamento del metodo ricercaAmiciPerCognome(String nickname, String cognome) definito nella classe GestoreUser del package session
	 */
	@Test
	public void testRicercaAmiciPerCognome(){
		Amicizia amiciziaDaAccettare;
		Calendar momentoAccettazione;
		List<User> risultatoRicerca;
		List<String> nicknameRicerca;

		//Test: verifico che il database sia vuoto prima di iniziare il test
		assertEquals(true, SupportoTest.verificaDatabaseVuoto());
		
		//aggiungo tre utenti al sistema
		gestoreUserRemote.registra("toto", "prova", "toto@mail.com", "antonio", "rossi", "path/toto.png", "palermo", "maschio", 1967);
		gestoreUserRemote.registra("kikka", "mamma", "kikka@mail.com", "federica", "rossi", "path/kikka.png", "milano", "femmina", 1990);
		gestoreUserRemote.registra("pippo", "pwd", "pippo@mail.com", "filippo", "roi", "/image/pippo.png", "cagliari", "maschio", 1988);

		/*
		 * instauro un rapporto di amicizia tra "kikka" (richiedente) e "toto" (destinatario)
		 * instauro un rapporto di amicizia tra "toto" (richiedente) e "pippo" (destinatario)
		 */
		Calendar momentoRichiesta = new GregorianCalendar();
		gestoreAmiciziaRemote.inviaRichiesta("kikka", "toto", momentoRichiesta);
		gestoreAmiciziaRemote.inviaRichiesta("toto", "pippo", momentoRichiesta);

		try {
			Thread.sleep(2000);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}

		amiciziaDaAccettare = gestoreAmiciziaRemote.getRichiesteRicevute("toto").get(0);
		momentoAccettazione = new GregorianCalendar();
		gestoreAmiciziaRemote.accettaRichiesta(amiciziaDaAccettare.getId(), momentoAccettazione);

		amiciziaDaAccettare = gestoreAmiciziaRemote.getRichiesteRicevute("pippo").get(0);
		momentoAccettazione = new GregorianCalendar();
		gestoreAmiciziaRemote.accettaRichiesta(amiciziaDaAccettare.getId(), momentoAccettazione);

		//Test: nel sistema tra gli amici di "kikka" esiste un solo user che si ha come cognome "rossi"
		assertEquals(1, gestoreUserRemote.ricercaAmiciPerCognome("kikka", "rossi").size());

		//recupero i risultati della ricerca effettuata da "kikka" estraendone i nickname
		risultatoRicerca = gestoreUserRemote.ricercaAmiciPerCognome("kikka", "rossi");
		nicknameRicerca = new ArrayList<String>();
		//recupero i nickname degli user che appartengono al risultato della ricerca effettuata da "kikka"
		for(User user: risultatoRicerca){
			nicknameRicerca.add(user.getNickname());
		}

		//Test: verifico se tra i risultati della ricerca effettuata da "kikka" ci sono solo gli user che hanno come cognome "rossi"
		assertEquals(false, nicknameRicerca.contains("pippo"));
		assertEquals(true, nicknameRicerca.contains("toto"));


		//Test: nel sistema tra gli amici di "pippo" non esiste nessuno user che ha come cognome "rossi"
		assertEquals(0, gestoreUserRemote.ricercaAmiciPerCognome("pippo", "rossi").size());

		//recupero i risultati della ricerca effettuata da "tonino" estraendone i nickname
		risultatoRicerca = gestoreUserRemote.ricercaAmiciPerCognome("pippo", "rossi");
		nicknameRicerca = new ArrayList<String>();
		//recupero i nickname degli user che appartengono al risultato della ricerca effettuata da "pippo"
		for(User user: risultatoRicerca){
			nicknameRicerca.add(user.getNickname());
		}
		//Test: verifico se tra i risultati della ricerca effettuata da pippo ci sono solo gli user che hanno come cognome "rossi"
		assertEquals(false, nicknameRicerca.contains("kikka"));
		assertEquals(false, nicknameRicerca.contains("toto"));

		//Test: svuoto il database e verifico che non vi rimanga pi� alcuna informazione
		assertEquals(true, SupportoTest.svuotaDB());	
	}

	/**
	 * Verifica il funzionamento del metodo ricercaAmiciPerNomeCognome(String nickname, String nome, String cognome) definito nella 
	 * classe GestoreUser del package session
	 */
	@Test
	public void testRicercaAmiciPerNomeCognome(){
		Amicizia amiciziaDaAccettare;
		Calendar momentoAccettazione;
		List<User> risultatoRicerca;
		List<String> nicknameRicerca;

		//Test: verifico che il database sia vuoto prima di iniziare il test
		assertEquals(true, SupportoTest.verificaDatabaseVuoto());
		
		//aggiungo tre utenti al sistema
		gestoreUserRemote.registra("toto", "prova", "toto@mail.com", "antonio", "rossi", "path/toto.png", "palermo", "maschio", 1967);
		gestoreUserRemote.registra("kikka", "mamma", "kikka@mail.com", "federica", "rossi", "path/kikka.png", "milano", "femmina", 1990);
		gestoreUserRemote.registra("pippo", "pwd", "pippo@mail.com", "filippo", "roi", "/image/pippo.png", "cagliari", "maschio", 1988);

		/*
		 * instauro un rapporto di amicizia tra "kikka" (richiedente) e "toto" (destinatario)
		 * instauro un rapporto di amicizia tra "kikka" (richiedente) e "pippo" (destinatario)
		 */
		Calendar momentoRichiesta = new GregorianCalendar();
		gestoreAmiciziaRemote.inviaRichiesta("kikka", "toto", momentoRichiesta);
		gestoreAmiciziaRemote.inviaRichiesta("kikka", "pippo", momentoRichiesta);

		try {
			Thread.sleep(2000);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}

		amiciziaDaAccettare = gestoreAmiciziaRemote.getRichiesteRicevute("toto").get(0);
		momentoAccettazione = new GregorianCalendar();
		gestoreAmiciziaRemote.accettaRichiesta(amiciziaDaAccettare.getId(), momentoAccettazione);

		amiciziaDaAccettare = gestoreAmiciziaRemote.getRichiesteRicevute("pippo").get(0);
		momentoAccettazione = new GregorianCalendar();
		gestoreAmiciziaRemote.accettaRichiesta(amiciziaDaAccettare.getId(), momentoAccettazione);

		//Test: nel sistema tra gli amici di "kikka" esiste un solo user che si chiama "antonio" "rossi"
		assertEquals(1, gestoreUserRemote.ricercaAmiciPerNomeCognome("kikka", "antonio", "rossi").size());

		//recupero i risultati della ricerca effettuata da "kikka" estraendone i nickname
		risultatoRicerca = gestoreUserRemote.ricercaAmiciPerNomeCognome("kikka", "antonio", "rossi");
		nicknameRicerca = new ArrayList<String>();
		//recupero i nickname degli user che appartengono al risultato della ricerca effettuata da "kikka"
		for(User user: risultatoRicerca){
			nicknameRicerca.add(user.getNickname());
		}

		//Test: verifico se tra i risultati della ricerca effettuata da "kikka" ci sono solo gli user che hanno come cognome "rossi"
		assertEquals(false, nicknameRicerca.contains("pippo"));
		assertEquals(true, nicknameRicerca.contains("toto"));


		//Test: nel sistema tra gli amici di "pippo" non esiste nessuno user che si chiama "antonio" "rossi"
		assertEquals(0, gestoreUserRemote.ricercaAmiciPerNomeCognome("pippo", "antonio", "rossi").size());

		//recupero i risultati della ricerca effettuata da "tonino" estraendone i nickname
		risultatoRicerca = gestoreUserRemote.ricercaAmiciPerNomeCognome("pippo", "antonio", "rossi");
		nicknameRicerca = new ArrayList<String>();
		//recupero i nickname degli user che appartengono al risultato della ricerca effettuata da "pippo"
		for(User user: risultatoRicerca){
			nicknameRicerca.add(user.getNickname());
		}
		//Test: verifico se tra i risultati della ricerca effettuata da pippo ci sono solo gli user che hanno come cognome "rossi"
		assertEquals(false, nicknameRicerca.contains("kikka"));
		assertEquals(false, nicknameRicerca.contains("toto"));

		//Test: svuoto il database e verifico che non vi rimanga pi� alcuna informazione
		assertEquals(true, SupportoTest.svuotaDB());	
	}

}
