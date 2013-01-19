package test;

import static org.junit.Assert.*;
import javax.naming.Context;
import org.junit.BeforeClass;
import org.junit.Test;
import entity.Admin;
import session.GestoreAdminRemote;


/**
 * Classe di test per testare tutti i metodi del GestoreAdmin definito nel package session
 * 
 * @author Eugenio Guidetti - Claudio Fratto
 *
 */
public class GestoreAdminTest {

	/*
	 * Definisco il contesto per poter agganciare il test alle session
	 */
	 static private Context jndiContext;  
     static private GestoreAdminRemote gestoreAdminRemote;
     
     /*
      * Definisco il contesto, agganciando i gestori remoti attraverso le lookup
      * Definisco le condizioni iniziali del database inserendo l'utente admin
      */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		jndiContext = SupportoTest.getInitialContext();
		
		Object refAdmin = jndiContext.lookup("GestoreAdminJNDI");
		gestoreAdminRemote = (GestoreAdminRemote) refAdmin;

		gestoreAdminRemote.creaAdmin("admin", "admin", "admin@mail.com", "nome admin", "cognome admin", "avatar path admin");
	}

	/**
	 * Verifica il funzionamento del metodo getAdmin(String nickname) definito nella classe GestoreAdmin del package session
	 */
	@Test
	public void testGetAdmin(){
		/*
		 * Test: il profilo dell'utente amministratore deve avere i seguenti attributi:
		 * nickname = "admin", password = "admin", email = "admin@mail.com", nome = "nome admin", cognome = "cognome admin" 
		 * avatarPath = "avatar path admin"
		 */
		Admin admin = gestoreAdminRemote.getAdmin("admin");
		
		assertEquals(true, admin.getNickname().equals("admin"));
		assertEquals(true, admin.getPassword().equals("admin"));
		assertEquals(true, admin.getEmail().equals("admin@mail.com"));
		assertEquals(true, admin.getNome().equals("nome admin"));
		assertEquals(true, admin.getCognome().equals("cognome admin"));
		assertEquals(true, admin.getAvatarPath().equals("avatar path admin"));
	}
	
	/**
	 * Verifica il funzionamento del metodo modificaPassword(String nickname, String password) definito 
	 * nella classe GestoreAdmin del package session
	 */
	@Test
	public void testModificaPassword(){
		Admin admin = gestoreAdminRemote.getAdmin("admin");
		
		/*
		 * Test: modifico la password da "admin" a "passwordAdmin" dell'amministratore associato al nickname "admin"
		 */
		assertEquals(true, admin.getPassword().equals("admin"));
		admin.setPassword("passwordAdmin");
		assertEquals(true, admin.getPassword().equals("passwordAdmin"));
	}
	

	/**
	 * Verifica il funzionamento del metodo modificaEmail(String nickname, String email) definito 
	 * nella classe GestoreAdmin del package session
	 */
	@Test
	public void testModificaEmail(){
		Admin admin = gestoreAdminRemote.getAdmin("admin");
		
		/*
		 * Test: modifico l'indirizzo email "admin@mail.com" a "admin.admin@mail.com" dell'amministratore 
		 * associato al nickname "admin"
		 */
		assertEquals(true, admin.getEmail().equals("admin@mail.com"));
		admin.setEmail("admin.admin@mail.com");
		assertEquals(true, admin.getEmail().equals("admin.admin@mail.com"));
	}
	
	/**
	 * Verifica il funzionamento del metodo modificaNome(String nickname, String nome) definito 
	 * nella classe GestoreAdmin del package session
	 */
	@Test
	public void testModificaNome(){
		Admin admin = gestoreAdminRemote.getAdmin("admin");
		
		/*
		 * Test: modifico il nome da "nome admin" a "Admin" dell'amministratore associato al nickname "admin"
		 */
		assertEquals(true, admin.getNome().equals("nome admin"));
		admin.setNome("Admin");
		assertEquals(true, admin.getNome().equals("Admin"));
	}
	
	/**
	 * Verifica il funzionamento del metodo modificaCognome(String nickname, String cognome) definito 
	 * nella classe GestoreAdmin del package session
	 */
	@Test
	public void testModificaCognome(){
		Admin admin = gestoreAdminRemote.getAdmin("admin");
		
		/*
		 * Test: modifico il nome da "cognome admin" a "Cognome" dell'amministratore associato al nickname "admin"
		 */
		assertEquals(true, admin.getCognome().equals("cognome admin"));
		admin.setCognome("Cognome");
		assertEquals(true, admin.getCognome().equals("Cognome"));
	}
	
}
