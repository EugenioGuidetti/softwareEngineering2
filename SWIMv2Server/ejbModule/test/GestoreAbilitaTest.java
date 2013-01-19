package test;

import static org.junit.Assert.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.naming.Context;
import org.junit.BeforeClass;
import org.junit.Test;
import entity.Abilita;
import session.GestoreAbilitaRemote;
import session.GestoreUserRemote;

public class GestoreAbilitaTest {
	/*
	 * Definisco il contesto per poter agganciare il test alle session
	 */
	 static private Context jndiContext;  
     static private GestoreAbilitaRemote gestoreAbilitaRemote;
     static private GestoreUserRemote gestoreUserRemote;
     
     /*
      * Definisco il contesto, agganciando i gestori remoti attraverso le lookup
      * Definisco le condizioni iniziali del database inserendo l'utente admin
      */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		jndiContext = SupportoTest.getInitialContext();
		
		Object refAbilita = jndiContext.lookup("GestoreAbilitaJNDI");
		gestoreAbilitaRemote = (GestoreAbilitaRemote) refAbilita;
		
		Object refUser = jndiContext.lookup("GestoreUserJNDI");
		gestoreUserRemote = (GestoreUserRemote) refUser;
	}

	
	/**
	 * Verifica il funzionamento del metodo crea(String nome, String descrizione, String iconaPath) definito nella classe
	 * GestoreAbilita del package sessione
	 */
	@Test
	public void testCreaElimina(){
		/*
		 * All'inizio nel sistema non sono presenti abilità
		 */
		assertEquals(0, gestoreAbilitaRemote.getAbilitaSistema().size());
		
		/*
		 * Creo una nuova abilita
		 */
		assertEquals(true, gestoreAbilitaRemote.crea("gigolò", "per serate da favola", "path"));
		
		/*
		 * Nel sistema ora è presente un'abilità associata all'id 1 (auto-generato)
		 */
		assertEquals(1, gestoreAbilitaRemote.getAbilitaSistema().size());
		Abilita abilita = gestoreAbilitaRemote.getAbilita(1);
		assertEquals(true, abilita.getId() == 1);
		
		/*
		 * Elimino l'abilità associata all'id uguale a 1
		 */
		assertEquals(true, gestoreAbilitaRemote.elimina(1));
		
		/*
		 * A questo punto nel sistema non sono presenti abilità
		 */
		assertEquals(0, gestoreAbilitaRemote.getAbilitaSistema().size());
		
	}
	
	/**
	 * Verifica il funzionamento del metodo List<Abilita> getAbilitaSistema(); definito nella classe GestoreAbilita
	 * nel package session
	 */
	@Test
	public void testGetAbilitaSistema(){
		/*
		 * All'inizio nel sistema non sono presenti abilità
		 */
		assertEquals(true, gestoreAbilitaRemote.getAbilitaSistema().size() == 0);
		
		/*
		 * Creo 3 abilita nel sistema
		 */
		gestoreAbilitaRemote.crea("gigolò", "per serate da favola", "gigolò.png");
		gestoreAbilitaRemote.crea("bagnino", "piscina", "bagnino.png");
		gestoreAbilitaRemote.crea("meccanio", "pulizia carburatori", "meccanico.png");
		
		/*
		 * Ora nel sistema sono presetni 3 abilità
		 * 
		 */
		List<Abilita> abilitaSistema = gestoreAbilitaRemote.getAbilitaSistema();
		assertEquals(true, abilitaSistema.size() == 3);
		
		/*
		 * Elimino tutte le abilità create per il test successivo
		 */
		for(Abilita a: gestoreAbilitaRemote.getAbilitaSistema()){
			gestoreAbilitaRemote.elimina(a.getId());
		}
		
	}

	/**
	 * Verifica il funzionamento del metodo List<Abilita> getAbilitaUser(String nickname) definito nella classe GestoreAbilita
	 * nel package session
	 */
	@Test
	public void testGetAbilitaUser(){
		List<Abilita> listaAbilita;
		Set<Abilita> setAbilita = new HashSet<Abilita>();
			
		/*
		 * Aggiungole 3 abilità nel sistema ed uno user
		 */
		gestoreAbilitaRemote.crea("gigolò", "per serate da favola", "gigolò.png");
		gestoreAbilitaRemote.crea("bagnino", "piscina", "bagnino.png");
		gestoreAbilitaRemote.crea("meccanio", "pulizia carburatori", "meccanico.png");
		
		gestoreUserRemote.registra("vercingetorige", "pwd", "vercingetorige@mail.com", "filippo", "rossi", "vercingetorige.png", "milano", "maschio", 1987);
		
		/*
		 * Modifico l'insieme delle abilità dello user associato al nickname "vercingetorige"
		 */
		listaAbilita = gestoreAbilitaRemote.getAbilitaSistema();
		for(Abilita abilita: listaAbilita) {
			setAbilita.add(abilita);
		}
		gestoreUserRemote.modificaAbilitaDichiarate("vercingetorige", setAbilita);
		
		/*
		 * Verifico se il numero di abilità dichiarate dallo user "vercingetorige" è pari a 3
		 */
		assertEquals(true, gestoreAbilitaRemote.getAbilitaUser("vercingetorige").size() == 3);
		
		/*
		 * Elimino lo user "vercingetorige"
		 * Elimino tutte le abilità create per il test successivo
		 */
		gestoreUserRemote.elimina("vercingetorige");
		for(Abilita a: gestoreAbilitaRemote.getAbilitaSistema()){
			gestoreAbilitaRemote.elimina(a.getId());
		}
	}
}
