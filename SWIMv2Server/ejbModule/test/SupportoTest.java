package test;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import entity.Abilita;
import entity.Admin;
import entity.User;
import session.GestoreAbilitaRemote;
import session.GestoreAdminRemote;
import session.GestoreAiutoRemote;
import session.GestoreAmiciziaRemote;
import session.GestorePropostaAbilitaRemote;
import session.GestoreUserRemote;

/**
 * Classe di supporto alle singole classi di test che contiente essenzialmente dei metodi statici per l'inizializzaione 
 * dei contesti dei vari test e per la verifica dello stato del database
 * 
 * @author Eugenio Guidetti - Claudio Fratto
 *
 */
public class SupportoTest {
	
	private static Context jndiContext;  
	private static GestoreAbilitaRemote gestoreAbilitaRemote;
	private static GestoreAdminRemote gestoreAdminRemote;
	private static GestoreUserRemote gestoreUserRemote;

	/**
	 * Tale metodo server per ottenere il Context da utilizzare per riuscire ad ottenere i riferimenti ai gestori remoti
	 * definiti nel packge session
	 * 
	 * @return Context utilizzato per la lookup
	 * @throws NamingException Eccezione lanciata nel caso il metodo non riesca ad ottenere il Context
	 */
	public static Context getInitialContext() throws NamingException {

		Hashtable<String,String> env = new Hashtable<String,String>();

		env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		env.put(Context.PROVIDER_URL, "localhost:1099");        
		return new InitialContext(env);        
	}

	/**
	 * Il metodo serve per verificare che nel database non sia presenta alcuna informazione
	 * 
	 * @return	true, se il database è vuoto; false, altrimenti
	 */
	public static boolean verificaDatabaseVuoto(){
		boolean risultato = true;

		Context jndiContext;  
		GestoreAbilitaRemote gestoreAbilitaRemote;
		GestoreAdminRemote gestoreAdminRemote;
		GestoreAiutoRemote gestoreAiutoRemote;
		GestoreAmiciziaRemote gestoreAmiciziaRemote;
		GestorePropostaAbilitaRemote gestorePropostaAbilitaRemote;
		GestoreUserRemote gestoreUserRemote;


		//creo i gestori per recuperare le informazioni dal database
		try {
			jndiContext = getInitialContext();

			Object refAbilita = jndiContext.lookup("GestoreAbilitaJNDI");
			gestoreAbilitaRemote = (GestoreAbilitaRemote) refAbilita;

			Object refAdmin = jndiContext.lookup("GestoreAdminJNDI");
			gestoreAdminRemote = (GestoreAdminRemote) refAdmin;

			Object refAiuto = jndiContext.lookup("GestoreAiutoJNDI");
			gestoreAiutoRemote = (GestoreAiutoRemote) refAiuto;

			Object refAmicizia = jndiContext.lookup("GestoreAmiciziaJNDI");
			gestoreAmiciziaRemote = (GestoreAmiciziaRemote) refAmicizia;

			Object refProposte = jndiContext.lookup("GestorePropostaAbilitaJNDI");
			gestorePropostaAbilitaRemote = (GestorePropostaAbilitaRemote) refProposte;

			Object refUser = jndiContext.lookup("GestoreUserJNDI");
			gestoreUserRemote = (GestoreUserRemote) refUser;

			//verifico che non ci sono utenti nel database
			if(gestoreUserRemote.getUserSistema().size() > 0){
				risultato = false;
			}

			//verifico che non ci sia una figura di admin
			Admin admin = gestoreAdminRemote.getAdmin("admin");
			if(admin != null){
				risultato = false;
			}

			//verifico che non ci siano abilità nel sistma
			if(gestoreAbilitaRemote.getAbilitaSistema().size() > 0){
				risultato = false;
			}

			//verifico che non ci siano proposte di abilità
			if(gestorePropostaAbilitaRemote.getProposteNonVisionate().size() > 0 || gestorePropostaAbilitaRemote.getProposteVisionate().size() > 0){
				risultato = false;
			}

			//verifico che non ci siano amicizie nel database
			for(User user: gestoreUserRemote.getUserSistema()){
				String nickname = user.getNickname();
				if( gestoreAmiciziaRemote.getAmicizieAllacciate(nickname).size() > 0 	||
						gestoreAmiciziaRemote.getRichiesteInviate(nickname).size() > 0  ||
						gestoreAmiciziaRemote.getRichiesteRicevute(nickname).size() > 0 
						){
					risultato = false;
				}
			}

			//verifico che non ci siano aiuti nel database
			for(User user: gestoreUserRemote.getUserSistema()){
				String nickname = user.getNickname();
				if( gestoreAiutoRemote.getAiutiForniti(nickname).size() > 0 	||
						gestoreAiutoRemote.getAiutiRicevuti(nickname).size() > 0  ||
						gestoreAiutoRemote.getRichiesteInviate(nickname).size() > 0 ||
						gestoreAiutoRemote.getRichiesteRicevute(nickname).size() > 0 
						){
					risultato = false;
				}
			}

		} catch (NamingException e) {
			risultato = false;
		}

		return risultato;

	}

	/**
	 * Il metodo server per elimare tutte le informazioni presenti nel database controllando che li elimini realmente
	 * 
	 * @return	true, se il database dopo l'eliminazione risulta vuoto; false, altrimenti
	 */
	public static boolean svuotaDB(){
		boolean risultato = true;

		//creo i gestori per recuperare le informazioni dal database
		try {
			jndiContext = getInitialContext();

			Object refAbilita = jndiContext.lookup("GestoreAbilitaJNDI");
			gestoreAbilitaRemote = (GestoreAbilitaRemote) refAbilita;

			Object refAdmin = jndiContext.lookup("GestoreAdminJNDI");
			gestoreAdminRemote = (GestoreAdminRemote) refAdmin;

			Object refUser = jndiContext.lookup("GestoreUserJNDI");
			gestoreUserRemote = (GestoreUserRemote) refUser;

			//elimino l'admin
			gestoreAdminRemote.rimuoviAdmin("admin");
			
			//elimino tutti gli user del sistema
			for(User user: gestoreUserRemote.getUserSistema()){
				gestoreUserRemote.elimina(user.getNickname());
			}
			
			//elimino tutte le proposte di abilità
			for(Abilita a: gestoreAbilitaRemote.getAbilitaSistema()){
				gestoreAbilitaRemote.elimina(a.getId());
			}
			
			//verifico che il db sia realmente vuoto
			risultato = verificaDatabaseVuoto();
			
		} catch (NamingException e) {
			risultato = false;
		}

		return risultato;

	}
}
