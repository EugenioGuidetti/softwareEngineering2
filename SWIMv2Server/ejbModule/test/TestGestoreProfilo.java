package test;

import static org.junit.Assert.*;

import java.util.Hashtable;

import session.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.BeforeClass;
import org.junit.Test;



/**
 * Classe di test per testare tutti i metodi del GestoreProfilo definito nel package session
 * 
 * @author Eugenio Guidetti - Claudio Fratto
 *
 */
public class TestGestoreProfilo {

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
     public static void setUp() throws NamingException {
    	 jndiContext= getInitialContext();
    	 Object refProfilo = jndiContext.lookup("GestoreProfiloJNDI");
    	 gestoreProfiloRemote = (GestoreProfiloRemote) refProfilo;

    	 Object refUser = jndiContext.lookup("GestoreUserJNDI");
    	 gestoreUserRemote = (GestoreUserRemote) refUser;
    	 
    	 Object refAdmin = jndiContext.lookup("GestoreAdminJNDI");
    	 gestoreAdminRemote = (GestoreAdminRemote) refAdmin;

    	 gestoreAdminRemote.creaAdmin("admin", "admin", "admin@mail.com", "nome admin", "cognome admin", "avatar path admin");
    	 gestoreUserRemote.registra("pippo", "password", "pippo@mail.com", "filippo", "rossi", "prova", "milano", "maschio", 1990);

     }

     /**
      * Verifica il funzionamento del metodo controllaCredenziali(String nickname, String password) definito nella classe
      * GestoreProfilo del package session
      */
     @Test
     public void testControllaCredenziali(){ 
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

     }

     /**
      * Verifica il funzionamento del metodo getRuolo(String nickname) definito nella classe
      * GestoreProfilo del package session
      */
     @Test
     public void testGetRuolo(){
    	 /*
    	  * Test: il nickname "pippo" è associato ad un profilo classificato come "user"
    	  */
    	 assertEquals("user", gestoreProfiloRemote.getRuolo("pippo"));
    	 
    	 /*
    	  * Test: il nickname "admin" è associato ad un profilo classificato come "admin"
    	  */
    	 assertEquals("admin", gestoreProfiloRemote.getRuolo("admin"));
     }
     
     
     /**
      * Verifica il funzionamento del metodo disponibilitaNickname(String nickname) definito nella classe
      * GestoreProfilo del package session
      */
     @Test
     public void testDisponibilitaNickname(){
    	 /*
    	  * Test: il nickname "pippo" è già presente nel sistema e non può essere usato
    	  */
    	 assertEquals(false, gestoreProfiloRemote.disponibilitaNickname("pippo"));

    	 /*
    	  * Test: il nickname "kikka" non è presente nel sistema quindi è disponibile
    	  */
    	 assertEquals(true, gestoreProfiloRemote.disponibilitaNickname("kikka"));
     }


	
	/**
     * Tale metodo server per ottenere il Context da utilizzare per riuscire ad ottenere i riferimenti ai gestori remoti
     * definiti nel packge session
     * 
     * @return Context utilizzato per la lookup
     * @throws NamingException Eccezione lanciata nel caso il metodo non riesca ad ottenere il Context
     */
    static public Context getInitialContext() throws NamingException {
           
            Hashtable<String,String> env = new Hashtable<String,String>();
   
            env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
            env.put(Context.PROVIDER_URL, "localhost:1099");        
            return new InitialContext(env);        
    }
}
