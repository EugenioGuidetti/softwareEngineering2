package test;

import static org.junit.Assert.*;
import javax.naming.Context;
import org.junit.BeforeClass;
import org.junit.Test;

import entity.PropostaAbilita;
import session.GestorePropostaAbilitaRemote;
import session.GestoreUserRemote;

/**
 * Classe di test per testare tutti i metodi del GestorePropostaAbilita definito nel package session
 * 
 * @author Eugenio Guidetti - Claudio Fratto
 *
 */
public class GestorePropostaAbilitaTest {
	
	/*
	 * Definisco il contesto per poter agganciare il test alle session
	 */
	 static private Context jndiContext;  
     static private GestorePropostaAbilitaRemote gestorePropostaAbilitaRemote;
     static private GestoreUserRemote gestoreUserRemote;
     
     /*
      * Definisco il contesto, agganciando i gestori remoti attraverso le lookup
      */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		jndiContext = SupportoTest.getInitialContext();
		
		Object refProposta = jndiContext.lookup("GestorePropostaAbilitaJNDI");
		gestorePropostaAbilitaRemote = (GestorePropostaAbilitaRemote) refProposta;
		
		Object refUser = jndiContext.lookup("GestoreUserJNDI");
		gestoreUserRemote = (GestoreUserRemote) refUser;
	}

	/**
	 * Verifica del funzionamento del metodo inviaProposta(String nickname, String nomeAbilita, String descrizioneAbilita)
	 * definito nella classe GestorePropostaAbilita del package session
	 */
	@Test
	public void testInviaProposta(){
		
		//Creo un nuovo user (all'inizio non ha proposte di abilità inviate)
		gestoreUserRemote.registra("pippo", "pwd", "pippo@mail.com", "filippo", "roi", "/image/pippo.png", "cagliari", "maschio", 1988);
		
		//Test: creo una proposta di abilità (inviata dallo user "pippo")
		assertEquals(true, gestorePropostaAbilitaRemote.inviaProposta("pippo", "clown", "per feste private"));
		
		//Test: verifico se il numero di proposte abilita del sistema è pari ad 1
		assertEquals(true, gestorePropostaAbilitaRemote.getProposteNonVisionate().size() == 1);
		
		//Elimino lo user "pippo" e di conseguenza tutte le proposte a lui riferite (per gli altri test)
		gestoreUserRemote.elimina("pippo");
		
		//Test: verifico che non ci siano proposte di abilita associata all'utente "pippo"
		for(PropostaAbilita proposta: gestorePropostaAbilitaRemote.getProposteNonVisionate()){
			assertEquals(false, proposta.getUserProponente().getNickname() == "pippo");
		}
	}
	
		
	/**
	 * Verifica del funzionamento dei metodi:
	 * 1)	visionaProposta(long id) 
	 * 2) 	getProposteNonVisionate()
	 * 3)	getProposteVisionate()
	 * 
	 * definiti nella classe GestorePropostaAbilita del package session
	 */
	@Test
	public void testGetNonVisionateVisionProposta(){
		
		//Creo un nuovo user (all'inizio non ha proposte di abilità inviate)
		gestoreUserRemote.registra("pippo", "pwd", "pippo@mail.com", "filippo", "roi", "/image/pippo.png", "cagliari", "maschio", 1988);
		
		//Creo una proposta di abilità (inviata dallo user "pippo")
		gestorePropostaAbilitaRemote.inviaProposta("pippo", "clown", "per feste private");
		
		//Test: verifico che tale proposta appena inserita sia annotata come "nonVisionata" e che non esistono proposta annotatate come "visionate"
		assertEquals(true, gestorePropostaAbilitaRemote.getProposteNonVisionate().size() == 1);
		assertEquals(true, gestorePropostaAbilitaRemote.getProposteVisionate().size() == 0);
		
		//Recupero l'identificatore della proposta di nuova abilità inserita
		long idProposta = gestorePropostaAbilitaRemote.getProposteNonVisionate().get(0).getId();
		
		//Annoto come "visionata" la proposta di abilita identificata dall'idProposta
		assertEquals(true, gestorePropostaAbilitaRemote.visionaProposta(idProposta));
		
		//Test: verifico che la proposta appena visionata non compaia più tra quelle da visionare ma tra quelle già visionate
		assertEquals(true, gestorePropostaAbilitaRemote.getProposteNonVisionate().size() == 0);
		assertEquals(true, gestorePropostaAbilitaRemote.getProposteVisionate().size() == 1);
		
		//elimino lo user "pippo" e di conseguenza tutte le proposte a lui riferite (per gli altri test)
		gestoreUserRemote.elimina("pippo");
	}
	
	
	/**
	 * Verifica del funzionamento del metodo cancellaProposta(long id) definito nella classe GestorePropostaAbilita del
	 * package session
	 */
	@Test
	public void testCancellaProposta(){
		//Creo un nuovo user (all'inizio non ha proposte di abilità inviate)
		gestoreUserRemote.registra("pippo", "pwd", "pippo@mail.com", "filippo", "roi", "/image/pippo.png", "cagliari", "maschio", 1988);

		//Creo due proposte di abilità (inviate dallo user "pippo")
		gestorePropostaAbilitaRemote.inviaProposta("pippo", "clown", "per feste private");
		gestorePropostaAbilitaRemote.inviaProposta("pippo", "bagnino", "per piscine");
		
		//Recupero la proposta di abilità da eliminare
		PropostaAbilita propostaDaEliminare = gestorePropostaAbilitaRemote.getProposteNonVisionate().get(0);
		
		//Test: elimino la proposta di abilità recuperata
		assertEquals(true, gestorePropostaAbilitaRemote.cancellaProposta(propostaDaEliminare.getId()));
		
		//Test: verifico che nel sistema non esiste più una proposta identificata dall'id della proposta eliminata
		for(PropostaAbilita proposta: gestorePropostaAbilitaRemote.getProposteNonVisionate()){
			assertEquals(false, proposta.getId() == propostaDaEliminare.getId());
		}
		
		//Elimino lo user "pippo" e di conseguenza tutte le proposte a lui riferite (per gli altri test)
		gestoreUserRemote.elimina("pippo");
	}

}

