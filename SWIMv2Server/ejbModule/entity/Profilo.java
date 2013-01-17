package entity;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ruolo", discriminatorType = DiscriminatorType.STRING)
@Table(name = "profilo")
public abstract class Profilo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "nickname")
	private String nickname;

	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "nome", nullable = false)
	private String nome;
	
	@Column(name = "cognome", nullable = false)
	private String cognome;
	
	@Column(name = "avatar_path")
	private String avatarPath;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getAvatarPath() {
		return avatarPath;
	}

	public void setAvatarPath(String avatarPath) {
		this.avatarPath = avatarPath;
	}
}
