package test;

import static org.junit.Assert.*;

import java.util.Set;

import javax.naming.Context;

import org.junit.BeforeClass;
import org.junit.Test;

import entity.Abilita;
import entity.Admin;
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
	public void testRegistraElimina(){		
		/*
		 * Test: lo user "toto" si registra al sistema (nickname disponibile)
		 */
		assertEquals(true, gestoreUserRemote.registra("toto", "prova", "toto@mail.com", "salvatore", "rossi", "path/toto.png", "palermo", "maschio", 1967));
		
		/*
		 * Test: lo user "toto" si elimina dal sistema
		 */
		assertEquals(true, gestoreUserRemote.elimina("toto"));
	}
	
	/**
	 * Verifica il funzionamento del metodo modificaPassword(String nickname, String password) definito 
	 * nella classe GestoreUser del package session
	 */
	@Test
	public void testModificaPassword(){
		gestoreUserRemote.registra("pippo", "pwd", "pippo@mail.com", "filippo", "roi", "/image/pippo.png", "cagliari", "maschio", 1988);
		
		User user = gestoreUserRemote.getUser("pippo");
		
		/*
		 * Test: modifico la password da "pwd" a "password" dello user associato al nickname "pippo"
		 */
		assertEquals(true, user.getPassword().equals("pwd"));
		assertEquals(true, gestoreUserRemote.modificaPassword("pippo", "password"));
		
		user = gestoreUserRemote.getUser("pippo");
		assertEquals(true, user.getPassword().equals("password"));
		
		gestoreUserRemote.elimina("pippo");
	}
	
	
	/**
	 * Verifica il funzionamento del metodo modificaEmail(String nickname, String email) definito 
	 * nella classe GestoreUser del package session
	 */
	@Test
	public void testModificaEmail(){
		gestoreUserRemote.registra("pippo", "pwd", "pippo@mail.com", "filippo", "roi", "/image/pippo.png", "cagliari", "maschio", 1988);

		User user = gestoreUserRemote.getUser("pippo");

		/*
		 * Test: modifico l'indirizzo email da "pippo@mail.com" a "pippo.pippo@mail.com" dello user associato al nickname "pippo"
		 */
		assertEquals(true, user.getEmail().equals("pippo@mail.com"));
		assertEquals(true, gestoreUserRemote.modificaEmail("pippo", "pippo.pippo@mail.com"));
		
		user = gestoreUserRemote.getUser("pippo");
		assertEquals(true, user.getEmail().equals("pippo.pippo@mail.com"));

		gestoreUserRemote.elimina("pippo");
	}
	
	
	
	/**
	 * Verifica il funzionamento del metodo modificaNome(String nickname, String nome) definito 
	 * nella classe GestoreUser del package session
	 */
	@Test
	public void testModificaNome(){
		gestoreUserRemote.registra("pippo", "pwd", "pippo@mail.com", "filippo", "roi", "/image/pippo.png", "cagliari", "maschio", 1988);

		User user = gestoreUserRemote.getUser("pippo");

		/*
		 * Test: modifico il nome da "filippo" a "antonio" dello user associato al nickname "pippo"
		 */
		assertEquals(true, user.getNome().equals("filippo"));
		assertEquals(true, gestoreUserRemote.modificaNome("pippo", "antonio"));
		
		user = gestoreUserRemote.getUser("pippo");
		assertEquals(true, user.getNome().equals("antonio"));

		gestoreUserRemote.elimina("pippo");
	}

	
	/**
	 * Verifica il funzionamento del metodo modificaCognome(String nickname, String cognome) definito 
	 * nella classe GestoreUser del package session
	 */
	@Test
	public void testModificaCognome(){
		gestoreUserRemote.registra("pippo", "pwd", "pippo@mail.com", "filippo", "roi", "/image/pippo.png", "cagliari", "maschio", 1988);

		User user = gestoreUserRemote.getUser("pippo");

		/*
		 * Test: modifico il cognome da "roi" a "la russa" dello user associato al nickname "pippo"
		 */
		assertEquals(true, user.getCognome().equals("roi"));
		assertEquals(true, gestoreUserRemote.modificaCognome("pippo", "la russa"));
		
		user = gestoreUserRemote.getUser("pippo");
		assertEquals(true, user.getCognome().equals("la russa"));

		gestoreUserRemote.elimina("pippo");
	}


	/**
	 * Verifica il funzionamento del metodo modificaAvatar(String nickname, String avatarPath) definito nella classe
	 * GestoreUser del package session
	 */
	@Test
	public void testModificaPathAvatar(){
		gestoreUserRemote.registra("pippo", "pwd", "pippo@mail.com", "filippo", "roi", "/image/pippo.png", "cagliari", "maschio", 1988);

		User user = gestoreUserRemote.getUser("pippo");

		/*
		 * Test: modifico il path dell'avatar da "/image/pippo.png" a "/image/pippo2.png" dello user associato al nickname "pippo"
		 */
		assertEquals(true, user.getAvatarPath().equals("/image/pippo.png"));
		assertEquals(true, gestoreUserRemote.modificaAvatar("pippo", "/image/pippo2.png"));
		
		user = gestoreUserRemote.getUser("pippo");
		assertEquals(true, user.getAvatarPath().equals("/image/pippo2.png"));

		gestoreUserRemote.elimina("pippo");

	}
	
	/**
	 * Verifica il funzionamento del metodo modificaCitta(String nickname, String citta) definito nella classe
	 * GestoreUser del package session
	 */
	@Test
	public void testModificaCitta(){
		gestoreUserRemote.registra("pippo", "pwd", "pippo@mail.com", "filippo", "roi", "/image/pippo.png", "cagliari", "maschio", 1988);

		User user = gestoreUserRemote.getUser("pippo");

		/*
		 * Test: modifico la città da "cagliari" a "palermo" dello user associato al nickname "pippo"
		 */
		assertEquals(true, user.getCitta().equals("cagliari"));
		assertEquals(true, gestoreUserRemote.modificaCitta("pippo", "palermo"));
		
		user = gestoreUserRemote.getUser("pippo");
		assertEquals(true, user.getCitta().equals("palermo"));

		gestoreUserRemote.elimina("pippo");

	}
	
	/**
	 * Verifica il funzionamento del metodo modificaAnnoNascita(String nickname, int annoNascita) definito nella classe
	 * GestoreUser del package session
	 */
	@Test
	public void testModificaAnnoNascita(){
		gestoreUserRemote.registra("pippo", "pwd", "pippo@mail.com", "filippo", "roi", "/image/pippo.png", "cagliari", "maschio", 1988);

		User user = gestoreUserRemote.getUser("pippo");

		/*
		 * Test: modifico l'anno di nascita da "1988" a "1970" dello user associato al nickname "pippo"
		 */
		assertEquals(true, user.getAnnoNascita() == 1988);
		assertEquals(true, gestoreUserRemote.modificaAnnoNascita("pippo", 1970));
		
		user = gestoreUserRemote.getUser("pippo");
		assertEquals(true, user.getAnnoNascita() == 1970);

		gestoreUserRemote.elimina("pippo");
	}
	
	
	/**
	 * Verifica il funzionamento del metodo modificaSesso(String nickname, String sesso) definito nella classe GestoreUser
	 * del package session
	 */
	@Test
	public void testModificaSesso(){
		gestoreUserRemote.registra("pippo", "pwd", "pippo@mail.com", "filippo", "roi", "/image/pippo.png", "cagliari", "maschio", 1988);

		User user = gestoreUserRemote.getUser("pippo");

		/*
		 * Test: modifico il sesso da "maschio" a "femmina"dello user associato al nickname "pippo"
		 */
		assertEquals(true, user.getSesso().equals("maschio"));
		assertEquals(true, gestoreUserRemote.modificaSesso("pippo", "femmina"));
		
		user = gestoreUserRemote.getUser("pippo");
		assertEquals(true, user.getSesso().equals("femmina"));

		gestoreUserRemote.elimina("pippo");

	}

}