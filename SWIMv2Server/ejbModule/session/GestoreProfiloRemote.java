package session;

import javax.ejb.Remote;

@Remote
public interface GestoreProfiloRemote {
	
	/**
	 * Il metodo serve per verificare la correttezza delle credenziali di accesso al sistema passate come parametri
	 *  
	 * @param nickname	dell'utente
	 * @param password	dell'utente
	 * 
	 * @return	true, se le credenziali passate come parametri corrispondonoa quelle di un utente registrato al sistema; false, altrimenti.
	 */
	boolean controlloCredenziali(String nickname, String password);
	
	/**
	 * Il metodo serve per recuperare il ruolo dell'utente associato al nickname passato come parametro
	 * 
	 * @param nickname	dell'utente di cui si vuole ottenere il ruolo
	 * 
	 * @return	"admin", se il nickname corrisponde ad un amministratore del sistema; "user", se il nickname corrisponde ad uno user
	 */
	String getRuolo(String nickname);
	
	/**
	 * Il metodo serve per verificare la disponibilità di un nickname nel sistema
	 * 
	 * @param nickname	di cui si vuole verificare la disponibilità
	 * 
	 * @return	true, se il nickname passato come parametro è disponibile; false, altrimenti.
	 */
	boolean disponibilitaNickname(String nickname);

}
