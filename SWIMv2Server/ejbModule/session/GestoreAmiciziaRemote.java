package session;

import java.util.Calendar;
import java.util.List;
import javax.ejb.Remote;
import entity.Amicizia;

@Remote
public interface GestoreAmiciziaRemote {
	
	/**
	 * Il metodo serve per recuperare tutte le richieste di amicizia inviate (in attesa di essere accettate o rifiutate)
	 * dallo user associato al nickname passato come parametro
	 * 
	 * @param nicknameRichiedente	dello user di cui si vogliono recuperare tutte le richieste di amicizia inviate
	 * 
	 * @return		lista delle richieste di amicizia inviate (in attesa di essere accettate o rifiutate)
	 */
	List<Amicizia> getRichiesteInviate(String nicknameRichiedente);
	
	/**
	 * Il metodo serve per recuperare tutte le richieste di amicizia ricevute (in attesa di essere accettate o rifiutate)
	 * dallo user associato al nickname passato come parametro
	 * 
	 * @param nicknameDestinatario	dello user di cui si vogliono recuperare tutte le richieste di amicizia ricevute
	 * 
	 * @return		lista delle richieste di amicizia ricevute (in attesa di essere accettate o rifiutate)
	 */
	List<Amicizia> getRichiesteRicevute(String nicknameDestinatario);
	
	/**
	 * Il metodo serve per recuperare tutti i rapporti di amicizia allacciati dallo user associato al nickname passato
	 * come parametro
	 * 
	 * @param nickname	dello user di cui si vogliono recuperare tutti i rapporti di amicizia
	 * 
	 * @return		lista dei rapporti di amicizia allacciati
	 */
	List<Amicizia> getAmicizieAllacciate(String nickname);
	
	/**
	 * Il metodo serve per inoltrare una richiesta di amicizia tra uno user ed un altro
	 * 
	 * @param nicknameRichiedente	nickname dello user che invia la richiesta di amicizia
	 * @param nicknameDestinatario	nickname dello user a cui è inviata la richiesta di amicizia
	 * @param momentoRichiesta		istante temporale che identifica il momento di invio della richiesta
	 * 
	 * @return		true, se l'operazione va a buon fine; false, altrimenti.
	 */
	boolean inviaRichiesta(String nicknameRichiedente, String nicknameDestinatario, Calendar momentoRichiesta);
	
	/**
	 * Il metodo serve per consentire allo user ricevente della richiesta di amicizia identificata dall'id passato come
	 * parametro, di accettarla
	 * 
	 * @param id		della richiesta di amicizia da accettare
	 * @param momentoAccettazione	istante temporale che identifica il momento di accettazione della richiesta
	 * 
	 * @return		true, se l'operazione va a buon fine; false, altrimenti.
	 */
	boolean accettaRichiesta(long id, Calendar momentoAccettazione);
	
	/**
	 * Il metodo serve per eliminare l'amicizia (richiesta o già allacciata) identificata dall'id passato come parametro
	 * 
	 * @param id		dell'amicizia da eliminare
	 * 
	 * @return		true, se l'operazione va a buon fine; false, altrimenti.
	 */
	boolean rimuovi(long id);

}
