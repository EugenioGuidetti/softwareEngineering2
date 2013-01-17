package entity;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;

@NamedQueries( {
	@NamedQuery(
			name = "tutteLeAbilita", query = 
			"SELECT a " +
			"FROM Abilita a " +
			"ORDER BY a.nome"
	),
	@NamedQuery(
			name = "tutteLeAbilitaDiUnoUser", query = 
			"SELECT a " +
			"FROM Abilita a, User u " +
			"WHERE a IN u.abilitaDichiarate " +
			"AND u = :user" +
			"ORDER BY a.nome"
	)
} )


@Entity
@Table(name = "abilita")
public class Abilita implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "nome", nullable = false)
	private String nome;
	
	@Column(name = "descrizione", nullable = false)
	private String descrizione;
	
	@Column(name = "icona_path")
	private String iconaPath;
	
	@ManyToMany(mappedBy = "abilitaDichiarate")
	private Set<User> userDichiaranti;
	
	public Abilita() {
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

	public String getIconaPath() {
		return iconaPath;
	}

	public void setIconaPath(String iconaPath) {
		this.iconaPath = iconaPath;
	}

	public Set<User> getUserDichiaranti() {
		return userDichiaranti;
	}

	public void setUserDichiaranti(Set<User> userDichiaranti) {
		this.userDichiaranti = userDichiaranti;
	}

}
