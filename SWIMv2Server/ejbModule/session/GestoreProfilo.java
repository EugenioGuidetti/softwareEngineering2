package session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jboss.ejb3.annotation.RemoteBinding;
import entity.Profilo;
import entity.User;

/**
 * Session Bean implementation class GestoreProfilo
 */
@Stateless
@RemoteBinding(jndiBinding = "GestoreProfiloJNDI")
public class GestoreProfilo implements GestoreProfiloRemote {
	
	private static final String USER = "user";
	private static final String ADMIN = "admin";
	
	@PersistenceContext(unitName = "swimv2_unit")
	private EntityManager entityManager;

    public GestoreProfilo() {
    	super();
    }

	@Override
	public boolean controlloCredenziali(String nickname, String password) {
		Profilo profilo = entityManager.find(Profilo.class, nickname);
		if(profilo == null) {
			return false;
		}
		if(!profilo.getPassword().equals(password)) {
			return false;
		}
		return true;
	}

	@Override
	public String getRuolo(String nickname) {
		User user = entityManager.find(User.class, nickname);
		if(user == null) {
			return ADMIN;
		}
		return USER;
	}

	@Override
	public boolean controllaDisponibilitaNickname(String nickname) {
		Profilo profilo = entityManager.find(Profilo.class, nickname);
		
		if(profilo == null){
			//nickname disponibile
			return true;
		}
		return false;
	}

}
