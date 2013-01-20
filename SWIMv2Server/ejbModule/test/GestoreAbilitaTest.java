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
		
		//Test: all'inizio nel sistema non sono presenti abilità
		assertEquals(0, gestoreAbilitaRemote.getAbilitaSistema().size());
		
		//Test: creo due nuova abilita
		assertEquals(true, gestoreAbilitaRemote.crea("gigolò", "per serate da favola", "path"));
		assertEquals(true, gestoreAbilitaRemote.crea("cameriere", "per serate di gala", "path"));
		
		//Test: nel sistema ora sono presenti due abilita
		assertEquals(2, gestoreAbilitaRemote.getAbilitaSistema().size());
		
		//recupero l'abilità da eliminare
		Abilita abilitaDaEliminare = gestoreAbilitaRemote.getAbilitaSistema().get(0);
		
		//Test: elimino l'abilità recuperata
		assertEquals(true, gestoreAbilitaRemote.elimina(abilitaDaEliminare.getId()));
		
		//Test: verifico che nel sistema non ci sia più un'abiità identificata dall'id dell'abilità eliminata
		for(Abilita abilita: gestoreAbilitaRemote.getAbilitaSistema()){
			assertEquals(false, abilita.getId() == abilitaDaEliminare.getId());
		}
		
		//Elimino le altre abilità per i test successivi
		for(Abilita abilita: gestoreAbilitaRemote.getAbilitaSistema()){
			gestoreAbilitaRemote.elimina(abilita.getId());
		}
	}
	
	/**
	 * Verifica il funzionamento del metodo List<Abilita> getAbilitaSistema(); definito nella classe GestoreAbilita
	 * nel package session
	 */
	@Test
	public void testGetAbilitaSistema(){
		
		//Test: all'inizio nel sistema non sono presenti abilità
		assertEquals(true, gestoreAbilitaRemote.getAbilitaSistema().size() == 0);
		
		//Creo 3 abilita nel sistema
		gestoreAbilitaRemote.crea("gigolò", "per serate da favola", "gigolò.png");
		gestoreAbilitaRemote.crea("bagnino", "piscina", "bagnino.png");
		gestoreAbilitaRemote.crea("meccanio", "pulizia carburatori", "meccanico.png");
		
		//Test: ora nel sistema sono presetni 3 abilità
		List<Abilita> abilitaSistema = gestoreAbilitaRemote.getAbilitaSistema();
		assertEquals(true, abilitaSistema.size() == 3);
		
		//Elimino tutte le abilità create per il test successivo
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
			
		//Aggiungole 3 abilità nel sistema ed uno user
		gestoreAbilitaRemote.crea("gigolò", "per serate da favola", "gigolò.png");
		gestoreAbilitaRemote.crea("bagnino", "piscina", "bagnino.png");
		gestoreAbilitaRemote.crea("meccanio", "pulizia carburatori", "meccanico.png");
		
		gestoreUserRemote.registra("vercingetorige", "pwd", "vercingetorige@mail.com", "filippo", "rossi", "vercingetorige.png", "milano", "maschio", 1987);
		
		//Modifico l'insieme delle abilità dello user associato al nickname "vercingetorige"
		listaAbilita = gestoreAbilitaRemote.getAbilitaSistema();
		for(Abilita abilita: listaAbilita) {
			setAbilita.add(abilita);
		}
		gestoreUserRemote.modificaAbilitaDichiarate("vercingetorige", setAbilita);
		
		//Test: verifico se il numero di abilità dichiarate dallo user "vercingetorige" è pari a 3
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
