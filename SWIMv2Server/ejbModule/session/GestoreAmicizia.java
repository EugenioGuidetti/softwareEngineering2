package session;

import java.util.Calendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Amicizia> getAmicizieAllacciate(String nickname) {
		List<Amicizia> amicizieAllacciate;
		User user = entityManager.find(User.class, nickname);
		Query query = entityManager.createNamedQuery("amicizieAllacciate");
		query.setParameter("user", user);
		try {
			amicizieAllacciate = query.getResultList();
			return amicizieAllacciate;
		} catch (IllegalStateException e) {
			return null;
		}
	}

	@Override
	public boolean inviaRichiesta(String nicknameRichiedente, String nicknameDestinatario, Calendar momentoRichiesta) {
		Amicizia amicizia = new Amicizia();
		User userRichiedente = entityManager.find(User.class, nicknameRichiedente);
		User userDestinatario = entityManager.find(User.class, nicknameDestinatario);
		
		/*
		 * prima di inoltrare la richiesta di amicizia verifico se è già presente una richiesta di amicizia tra i due user
		 * o se tra di essi è già presente un rapporto di amicizia
		 */
		if(! controllaAmici(nicknameRichiedente, nicknameDestinatario)){
			//richiesta non presente e userDestinatario non è già amico di userRichiedente
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
		else{
			//richiesta già presente o userDestinatario è già amico di userRichiedente
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

	@SuppressWarnings("unused")
	@Override
	public boolean controllaAmici(String nicknameRichiedente, String nicknameDestinatario) {
		Amicizia amicizia;
		User userRichiedente = entityManager.find(User.class, nicknameRichiedente);
		User userDestinatario = entityManager.find(User.class, nicknameDestinatario);

		Query query = entityManager.createNamedQuery("controllaAmici");
		query.setParameter("userRichiedente", userRichiedente);
		query.setParameter("userDestinatario", userDestinatario);
		
		try{
			amicizia = (Amicizia) query.getSingleResult();
			return true;
		}catch (NoResultException e) {
			return false;
		}
	}

	
}
