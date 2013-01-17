package session;

import javax.ejb.Remote;

@Remote
public interface GestoreProfiloRemote {
	
	boolean controlloCredenziali(String nickname, String password);
	
	String getRuolo(String nickname);
	
	boolean disponibilitaNickname(String nickname);

}
