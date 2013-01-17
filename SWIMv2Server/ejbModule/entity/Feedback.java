package entity;

import java.io.Serializable;
import java.util.GregorianCalendar;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "feedback")
public class Feedback implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id_aiuto")
	@GeneratedValue(generator = "foreign")
	@GenericGenerator(
			name = "foreign", 
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
	private GregorianCalendar momentoRilascio;
	
	@OneToOne(mappedBy = "feedbackRicevuto")
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

	public GregorianCalendar getMomentoRilascio() {
		return momentoRilascio;
	}

	public void setMomentoRilascio(GregorianCalendar momentoRilascio) {
		this.momentoRilascio = momentoRilascio;
	}

	public Aiuto getAiutoValutato() {
		return aiutoValutato;
	}

	public void setAiutoValutato(Aiuto aiutoValutato) {
		this.aiutoValutato = aiutoValutato;
	}

}
