package session;

import java.util.List;
import javax.ejb.Remote;
import entity.Abilita;

@Remote
public interface GestoreAbilitaRemote {
	
	/**
	 * Il metodo serve per recuperare l'abilità associata all'id passato come parametro
	 * 
	 * @param id	codice identificativo dell'abilità da recuperare
	 * 
	 * @return		abilità associata all'id passato come parametro
	 */
	Abilita getAbilita(long id);
	
	/**
	 * Il metodo serve per recuperare tutte le abilità disponibili nel sistema
	 * 
	 * @return		tutte le abilità disponibili nel sistema
	 */
	List<Abilita> getAbilitaSistema();
	
	/**
	 * Il metodo serve per recuperare tutte le abilità dichiarate da uno user
	 * 
	 * @param nickname	dello user di cui si vuole recuperare la lista delle abilità dichiarate
	 * 
	 * @return		lista delle abilità dichiarate dallo user associato al nickname passato come parametro
	 */
	List<Abilita> getAbilitaUser(String nickname);
	
	/**
	 * Il metodo serve per inserire nel sistema una nuova abilità
	 * 
	 * @param nome		della nuova abilità da inserire
	 * @param descrizione	breve descrizione della nuova abilità da inserire
	 * @param iconaPath		percorso in cui recuperare l'immagine dell'icona associata alla nuova abilità da inserire
	 * 
	 * @return			true, se l'operazione è andata a buon fine; false, altrimenti
	 */
	boolean crea(String nome, String descrizione, String iconaPath);

}
