package session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.ejb3.annotation.RemoteBinding;
import entity.Profilo;
import entity.User;

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
		String ruolo;
		
		Query q = entityManager.createNativeQuery("SELECT ruolo FROM profilo WHERE nickname = :nickname");
		q.setParameter("nickname", nickname);
		
		ruolo = (String) q.getSingleResult();
		
		if(ruolo.equals(ADMIN)){
			return ADMIN;
		}
		else{
			return USER;
		}
	}

	@Override
	public boolean disponibilitaNickname(String nickname) {
		Profilo profilo = entityManager.find(Profilo.class, nickname);
		
		if(profilo == null){
			//nickname disponibile
			return true;
		}
		return false;
	}

}
