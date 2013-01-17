package entity;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.*;

@Entity
@Table(name = "amicizia")
public class Amicizia implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "momento_richiesta", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar momentoRichiesta;
	
	@Column(name = "momento_accettazione")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar momentoAccettazione;
	
	@ManyToOne
	@JoinColumn(name = "user_richiedente", referencedColumnName = "nickname", nullable = false)
	private User userRichiedente;
	
	@ManyToOne
	@JoinColumn(name = "user_destinatario", referencedColumnName = "nickname", nullable = false)
	private User userDestinatario;
	
	public Amicizia() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Calendar getMomentoRichiesta() {
		return momentoRichiesta;
	}

	public void setMomentoRichiesta(Calendar momentoRichiesta) {
		this.momentoRichiesta = momentoRichiesta;
	}

	public Calendar getMomentoAccettazione() {
		return momentoAccettazione;
	}

	public void setMomentoAccettazione(Calendar momentoAccettazione) {
		this.momentoAccettazione = momentoAccettazione;
	}

	public User getUserRichiedente() {
		return userRichiedente;
	}

	public void setUserRichiedente(User userRichiedente) {
		this.userRichiedente = userRichiedente;
	}

	public User getUserDestinatario() {
		return userDestinatario;
	}

	public void setUserDestinatario(User userDestinatario) {
		this.userDestinatario = userDestinatario;
	}
	
}
