package session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TransactionRequiredException;

import org.jboss.ejb3.annotation.RemoteBinding;
import entity.Admin;

@Stateless
@RemoteBinding(jndiBinding = "GestoreAdminJNDI")
public class GestoreAdmin implements GestoreAdminRemote {

	@PersistenceContext(unitName = "swimv2_unit")
	private EntityManager entityManager;
	
    public GestoreAdmin() {
    	super();
    }

    @Override
    public Admin getAdmin(String nickname) {
		Admin admin = entityManager.find(Admin.class, nickname);
    	return admin;
    }
    
	@Override
	public boolean modificaPassword(String nickname, String password) {
		Admin admin = entityManager.find(Admin.class, nickname);
		admin.setPassword(password);
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
		Admin admin = entityManager.find(Admin.class, nickname);
		admin.setEmail(email);
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
		Admin admin = entityManager.find(Admin.class, nickname);
		admin.setNome(nome);
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
		Admin admin = entityManager.find(Admin.class, nickname);
		admin.setCognome(cognome);
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
	public boolean creaAdmin(String nickname, String password, String email, String nome, String cognome, String avatarPath) {
		Admin admin = new Admin();
		
		admin.setNickname(nickname);
		admin.setPassword(password);
		admin.setEmail(email);
		admin.setNome(nome);
		admin.setCognome(cognome);
		admin.setAvatarPath(avatarPath);
		
		try {
			entityManager.persist(admin);
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
	public boolean rimuoviAdmin(String nickname) {
		Admin admin = entityManager.find(Admin.class, nickname);
		try {
			entityManager.remove(admin);
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
