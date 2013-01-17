package session;

import javax.ejb.Remote;
import entity.Admin;

@Remote
public interface GestoreAdminRemote {
	
	Admin getAdmin(String nickname);
	
	void modificaPassword(String nickname, String password);
	
	void modificaEmail(String nickname, String email);
	
	void modificaNome(String nickname, String nome);
	
	void modificaCognome(String nickname, String cognome);

}
