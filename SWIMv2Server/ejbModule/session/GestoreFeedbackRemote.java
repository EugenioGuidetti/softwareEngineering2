package session;

import java.util.Calendar;
import javax.ejb.Remote;
import entity.Feedback;

@Remote
public interface GestoreFeedbackRemote {
	
	Feedback getFeedback(long idAiuto);
	
	boolean rilascia(long idAiuto, int valutazioneNumerica, String valutazioneEstesa, Calendar momentoRilascio);

}
