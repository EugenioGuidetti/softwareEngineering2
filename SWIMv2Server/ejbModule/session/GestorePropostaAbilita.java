package session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import org.jboss.ejb3.annotation.RemoteBinding;
import entity.User;
import entity.PropostaAbilita;

@Stateless
@RemoteBinding(jndiBinding = "GestorePropostaAbilitaJNDI")
public class GestorePropostaAbilita implements GestorePropostaAbilitaRemote {

	@PersistenceContext(unitName = "swimv2_unit")
	private EntityManager entityManager;

    public GestorePropostaAbilita() {
    	super();
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<PropostaAbilita> getProposteNonVisionate() {
    	List<PropostaAbilita> proposteNonVisionate;
    	boolean statoProposta = false;
    	Query query = entityManager.createNamedQuery("proposteAbilitaPerPresaVisione");
    	query.setParameter("boolean", statoProposta);
    	try {
    		proposteNonVisionate = query.getResultList();
    		return proposteNonVisionate;
    	} catch (IllegalStateException e) {
			return null;
		}
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<PropostaAbilita> getProposteVisionate() {
    	List<PropostaAbilita> proposteVisionate;
    	boolean statoProposta = true;
    	Query query = entityManager.createNamedQuery("proposteAbilitaPerPresaVisione");
    	query.setParameter("boolean", statoProposta);
    	try {
    		proposteVisionate = query.getResultList();
    		return proposteVisionate;
    	} catch (IllegalStateException e) {
			return null;
		}
	}

	@Override
	public boolean inviaProposta(String nickname, String nomeAbilita,
			String descrizioneAbilita) {
		boolean presaVisione = false;
		User user = entityManager.find(User.class, nickname);
		PropostaAbilita proposta = new PropostaAbilita();
		proposta.setNome(nomeAbilita);
		proposta.setDescrizione(descrizioneAbilita);
		proposta.setPresaVisione(presaVisione);
		proposta.setUserProponente(user);
		try {
			entityManager.persist(proposta);
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
	public boolean visionaProposta(long id) {
		boolean presaVisione = true;
		PropostaAbilita proposta = entityManager.find(PropostaAbilita.class, id);
		proposta.setPresaVisione(presaVisione);
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
	public boolean cancellaProposta(long id) {
		PropostaAbilita proposta = entityManager.find(PropostaAbilita.class, id);
		try {
			entityManager.remove(proposta);
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
