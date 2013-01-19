package entity;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;

@NamedQueries( {
	@NamedQuery(name = "amiciUser", query = 
			"SELECT a.userDestinatario " +
			"FROM Amicizia a " +
			"WHERE a.userRichiedente = :user " +
			"AND momentoAccettazione IS NOT NULL " +
			"ORDER BY a.userDestinatario"),
			
	@NamedQuery( name = "ricercaPerAbilita", query = 
			"SELECT u " +
			"FROM Abilita a, User u " +
			"WHERE a.id = :idAbilita " +
			"AND u IN a.userDichiaranti " +
			"ORDER BY u.nome"),
	
	@NamedQuery( name = "ricercaPerNome", query = 
			"SELECT u " +
			"FROM User u " +
			"WHERE u.nome = :nome " +
			"ORDER BY u.cognome"),
			
	@NamedQuery( name = "ricercaPerCognome", query = 
			"SELECT u " +
			"FROM User u " +
			"WHERE u.cognome = :cognome " +
			"ORDER BY u.nome"),
			
	@NamedQuery( name = "ricercaPerNomeCognome", query = 
			"SELECT u " +
			"FROM User u " +
			"WHERE u.nome = :nome " +
			"OR u.cognome = :cognome " +
			"ORDER BY u.cognome, u.nome"),
			
	@NamedQuery( name = "ricercaAmiciPerAbilita", query = 
			"SELECT u " +
			"FROM Abilita a, User u " +
			"WHERE a.id = :idAbilita " +
			"AND u IN a.userDichiaranti " +
			"AND u IN (" +
				"SELECT a.userDestinatario " +
				"FROM Amicizia a " +
				"WHERE a.userRichiedente = :user " +
				"AND momentoAccettazione IS NOT NULL)" +
			"ORDER BY u.nome"),
			
	@NamedQuery( name = "ricercaAmiciPerNome", query = 
			"SELECT u " +
			"FROM User u " +
			"WHERE u.nome = :nome " +
			"AND u IN (" +
				"SELECT a.userDestinatario " +
				"FROM Amicizia a " +
				"WHERE a.userRichiedente = :user " +
				"AND momentoAccettazione IS NOT NULL)" +
			"ORDER BY u.cognome"),
			

	@NamedQuery( name = "ricercaAmiciPerCognome", query = 
			"SELECT u " +
			"FROM User u " +
			"WHERE u.cognome = :cognome " +
			"AND u IN (" +
				"SELECT a.userDestinatario " +
				"FROM Amicizia a " +
				"WHERE a.userRichiedente = :user " +
				"AND momentoAccettazione IS NOT NULL)" +
			"ORDER BY u.nome"),
	
	@NamedQuery( name = "ricercaAmiciPerNomeCognome", query = 
			"SELECT u " +
			"FROM User u " +
			"WHERE (u.nome = :nome OR u.cognome = :cognome ) " +
			"AND u IN (" +
				"SELECT a.userDestinatario " +
				"FROM Amicizia a " +
				"WHERE a.userRichiedente = :user " +
				"AND momentoAccettazione IS NOT NULL)" +
			"ORDER BY u.cognome, u.nome"),
	
} )

@Entity
@DiscriminatorValue("user")
public class User extends Profilo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Column(name = "citta")
	private String citta;

	@Column(name = "sesso")
	private String sesso;

	@Column(name = "anno_nascita")
	private int annoNascita;
	
	@ManyToMany
	@JoinTable(name = "dichiarazione", 
		joinColumns = @JoinColumn(name = "user_dichiarante", referencedColumnName = "nickname"), 
		inverseJoinColumns = @JoinColumn(name = "abilita_dichiarata", referencedColumnName = "id") )
	private Set<Abilita> abilitaDichiarate;
	
	@OneToMany(mappedBy = "userRichiedente")
	private Set<Amicizia> amicizieRichieste;
	
	@OneToMany(mappedBy = "userDestinatario")
	private Set<Amicizia> amicizieRicevute;
	
	@OneToMany(mappedBy = "userRichiedente")
	private Set<Aiuto> aiutiRichiesti;
	
	@OneToMany(mappedBy = "userDestinatario")
	private Set<Aiuto> aiutiForniti;
	
	@OneToMany(mappedBy = "userProponente")
	private Set<PropostaAbilita> proposteInviate;
	
	public User() {
		super();
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public int getAnnoNascita() {
		return annoNascita;
	}

	public void setAnnoNascita(int annoNascita) {
		this.annoNascita = annoNascita;
	}

	public Set<Abilita> getAbilitaDichiarate() {
		return abilitaDichiarate;
	}

	public void setAbilitaDichiarate(Set<Abilita> abilitaDichiarate) {
		this.abilitaDichiarate = abilitaDichiarate;
	}

	public Set<Amicizia> getAmicizieInviate() {
		return amicizieRichieste;
	}

	public void setAmicizieInviate(Set<Amicizia> amicizieInviate) {
		this.amicizieRichieste = amicizieInviate;
	}

	public Set<Amicizia> getAmicizieRicevute() {
		return amicizieRicevute;
	}

	public void setAmicizieRicevute(Set<Amicizia> amicizieRicevute) {
		this.amicizieRicevute = amicizieRicevute;
	}

	public Set<Aiuto> getAiutiRichiesti() {
		return aiutiRichiesti;
	}

	public void setAiutiRichiesti(Set<Aiuto> aiutiRichiesti) {
		this.aiutiRichiesti = aiutiRichiesti;
	}

	public Set<Aiuto> getAiutiForniti() {
		return aiutiForniti;
	}

	public void setAiutiForniti(Set<Aiuto> aiutiForniti) {
		this.aiutiForniti = aiutiForniti;
	}

	public Set<PropostaAbilita> getProposteInviate() {
		return proposteInviate;
	}

	public void setProposteInviate(Set<PropostaAbilita> proposteInviate) {
		this.proposteInviate = proposteInviate;
	}

}
