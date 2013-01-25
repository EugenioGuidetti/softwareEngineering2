package test;

import static org.junit.Assert.*;
import session.*;
import javax.naming.Context;
import javax.naming.NamingException;
import org.junit.BeforeClass;
import org.junit.Test;



/**
 * Classe di test che verifica i singoli metodi definiti nella classe GestoreProfilo del package session
 * 
 * @author Eugenio Guidetti - Claudio Fratto
 *
 */
public class GestoreProfiloTest {

	/*
	 * Definisco il contesto per poter agganciare il test alle session
	 */
	static private Context jndiContext;
	static private GestoreProfiloRemote gestoreProfiloRemote;  
	static private GestoreUserRemote gestoreUserRemote;
	static private GestoreAdminRemote gestoreAdminRemote;


	/*
	 * Definisco il contesto, agganciando i gestori remoti attraverso le lookup
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws NamingException {
		jndiContext = SupportoTest.getInitialContext();
		Object refProfilo = jndiContext.lookup("GestoreProfiloJNDI");
		gestoreProfiloRemote = (GestoreProfiloRemote) refProfilo;

		Object refUser = jndiContext.lookup("GestoreUserJNDI");
		gestoreUserRemote = (GestoreUserRemote) refUser;

		Object refAdmin = jndiContext.lookup("GestoreAdminJNDI");
		gestoreAdminRemote = (GestoreAdminRemote) refAdmin;

	}

	/**
	 * Verifica il funzionamento del metodo controllaCredenziali(String nickname, String password) definito nella classe
	 * GestoreProfilo del package session
	 */
	@Test
	public void testControllaCredenziali(){ 

		//Test: verifico che il database sia vuoto prima di iniziare il test
		assertEquals(true, SupportoTest.verificaDatabaseVuoto());

		gestoreAdminRemote.creaAdmin("admin", "admin", "admin@mail.com", "nome admin", "cognome admin", "avatar path admin");
		gestoreUserRemote.registra("pippo", "password", "pippo@mail.com", "filippo", "rossi", "prova", "milano", "maschio", 1990);

		/*
		 * Test: la coppia nickname e password si riferisce ad uno user esistente nel sistema 
		 * Lo user "pippo" è registrato al sistema con la password "password"
		 */
		assertEquals(true, gestoreProfiloRemote.controlloCredenziali("pippo", "password"));

		/*
		 * Test: nickname non esistente
		 * Non esiste nessuno user registrato con il nickname "kikka" e con password "mamma"
		 */
		assertEquals(false, gestoreProfiloRemote.controlloCredenziali("kikka", "mamma"));

		/*
		 * Test: la password di uno user registrato è scorretta
		 * La password dello user "pippo" non è "word"
		 */
		assertEquals(false, gestoreProfiloRemote.controlloCredenziali("pippo", "word"));

		//Test: svuoto il database e verifico che non vi rimanga più alcuna informazione
		assertEquals(true, SupportoTest.svuotaDB());
	}

	/**
	 * Verifica il funzionamento del metodo getRuolo(String nickname) definito nella classe
	 * GestoreProfilo del package session
	 */
	@Test
	public void testGetRuolo(){

		//Test: verifico che il database sia vuoto prima di iniziare il test
		assertEquals(true, SupportoTest.verificaDatabaseVuoto());

		gestoreAdminRemote.creaAdmin("admin", "admin", "admin@mail.com", "nome admin", "cognome admin", "avatar path admin");
		gestoreUserRemote.registra("pippo", "password", "pippo@mail.com", "filippo", "rossi", "prova", "milano", "maschio", 1990);

		/*
		 * Test: il nickname "pippo" è associato ad un profilo classificato come "user"
		 */
		assertEquals("user", gestoreProfiloRemote.getRuolo("pippo"));

		/*
		 * Test: il nickname "admin" è associato ad un profilo classificato come "admin"
		 */
		assertEquals("admin", gestoreProfiloRemote.getRuolo("admin"));

		//Test: svuoto il database e verifico che non vi rimanga più alcuna informazione
		assertEquals(true, SupportoTest.svuotaDB());
	}

}
