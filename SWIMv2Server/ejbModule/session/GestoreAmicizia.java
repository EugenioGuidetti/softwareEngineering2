package session;

import java.util.Calendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import org.jboss.ejb3.annotation.RemoteBinding;
import entity.User;
import entity.Amicizia;

@Stateless
@RemoteBinding(jndiBinding = "GestoreAmiciziaJNDI")
public class GestoreAmicizia implements GestoreAmiciziaRemote {
	
	@PersistenceContext(unitName = "swimv2_unit")
	private EntityManager entityManager;
	
    public GestoreAmicizia() {
    	super();
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Amicizia> getRichiesteInviate(String nicknameRichiedente) {
		List<Amicizia> richiesteAmiciziaInviate;
		User user = entityManager.find(User.class, nicknameRichiedente);
		Query query = entityManager.createNamedQuery("richiesteAmiciziaInviate");
		query.setParameter("user", user);
		try {
			richiesteAmiciziaInviate = query.getResultList();
			return richiesteAmiciziaInviate;
		} catch (IllegalStateException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Amicizia> getRichiesteRicevute(String nicknameDestinatario) {
		List<Amicizia> richiesteAmiciziaRicevute;
		User user = entityManager.find(User.class, nicknameDestinatario);
		Query query = entityManager.createNamedQuery("richiesteAmiciziaRicevute");
		query.setParameter("user", user);
		try {
			richiesteAmiciziaRicevute = query.getResultList();
			return richiesteAmiciziaRicevute;
		} catch (IllegalStateException e) {
			return null;
		}
	}

	@Override
	public boolean inviaRichiesta(String nicknameRichiedente, String nicknameDestinatario,
			Calendar momentoRichiesta) {
		Amicizia amicizia = new Amicizia();
		User userRichiedente = entityManager.find(User.class, nicknameRichiedente);
		User userDestinatario = entityManager.find(User.class, nicknameDestinatario);
		amicizia.setUserRichiedente(userRichiedente);
		amicizia.setUserDestinatario(userDestinatario);
		amicizia.setMomentoRichiesta(momentoRichiesta);
		try {
			entityManager.persist(amicizia);
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

	@Override
	public boolean accettaRichiesta(long id, Calendar momentoAccettazione) {
		Amicizia amicizia = entityManager.find(Amicizia.class, id);
		amicizia.setMomentoAccettazione(momentoAccettazione);
		try {
			entityManager.flush();
			return true;
		} catch (IllegalStateException e) {
			return false;
		} catch (TransactionRequiredException e) {
			return false;
		} catch (PersistenceException e) {
			return false;
		}
	}

	@Override
	public boolean rimuovi(long id) {
		Amicizia amicizia = entityManager.find(Amicizia.class, id);
		try {
			entityManager.remove(amicizia);
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
