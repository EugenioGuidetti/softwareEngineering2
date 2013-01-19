package session;

import java.util.List;
import java.util.Set;
import javax.ejb.Remote;
import entity.Abilita;
import entity.User;

@Remote
public interface GestoreUserRemote {

	/**
	 * Il metodo serve per ottenere il profilo associato all'utente associato al nickname passato come parametro
	 * 
	 * @param nickname	dell'utente di cui si vuole ottenere il profilo
	 * 
	 * @return	profilo dell'utente associato al nickname passato come parametro
	 */
	User getUser(String nickname);
	
	/**
	 * Il metodo serve per cercare tutti gli user che hanno tra le abilità dichiarate quella corrispondende all'id passato come parametro
	 * 
	 * @param idAbilita		id dell'abilità che ha il ruolo di filtro nella ricerca
	 * 
	 * @return		lista degli User che dichiarano l'abilità associata all'id passato come parametro
	 */
	List<User> ricercaPerAbilita(long idAbilita);
	
	/**
	 * Il metodo serve per cercare tutti gli User che hanno un nome uguale a quello passato come parametro
	 * 
	 * @param nome	dello user che ha il ruolo di filtro nella ricerca
	 * 
	 * @return		lista degli User che hanno un nome uguale a quello passato come parametro
	 */
	List<User>	ricercaPerNome(String nome);
	
	/**
	 * Il metodo serve per cercare tutti gli User che hanno un cognome uguale a quello passato come parametro
	 * 
	 * @param cognome	dello user che ha il ruolo di filtro nella ricerca
	 * 
	 * @return		lista degli User che hanno un cognome uguale a quello passato come parametro
	 */
	List<User>	ricercaPerCognome(String cognome);

	/**
	 * Il metodo serve per cercare tutti gli User che hanno un nome o un cognome uguale a quelli passati come parametri
	 * 
	 * @param nome		dello user che ha il ruolo di filtro nella ricerca
	 * @param cognome	dello user che ha il ruolo di filtro nella ricerca
	 * 
	 * @return		lista degli User che hanno il nome o il cognome uguali a quelli passati come parametri
	 */
	List<User>	ricercaPerNomeCognome(String nome, String cognome);

	/**
	 * Il metodo serve per cercare tutti gli User con i quali il profilo dello User associato al nickname passato 
	 * come parametro ha allacciato un rapporto di amicizia e che hanno tra le abilità dichiarate quella 
	 * corrispondende all'id passato come parametro
	 *  
	 * @param nickname	dello user che effettua la ricerca
	 * @param idAbilita	id dell'abilità che ha il ruolo di filtro nella ricerca
	 * 
	 * @return		lista degli User, amici dello user che effettua la ricerca, che dichiarano l'abilità associata 
	 * 				all'id passato come parametro
	 */
	List<User> ricercaAmiciPerAbilita(String nickname, long idAbilita);

	/**
	 * Il metodo serve per cercare tutti gli User, con i quali il profilo dello User associato al nickname passato come parametro
	 * ha allacciato un rapporto di amicizia, che hanno un nome uguale a quello passato come parametro
	 *  
	 * @param nickname	dello user che effettua la ricerca
	 * @param nome		dello user che ha il ruolo di filtro nella ricerca
	 * 
	 * @return		lista degli User, amici dello user che effettua la ricerca, che hanno il nome uguale a quello passato come parametro
	 */	
	List<User>	ricercaAmiciPerNome(String nickname, String nome);

	/**
	 * Il metodo serve per cercare tutti gli User, con i quali il profilo dello User associato al nickname passato come parametro
	 * ha allacciato un rapporto di amicizia, che hanno un cognome uguale a quello passato come parametro
	 *  
	 * @param nickname	dello user che effettua la ricerca
	 * @param cognome	dello user che ha il ruolo di filtro nella ricerca
	 * 
	 * @return		lista degli User, amici dello user che effettua la ricerca, che hanno il cognome uguale a quello passato come parametro
	 */
	List<User>	ricercaAmiciPerCognome(String nickname, String cognome);

	/**
	 * Il metodo serve per cercare tutti gli User, con i quali il profilo dello User associato al nickname passato come parametro
	 * ha allacciato un rapporto di amicizia, che hanno il nome o il cognome uguale a quelli passati come parametro
	 * 
	 * @param nickname	dello user che effettua la ricerca
	 * @param nome		dello user che ha il ruolo di filtro nella ricerca
	 * @param cognome	dello user che ha il ruolo di filtro nella ricerca
	 * 
	 * @return		lista degli USer, amici dello user che effettua la ricerca, che hanno il nome o il cognome uguali a quelli passati come parametro
	 */
	List<User>	ricercaAmiciPerNomeCognome(String nickname, String nome, String cognome);

	/**
	 * Il metodo serve per modificare la password del profilo associato ad uno User del sistema il cui nickname
	 * è passato come parametro
	 * 
	 * @param nickname	del profilo dello User di cui si vuole modificare la password
	 * @param password	nuova password da sostituire a quella già esistente
	 * 
	 * @return		true, se la modifica è andata a buon fine; false, altrimenti.
	 */
	boolean modificaPassword(String nickname, String password);

