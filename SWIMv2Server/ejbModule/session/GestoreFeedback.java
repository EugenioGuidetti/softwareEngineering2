package session;

import java.util.Calendar;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TransactionRequiredException;
import org.jboss.ejb3.annotation.RemoteBinding;
import entity.Aiuto;
import entity.Feedback;

@Stateless
@RemoteBinding(jndiBinding = "GestoreFeedbackJNDI")
public class GestoreFeedback implements GestoreFeedbackRemote {
	
	@PersistenceContext(unitName = "swimv2_unit")
	private EntityManager entityManager;

    public GestoreFeedback() {
    	super();
    }

	@Override
	public Feedback getFeedback(long idAiuto) {
		Feedback feedback = entityManager.find(Feedback.class, idAiuto);
		return feedback;
	}

	@Override
	public boolean rilascia(long idAiuto, int valutazioneNumerica,
			String valutazioneEstesa, Calendar momentoRilascio) {
		Feedback feedback = new Feedback();
		Aiuto aiutoValutato = entityManager.find(Aiuto.class, idAiuto);
		feedback.setId(idAiuto);
		feedback.setValutazioneNumerica(valutazioneNumerica);
		feedback.setValutazioenEstesa(valutazioneEstesa);
		feedback.setAiutoValutato(aiutoValutato);
		try {
			entityManager.persist(feedback);
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
