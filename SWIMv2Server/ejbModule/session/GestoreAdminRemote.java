package session;

import javax.ejb.Remote;
import entity.Admin;

@Remote
public interface GestoreAdminRemote {
	
	/**
	 * Il metodo serve per ottenere il profilo associato all'utente amministratore del sistema
	 * 
	 * @param nickname	dell'amministratore del sistema (default "admin")
	 * 
	 * @return	profilo dell'amministratore del sistema
	 */
	Admin getAdmin(String nickname);
	
	/**
	 * Il metodo serve per modificare la password del profilo associato al nickname passato come parametro
	 * 
	 * @param nickname	del profilo di cui si vuole modificare la password
	 * @param password	nuova password da sostituire a quella già esistente
	 */
	void modificaPassword(String nickname, String password);
	
	/**
	 * Il metodo serve per modificare l'indirizzo email del profilo associato al nickname passato come parametro
	 * 
	 * @param nickname	del profilo di cui si vuole modificare l'indirizzo email
	 * @param email		nuovo indirizzo email da sostituire a quello già esistente
	 */
	void modificaEmail(String nickname, String email);
	
	/**
	 * Il metodo serve per modificare il nome del profilo associato al nickname passato come parametro
	 * 
	 * @param nickname	del profilo di cui si vuole modificare il nome
	 * @param nome		nuovo nome da sostituire a quello già esistente
	 */
	void modificaNome(String nickname, String nome);
	
	/**
	 * Il metodo serve per modificare il cognome del profilo associato al nickname passato come parametro
	 * 
	 * @param nickname	del profilo di cui si vuole modificare il cognome
	 * @param cognome		nuovo cognome da sostituire a quello già esistente
	 */
	void modificaCognome(String nickname, String cognome);

}
