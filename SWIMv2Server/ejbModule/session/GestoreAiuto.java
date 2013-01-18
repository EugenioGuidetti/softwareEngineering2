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
import entity.Abilita;
import entity.Aiuto;
import entity.User;

@Stateless
@RemoteBinding(jndiBinding = "GestoreAiutoJNDI")
public class GestoreAiuto implements GestoreAiutoRemote {
	
	@PersistenceContext(unitName = "swimv2_unit")
	private EntityManager entityManager;

    public GestoreAiuto() {
    	super();
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Aiuto> getRichiesteInviate(String nicknameRichiedente) {
		List<Aiuto> richiesteAiutoInviate;
		User user = entityManager.find(User.class, nicknameRichiedente);
		Query query = entityManager.createNamedQuery("richiesteAiutoInviate");
		query.setParameter("user", user);
		try {
			richiesteAiutoInviate = query.getResultList();
			return richiesteAiutoInviate;
		} catch (IllegalStateException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Aiuto> getRichiesteRicevute(String nicknameDestinatario) {
		List<Aiuto> richiesteAiutoRicevute;
		User user = entityManager.find(User.class, nicknameDestinatario);
		Query query = entityManager.createNamedQuery("richiesteAiutoRicevute");
		query.setParameter("user", user);
		try {
			richiesteAiutoRicevute = query.getResultList();
			return richiesteAiutoRicevute;
		} catch (IllegalStateException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Aiuto> getAiutiRicevutiEForniti(String nickname) {
		List<Aiuto> aiutiRicevutiEForniti;
		User user = entityManager.find(User.class, nickname);
		Query query = entityManager.createNamedQuery("aiutiRicevutEForniti");
		query.setParameter("user", user);
		try {
			aiutiRicevutiEForniti = query.getResultList();
			return aiutiRicevutiEForniti;
		} catch (IllegalStateException e) {
			return null;
		}
	}

	@Override
	public boolean inviaRichiesta(String nicknameRichiedente,
			String nicknameDestinatario, long idAbilitaRichiesta,
			String descrizione, Calendar momentoRichiesta) {
		Aiuto aiuto = new Aiuto();
		User userRichiedente = entityManager.find(User.class, nicknameRichiedente);
		User userDestinatario = entityManager.find(User.class, nicknameDestinatario);
		Abilita abilitaRichiesta = entityManager.find(Abilita.class, idAbilitaRichiesta);
		aiuto.setUserRichiedente(userRichiedente);
		aiuto.setUserDestinatario(userDestinatario);
		aiuto.setAbilitaRichiesta(abilitaRichiesta);
		aiuto.setDescrizione(descrizione);
		aiuto.setMomentoRichiesta(momentoRichiesta);
		try {
			entityManager.persist(aiuto);
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
		Aiuto aiuto = entityManager.find(Aiuto.class, id);
		aiuto.setMomentoAccettazione(momentoAccettazione);
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
	public boolean rifiutaRichiesta(long id) {
		Aiuto aiuto = entityManager.find(Aiuto.class, id);
		try {
			entityManager.remove(aiuto);
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
