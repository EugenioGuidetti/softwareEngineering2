package test;

import static org.junit.Assert.*;
import session.*;
import javax.naming.Context;
import javax.naming.NamingException;
import org.junit.BeforeClass;
import org.junit.Test;



/**
 * Classe di test per testare tutti i metodi del GestoreProfilo definito nel package session
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
      * Definisco le condizioni iniziali del database inserendo due utenti: l'admin e lo user pippo
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

    	 //rimuovo i profili creati
    	 gestoreAdminRemote.rimuoviAdmin("admin");
    	 gestoreUserRemote.elimina("pippo");
     }

     /**
      * Verifica il funzionamento del metodo getRuolo(String nickname) definito nella classe
      * GestoreProfilo del package session
      */
     @Test
     public void testGetRuolo(){
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
    	 
    	//rimuovo i profili creati
    	 gestoreAdminRemote.rimuoviAdmin("admin");
    	 gestoreUserRemote.elimina("pippo");
     }
     
}
