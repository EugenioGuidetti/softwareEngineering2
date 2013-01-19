package session;

import java.util.List;
import java.util.Set;
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
@RemoteBinding(jndiBinding = "GestoreUserJNDI")
public class GestoreUser implements GestoreUserRemote {

	@PersistenceContext(unitName = "swimv2_unit")
	private EntityManager entityManager;
	
    public GestoreUser() {
    	super();
    }

	@Override
	public User getUser(String nickname) {
		User user = entityManager.find(User.class, nickname);
		return user;
	}

	@Override
	public boolean modificaPassword(String nickname, String password) {
		User user = entityManager.find(User.class, nickname);
		user.setPassword(password);
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
	public boolean modificaEmail(String nickname, String email) {
		User user = entityManager.find(User.class, nickname);
		user.setEmail(email);
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
	public boolean modificaNome(String nickname, String nome) {
		User user = entityManager.find(User.class, nickname);
		user.setNome(nome);
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
	public boolean modificaCognome(String nickname, String cognome) {
		User user = entityManager.find(User.class, nickname);
		user.setCognome(cognome);
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
	public boolean modificaAvatar(String nickname, String avatarPath) {
		User user = entityManager.find(User.class, nickname);
		user.setAvatarPath(avatarPath);
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
	public boolean modificaCitta(String nickname, String citta) {
		User user = entityManager.find(User.class, nickname);
		user.setCitta(citta);
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
	public boolean modificaAnnoNascita(String nickname, int annoNascita) {
		User user = entityManager.find(User.class, nickname);
		user.setAnnoNascita(annoNascita);
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
	public boolean modificaSesso(String nickname, String sesso) {
		User user = entityManager.find(User.class, nickname);
		user.setSesso(sesso);
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
	public boolean modificaAbilitaDichiarate(String nickname, Set<Abilita> abilitaDichiarate) {
		User user = entityManager.find(User.class, nickname);
		user.setAbilitaDichiarate(abilitaDichiarate);
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
	public boolean registra(String nickname, String password, String email, String nome, String cognome, 
								String avatarPath, String citta, String sesso, int annoNascita) {
		User user = new User();
		
		user.setNickname(nickname);
		user.setPassword(password);
		user.setEmail(email);
		user.setNome(nome);
		user.setCognome(cognome);
		user.setAvatarPath(avatarPath);
		user.setCitta(citta);
		user.setSesso(sesso);
		user.setAnnoNascita(annoNascita);
		try {
			entityManager.persist(user);
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
	public boolean elimina(String nickname) {
		User user = entityManager.find(User.class, nickname);
		try {
			entityManager.remove(user);
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

	@SuppressWarnings("unchecked")
	@Override
	public List<User> ricercaPerAbilita(long idAbilita) {
		List<User> userCercati;
		
		Query query = entityManager.createNamedQuery("ricercaPerAbilita");
		query.setParameter("idAbilita", idAbilita);
		
		try {
			userCercati = query.getResultList();
			return userCercati;
		} catch (IllegalStateException e) {
			return null;
		}	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> ricercaPerNome(String nome) {
		List<User> userCercati;

		Query query = entityManager.createNamedQuery("ricercaPerNome");
		query.setParameter("nome", nome);

		try {
			userCercati = query.getResultList();
			return userCercati;
		} catch (IllegalStateException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> ricercaPerCognome(String cognome) {
		List<User> userCercati;

		Query query = entityManager.createNamedQuery("ricercaPerCognome");
		query.setParameter("cognome", cognome);

		try {
			userCercati = query.getResultList();
			return userCercati;
		} catch (IllegalStateException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> ricercaPerNomeCognome(String nome, String cognome) {
		List<User> userCercati;

		Query query = entityManager.createNamedQuery("ricercaPerNomeCognome");
		query.setParameter("nome", nome);
		query.setParameter("cognome", cognome);

		try {
			userCercati = query.getResultList();
			return userCercati;
		} catch (IllegalStateException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> ricercaAmiciPerAbilita(String nickname, long idAbilita) {
		List<User> userCercati;
		User user = entityManager.find(User.class, nickname);

		Query query = entityManager.createNamedQuery("ricercaAmiciPerAbilita");
		query.setParameter("idAbilita", idAbilita);
		query.setParameter("user", user);

		try {
			userCercati = query.getResultList();
			return userCercati;
		} catch (IllegalStateException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> ricercaAmiciPerNome(String nickname, String nome) {
		List<User> userCercati;
		User user = entityManager.find(User.class, nickname);

		Query query = entityManager.createNamedQuery("ricercaAmiciPerNome");
		query.setParameter("nome", nome);
		query.setParameter("user", user);

		try {
			userCercati = query.getResultList();
			return userCercati;
		} catch (IllegalStateException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> ricercaAmiciPerCognome(String nickname, String cognome) {
		User user = entityManager.find(User.class, nickname);
		List<User> userCercati;

		Query query = entityManager.createNamedQuery("ricercaAmiciPerCognome");
		query.setParameter("cognome", cognome);
		query.setParameter("user", user);

		try {
			userCercati = query.getResultList();
			return userCercati;
		} catch (IllegalStateException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> ricercaAmiciPerNomeCognome(String nickname, String nome,
			String cognome) {
		User user = entityManager.find(User.class, nickname);
		List<User> userCercati;

		Query query = entityManager.createNamedQuery("ricercaAmciPerNomeCognome");
		query.setParameter("nome", nome);
		query.setParameter("cognome", cognome);
		query.setParameter("user", user);

		try {
			userCercati = query.getResultList();
			return userCercati;
		} catch (IllegalStateException e) {
			return null;
		}
	}

}
