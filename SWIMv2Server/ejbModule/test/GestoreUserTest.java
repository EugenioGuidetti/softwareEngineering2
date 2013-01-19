package test;

import static org.junit.Assert.*;

import javax.naming.Context;

import org.junit.BeforeClass;
import org.junit.Test;

import entity.User;

import session.GestoreAdminRemote;
import session.GestoreUserRemote;

/**
 * Classe di test per testare tutti i metodi del GestoreUser definito nel package session
 * 
 * @author Eugenio Guidetti - Claudio Fratto
 *
 */
public class GestoreUserTest {
	/*
	 * Definisco il contesto per poter agganciare il test alle session
	 */
	static private Context jndiContext;  
	static private GestoreUserRemote gestoreUserRemote;

	/*
	 * 
	 * Definisco il contesto, agganciando i gestori remoti attraverso le lookup
	 * Definisco le condizioni iniziali del database inserendo l'utente admin
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		jndiContext = SupportoTest.getInitialContext();

		Object refUser = jndiContext.lookup("GestoreUserJNDI");
		gestoreUserRemote = (GestoreUserRemote) refUser;
	}

	/**
	 * Verifica il funzionamento del metodo registra(String nickname, String password, String email, String nome, 
	 * 										String cognome,	String avatarPath, String citta, String sesso, int annoNascita);
	 * definito nella classe GestoreUser del package session
	 */
	@Test
	public void testRegistra(){		
		/*
		 * Test: lo user "toto" si registra al sistema (nickname disponibile)
		 */
		assertEquals(true, gestoreUserRemote.registra("toto", "prova", "toto@mail.com", "salvatore", "rossi", "path/toto.png", "palermo", "maschio", 1967));
	}
	
	
	

}
