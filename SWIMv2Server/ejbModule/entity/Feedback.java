package entity;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "feedback")
@GenericGenerator(
			name = "aiuto_foreignKey",
			strategy = "foreign",
			parameters = { @Parameter( name = "property", value = "aiutoValutato") }
			)
public class Feedback implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "aiuto_foreignKey")
	@Column(name = "id_aiuto")
	private long id;
	
	@Column(name = "valutazione_numerica", nullable = false)
	private int valutazioneNumerica;
	
	@Column(name = "valutazione_estesa", nullable = false)
	private String valutazioneEstesa;
	
	@Column(name = "momento_rilascio", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar momentoRilascio;
	
	@OneToOne
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
		return valutazioneEstesa;
	}

	public void setValutazioenEstesa(String valutazioenEstesa) {
		this.valutazioneEstesa = valutazioenEstesa;
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
