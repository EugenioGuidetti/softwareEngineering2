package entity;

import java.io.Serializable;

public class ReputazioneAbilita implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int numeroFeedbackRicevuti;
	private int mediaValutazioniFeedback;
	
	public ReputazioneAbilita() {
		this.numeroFeedbackRicevuti = 0;
		this.mediaValutazioniFeedback = 0;
	}
		
	
	public int getNumeroFeedbackRicevuti() {
		return numeroFeedbackRicevuti;
	}
	public void setNumeroFeedbackRicevuti(int numeroFeedbackRicevuti) {
		this.numeroFeedbackRicevuti = numeroFeedbackRicevuti;
	}
	public int getMediaValutazioniFeedback() {
		return mediaValutazioniFeedback;
	}
	public void setMediaValutazioniFeedback(int mediaValutazioniFeedback) {
		this.mediaValutazioniFeedback = mediaValutazioniFeedback;
	}

}