	/**
	 * Il metodo serve per modificare l'indirizzo email del profilo associato ad uno User del sistema il cui nickname
	 * è passato come parametro
	 * 
	 * @param nickname	del profilo dello User di cui si vuole modificare l'indirizzo email
	 * @param email		nuovo indirizzo email da sostituire a quello già esistente
	 * 
	 * @return		true, se la modifica è andata a buon fine; false, altrimenti.
	 */
	boolean modificaEmail(String nickname, String email);

	/**
	 * Il metodo serve per modificare il nome del profilo associato ad uno User del sistema il cui nickname
	 * è passato come parametro
	 * 
	 * @param nickname	del profilo dello User di cui si vuole modificare il nome
	 * @param nome		nuovo nome da sostituire a quello già esistente
	 * 
	 * @return		true, se la modifica è andata a buon fine; false, altrimenti.
	 */
	boolean modificaNome(String nickname, String nome);

	/**
	 * Il metodo serve per modificare il cognome del profilo associato ad uno User del sistema il cui nickname
	 * è passato come parametro
	 * 
	 * @param nickname	del profilo dello User di cui si vuole modificare il cognome
	 * @param cognome		nuovo cognome da sostituire a quello già esistente
	 * 
	 * @return		true, se la modifica è andata a buon fine; false, altrimenti.
	 */
	boolean modificaCognome(String nickname, String cognome);
	
	/**
	 * Il metodo serve per modificare il percorso dove è memorizzata l'immagine dell'avatar dello User del sistema il cui
	 * nickname è passato come parametro
	 * 
	 * @param nickname	del profilo dello User di cui si vuole modificare il percorso da dove recuperare l'immagine dell'avatar 
	 * @param avatarPath	nuovo percorso dell'immagine dell'avatra da sostituire a quello già esistente
	 * 
	 * @return		true, se la modifica è andata a buon fine; false, altrimenti.
	 */
	boolean modificaAvatar(String nickname, String avatarPath);
	
	/**
	 * Il metodo serve per modificare la città di residenza del profilo associato ad uno User del sistema il cui nickname
	 * è passato come parametro
	 * 
	 * @param nickname	del profilo dello User di cui si vuole modificare la città di residenza
	 * @param citta		nuova città di residenza da sostituire a qualla già esistente
	 * 
	 * @return		true, se la modifica è andata a buon fine; false, altrimenti.
	 */
	boolean modificaCitta(String nickname, String citta);
	
	/**
	 * Il metodo serve per modificare l'anno di nascita del profilo associato ad uno User del sistema il cui nickname 
	 * è passato come parametro
	 * 
	 * @param nickname	del profilo dello User di cui si vuole modificare l'anno di nascita
	 * @param annoNascita	nuovo anno di nascita da sostituire a quello già esistente
	 * 
	 * @return		true, se la modifica è andata a buon fine; false, altrimenti.
	 */
	boolean modificaAnnoNascita(String nickname, int annoNascita);
	
	/**
	 * Il metodo serve per modificare il sesso del profilo associato ad uno User del sistema il cui nickname
	 * è passato come parametro
	 * 
	 * @param nickname	del profilo dello User di cui si vuole modificare il cognome
	 * @param sesso		nuovo sesso da sostituire a quallo già esistente {"maschio", "femmina"}
	 * 
	 * @return		true, se la modifica è andata a buon fine; false, altrimenti.
	 */
	boolean modificaSesso(String nickname, String sesso);
	
	/**
	 * Il metodo serve per modificare l'insieme delle abilità dichiarate dall'User associato al nickname passato come parametro
	 * 
	 * @param nickname		dello User di cui si vuole modificare l'insieme di abilità dichiarate
	 * @param abilitaDichiarate		insieme delle abilità da sostiture a quello già esistente
	 * 
	 * @return		true, se la modifica è andata a buon fine; false, altrimenti.
	 */
	boolean modificaAbilitaDichiarate(String nickname, Set<Abilita> abilitaDichiarate);
	
	/**
	 * Il metodo server per inserire nel sistema un nuovo User le cui informazioni sono passate come parametri
	 * 
	 * @param nickname	dello user da inserire
	 * @param password	dello user da inserire
	 * @param email		dello user da inserire
	 * @param nome		dello user da inserire
	 * @param cognome	dello user da inserire
	 * @param avatarPath	dello user da inserire
	 * @param citta		dello user da inserire
	 * @param sesso		dello user da inserire
	 * @param annoNascita	dello user da inserire
	 * 
	 * @return		true, se l'operazione è andata a buon fine; false, altrimenti.
	 */
	boolean registra(String nickname, String password, String email, String nome, String cognome, 
								String avatarPath, String citta, String sesso, int annoNascita);
	
	/**
	 * Il metodo serve per rimuove dal sistema lo User associato al nickname passato come parametro
	 * 
	 * @param nickname	dello user da rimuovere
	 * 
	 * @return		true, se l'operazione è andata a buon fine; false, altrimenti.
	 */
	boolean elimina(String nickname);

	
}
