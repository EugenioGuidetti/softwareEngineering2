package entity;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "feedback")
public class Feedback implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id_aiuto")
	@GeneratedValue(generator = "foreignKey")
	@GenericGenerator(
			name = "foreignKey", 
			strategy = "foreign", 
			parameters = {
					@Parameter(name = "property", value ="aiutoValutato")
			}
	)
	private long id;
	
	@Column(name = "valutazione_numerica", nullable = false)
	private int valutazioneNumerica;
	
	@Column(name = "valutazione_estesa", nullable = false)
	private String valutazioenEstesa;
	
	@Column(name = "momento_rilascio", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar momentoRilascio;
	
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Aiuto aiutoValutato;
	
	public Feedback() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getValutazioneNumerica() {
		return valutazioneNumerica;
	}

	public void setValutazioneNumerica(int valutazioneNumerica) {
		this.valutazioneNumerica = valutazioneNumerica;
	}

	public String getValutazioenEstesa() {
		return valutazioenEstesa;
	}

	public void setValutazioenEstesa(String valutazioenEstesa) {
		this.valutazioenEstesa = valutazioenEstesa;
	}

	public Calendar getMomentoRilascio() {
		return momentoRilascio;
	}

	public void setMomentoRilascio(Calendar momentoRilascio) {
		this.momentoRilascio = momentoRilascio;
	}

	public Aiuto getAiutoValutato() {
		return aiutoValutato;
	}

	public void setAiutoValutato(Aiuto aiutoValutato) {
		this.aiutoValutato = aiutoValutato;
	}

}
