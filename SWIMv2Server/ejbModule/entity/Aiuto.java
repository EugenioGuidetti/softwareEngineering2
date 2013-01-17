package entity;

import java.io.Serializable;
import java.util.GregorianCalendar;
import javax.persistence.*;

@Entity
@Table(name = "aiuto")
public class Aiuto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "descrizione", nullable = false)
	private String descrizione;
	
	@Column(name = "momento_richiesta", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private GregorianCalendar momentoRichiesta;
	
	@Column(name = "momento_accettazione")
	@Temporal(TemporalType.TIMESTAMP)
	private GregorianCalendar momentoAccettazione;
	
	@ManyToOne
	@JoinColumn(name = "abilita_richiesta", referencedColumnName = "id", nullable = false)
	private Abilita abilitaRichiesta;
	
	@ManyToOne
	@JoinColumn(name = "user_richiedente", referencedColumnName = "nickname", nullable = false)
	private User userRichiedente;
	
	@ManyToOne
	@JoinColumn(name = "user_destinatario", referencedColumnName = "nickname", nullable = false)
	private User userDestinatario;
	
	@OneToOne
	@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id_aiuto")
	private Feedback feedRicevuto;
	
	public Aiuto() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public GregorianCalendar getMomentoRichiesta() {
		return momentoRichiesta;
	}

	public void setMomentoRichiesta(GregorianCalendar momentoRichiesta) {
		this.momentoRichiesta = momentoRichiesta;
	}

	public GregorianCalendar getMomentoAccettazione() {
		return momentoAccettazione;
	}

	public void setMomentoAccettazione(GregorianCalendar momentoAccettazione) {
		this.momentoAccettazione = momentoAccettazione;
	}

	public Abilita getAbilitaRichiesta() {
		return abilitaRichiesta;
	}

	public void setAbilitaRichiesta(Abilita abilitaRichiesta) {
		this.abilitaRichiesta = abilitaRichiesta;
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

	public Feedback getFeedRicevuto() {
		return feedRicevuto;
	}

	public void setFeedRicevuto(Feedback feedRicevuto) {
		this.feedRicevuto = feedRicevuto;
	}

}
