package session;

import java.util.Calendar;
import javax.ejb.Remote;
import entity.Feedback;

@Remote
public interface GestoreFeedbackRemote {
	
	/**
	 * Il metodo serve per recuperare il feedback associato all'aiuto identificato dall'id passato come parametro
	 * 
	 * @param idAiuto	id dell'aiuto di cui si vuole recuperare il feedback
	 * 
	 * @return		feedback associato all'aiuto identificato dal parametro
	 */
	Feedback getFeedback(long idAiuto);
	
	/**
	 * Il metodo serve per rilasciare il feedback associato ad un aiuto ricevuto
	 * 
	 * @param idAiuto		id dell'aiuto ricevuto
	 * @param valutazioneNumerica	valutazione sintetica del feedback (0-5)
	 * @param valutazioneEstesa		valutazione estesa del feedback
	 * @param momentoRilascio		istante temporale che identifica il rilascio del feedback
	 * 
	 * @return		true, se l'operazione va a buon fine; false, altrimenti.
	 */
	boolean rilascia(long idAiuto, int valutazioneNumerica, String valutazioneEstesa, Calendar momentoRilascio);

}
