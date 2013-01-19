package session;

import java.util.List;
import java.util.Set;
import javax.ejb.Remote;
import entity.Abilita;
import entity.User;

@Remote
public interface GestoreUserRemote {

	User getUser(String nickname);
	
	List<User> ricercaPerAbilita(long idAbilita);
	
	List<User>	ricercaPerNome(String nome);
	
	List<User>	ricercaPerCognome(String cognome);

	List<User>	ricercaPerNomeCognome(String nome, String cognome);

	List<User> ricercaAmiciPerAbilita(String nickname, long idAbilita);

	List<User>	ricercaAmiciPerNome(String nickname, String nome);

	List<User>	ricercaAmiciPerCognome(String nickname, String cognome);

	List<User>	ricercaAmiciPerNomeCognome(String nickname, String nome, String cognome);

	boolean modificaPassword(String nickname, String password);

	boolean modificaEmail(String nickname, String email);

	boolean modificaNome(String nickname, String nome);

	boolean modificaCognome(String nickname, String cognome);
	
	boolean modificaAvatar(String nickname, String avatarPath);
	
	boolean modificaCitta(String nickname, String citta);
	
	boolean modificaAnnoNascita(String nickname, int annoNascita);
	
	boolean modificaSesso(String nickname, String sesso);
	
	boolean modificaAbilitaDichiarate(String nickname, Set<Abilita> abilitaDichiarate);
	
	boolean registra(String nickname, String password, String email, String nome, String cognome, 
								String avatarPath, String citta, String sesso, int annoNascita);
	
	boolean elimina(String nickname);

	
}
