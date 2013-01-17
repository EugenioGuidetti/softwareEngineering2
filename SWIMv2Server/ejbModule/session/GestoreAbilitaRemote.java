package session;

import java.util.List;
import javax.ejb.Remote;
import entity.Abilita;

@Remote
public interface GestoreAbilitaRemote {
	
	Abilita getAbilita(long id);
	
	List<Abilita> getAbilitaSistema();
	
	List<Abilita> getAbilitaUser(String nickname);
	
	boolean crea(String nome, String descrizione, String iconaPath);

}
