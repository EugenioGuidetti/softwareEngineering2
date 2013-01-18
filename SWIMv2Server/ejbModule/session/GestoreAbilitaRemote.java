package session;

import java.util.List;
import javax.ejb.Remote;
import entity.Abilita;

@Remote
public interface GestoreAbilitaRemote {
	
	/**
	 * Il metodo serve per recuperare l'abilit� associata all'id passato come parametro
	 * 
	 * @param id	codice identificativo dell'abilit� da recuperare
	 * 
	 * @return		abilit� associata all'id passato come parametro
	 */
	Abilita getAbilita(long id);
	
	/**
	 * Il metodo serve per recuperare tutte le abilit� disponibili nel sistema
	 * 
	 * @return		tutte le abilit� disponibili nel sistema
	 */
	List<Abilita> getAbilitaSistema();
	
	/**
	 * Il metodo serve per recuperare tutte le abilit� dichiarate da uno user
	 * 
	 * @param nickname	dello user di cui si vuole recuperare la lista delle abilit� dichiarate
	 * 
	 * @return		lista delle abilit� dichiarate dallo user associato al nickname passato come parametro
	 */
	List<Abilita> getAbilitaUser(String nickname);
	
	/**
	 * Il metodo serve per inserire nel sistema una nuova abilit�
	 * 
	 * @param nome		della nuova abilit� da inserire
	 * @param descrizione	breve descrizione della nuova abilit� da inserire
	 * @param iconaPath		percorso in cui recuperare l'immagine dell'icona associata alla nuova abilit� da inserire
	 * 
	 * @return			true, se l'operazione � andata a buon fine; false, altrimenti
	 */
	boolean crea(String nome, String descrizione, String iconaPath);

}
