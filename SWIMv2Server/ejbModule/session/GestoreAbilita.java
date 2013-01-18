package session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import org.jboss.ejb3.annotation.RemoteBinding;
import entity.Abilita;
import entity.User;

@Stateless
@RemoteBinding(jndiBinding = "GestoreAbilitaJNDI")
public class GestoreAbilita implements GestoreAbilitaRemote {
	
	@PersistenceContext(unitName = "swimv2_unit")
	private EntityManager entityManager;
	
    public GestoreAbilita() {
    	super();
    }

	@Override
	public Abilita getAbilita(long id) {
		Abilita abilita = entityManager.find(Abilita.class, id);
		return abilita;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Abilita> getAbilitaSistema() {
		List<Abilita> abilitaSistema;
		Query query = entityManager.createNamedQuery("tutteLeAbilita");
		try {
			abilitaSistema = query.getResultList();
			return abilitaSistema;
		} catch (IllegalStateException e) {
			return null;
		}	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Abilita> getAbilitaUser(String nickname) {
		List<Abilita> abilitaUser;
		User user = entityManager.find(User.class, nickname);
		Query query = entityManager.createNamedQuery("tutteLeAbilitaDiUnoUser");
		query.setParameter("user", user);
		try {
			abilitaUser = query.getResultList();
			return abilitaUser;
		} catch (IllegalStateException e) {
			return null;
		}
	}
	
	@Override
	public boolean crea(String nome, String descrizione, String iconaPath) {
		Abilita abilita = new Abilita();
		abilita.setNome(nome);
		abilita.setDescrizione(descrizione);
		abilita.setIconaPath(iconaPath);
		try {
			entityManager.persist(abilita);
			entityManager.flush();
			return true;
		} catch (IllegalStateException e) {
			return false;
		} catch (IllegalArgumentException e) {
			return false;
		} catch (TransactionRequiredException e) {
			return false;
		} catch (PersistenceException e) {
			return false;
		}
	}

}
