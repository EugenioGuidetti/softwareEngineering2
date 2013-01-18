package entity;

import java.io.Serializable;
import javax.persistence.*;

@NamedQueries( { 
	@NamedQuery(name = "proposteAbilitaPerPresaVisione", query = 
			"SELECT p " +
			"FROM PropostaAbilita p " +
			"WHERE p.presaVisione = :boolean")
} )

@Entity
@Table(name = "proposta_abilita")
public class PropostaAbilita implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "nome", nullable = false)
	private String nome;
	
	@Column(name = "descrizione", nullable = false)
	private String descrizione;
	
	@Column(name = "presa_visione", nullable = false)
	private boolean presaVisione;
	
	@ManyToOne
	@JoinColumn(name = "user_proponente", referencedColumnName = "nickname", nullable = false)
	private User userProponente;

	public PropostaAbilita() {
		super();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public boolean isPresaVisione() {
		return presaVisione;
	}

	public void setPresaVisione(boolean presaVisione) {
		this.presaVisione = presaVisione;
	}

	public User getUserProponente() {
		return userProponente;
	}

	public void setUserProponente(User userProponente) {
		this.userProponente = userProponente;
	}
	
}
