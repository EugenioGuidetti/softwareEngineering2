package session;

import java.util.Set;
import javax.ejb.Remote;
import entity.Abilita;
import entity.User;

@Remote
public interface GestoreUserRemote {

	User getUser(String nickname);

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
