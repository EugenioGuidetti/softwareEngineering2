package session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.jboss.ejb3.annotation.RemoteBinding;
import entity.Abilita;
import entity.ReputazioneAbilita;
import entity.User;

@Stateless
@RemoteBinding(jndiBinding = "GestoreUtilitiesJNDI")
public class GestoreUtilities implements GestoreUtilitiesRemote {

	@PersistenceContext(unitName = "swimv2_unit")
	private EntityManager entityManager;
	
    public GestoreUtilities() {
    	super();
    }

	@Override
	public ReputazioneAbilita getReputazioneAbilita(String nickname,long idAbilita) {
	
		ReputazioneAbilita reputazioneAbilita;
		User user;
		Abilita abilita;
		Query query;
		long numeroFeedback;
		double mediaValutazioni;
		
		//recupero lo user
		user = entityManager.find(User.class, nickname);
		//recuper l'abilità
		abilita = entityManager.find(Abilita.class, idAbilita);
		
		query = entityManager.createNamedQuery("calcolaValoriReputazione");
		query.setParameter("user", user);
		query.setParameter("abilita", abilita);
		try {
			//recupero i risultati della query: prima componente = numeroFeedback; seconda componente = mediaValutazioni
			Object[] risultatiRicerca =  (Object[]) query.getSingleResult();
			
			numeroFeedback = (Long) risultatiRicerca[0];
			
			if(numeroFeedback != 0 ){
				//posso calcolare la media
				mediaValutazioni = (Double) (risultatiRicerca[1]);
			}
			else{
				mediaValutazioni = 0;
			}
			
			reputazioneAbilita = new ReputazioneAbilita();
			reputazioneAbilita.setNumeroFeedbackRicevuti((int) numeroFeedback );
			reputazioneAbilita.setMediaValutazioniFeedback( (int) mediaValutazioni );
			
			return reputazioneAbilita;			
		} catch (NoResultException e) {
			System.out.println("non ci sono feedback");
			return null;
		}				
		
	}

}
