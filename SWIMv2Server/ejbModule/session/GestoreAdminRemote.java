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
	
	/**
	 * Il metodo serve per creare un nuovo profilo a cui assegnare il ruolo di amministratore del sistema
	 * (predisposto per future versioni)
	 * 
	 * @param nickname	del profilo a cui si vuole assegnare il ruolo di amministratore
	 * @param password	del profilo a cui si vuole assegnare il ruolo di amministratore
	 * @param email		del profilo a cui si vuole assegnare il ruolo di amministratore
	 * @param nome		del profilo a cui si vuole assegnare il ruolo di amministratore
	 * @param cognome	del profilo a cui si vuole assegnare il ruolo di amministratore
	 * @param avatarPath	del profilo a cui si vuole assegnare il ruolo di amministratore
	 * 
	 * @return			true, se l'operazione va a buon fine; false, altrimenti.
	 */
	boolean creaAdmin(String nickname, String password, String email, String nome, String cognome, String avatarPath);

	/**
	 * Il metodo serve per rimuovere il profilo di amministratore del sistema associato al nickname passato come parametro
	 * (predisposto per future versione)
	 * 
	 * @param nickname	del profilo di amministratore da rimuover
	 * 
	 * @return			true, se l'operazione va a buon fine; false, altrimenti.
	 */
	boolean rimuoviAdmin(String nickname);
}
