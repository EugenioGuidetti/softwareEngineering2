package session;

import java.util.Calendar;
import java.util.List;
import javax.ejb.Remote;
import entity.Aiuto;

@Remote
public interface GestoreAiutoRemote {
	
	Aiuto getAiuto(long id);
	
	/**
	 * Il metodo serve per recuperare tutte le richieste di aiuto inviate (in attesa di essere accettate o rifiutate)
	 * dallo user associato al nickname passato come parametro
	 * 
	 * @param nicknameRichiedente	dello user di cui si vogliono recuperare tutte le richieste di aiuto inviate
	 * 
	 * @return		lista delle richieste di aiuto inviate (in attesa di essere accettate o rifiutate)
	 */
	List<Aiuto> getRichiesteInviate(String nicknameRichiedente);
	
	/**
	 * Il metodo serve per recuperare tutte le richieste di aiuto ricevute (in attesa di essere accettate o rifiutate)
	 * dallo user associato al nickname passato come parametro
	 * 
	 * @param nicknameDestinatario	dello user di cui si vogliono recuperare tutte le richieste di aiuto ricevute
	 * 
	 * @return		lista delle richieste di aiuto ricevute (in attesa di essere accettate o rifiutate)
	 */
	List<Aiuto> getRichiesteRicevute(String nicknameDestinatario);
	
	/**
	 * Il metodo serve per recuperare l'insieme degli aiuti ricevuti e forniti dallo user associato al nickname
	 * passato come parametro
	 * 
	 * @param nickname		dello user di cui si vogliono recuperare tutti gli aiuti ricevuti e forniti
	 * 
	 * @return		lista degli aiuti ricevuti e forniti (corrispondenti a richieste accettate)
	 */
	List<Aiuto> getAiutiRicevutiEForniti(String nickname);
	
	/**
	 * Il metodo serve per recuperare tutti gli aiuti ricevuti dallo user associato al nickname passato come
	 * parametro (aiuti ricevuti = richieste di aiuto inviate dallo user che sono state accettate)
	 * 
	 * @param nickname		dello user di cui vogliono recuperare tutti gli aiuti ricevuti
	 * 
	 * @return		lista degli aiuti ricevuti
	 */
	List<Aiuto> getAiutiRicevuti(String nickname);
	
	/**
	 * Il metodo serve per recuperare tutti gli aiuti forniti dallo user associato al nickname passato come
	 * parametro (aiuti forniti = richieste di aiuto inviate allo user che sono state accettate)
	 * 
	 * @param nickname		dello user di cui vogliono recuperare tutti gli aiuti forniti
	 * 
	 * @return		lista degli aiuti ricevuti
	 */
	List<Aiuto> getAiutiForniti(String nickname);
	
	/**
	 * Il metodo serve per inoltrare una richiesta di aiuto tra uno user ed un altro
	 * 
	 * @param nicknameRichiedente	nickname dello user che invia la richiesta di aiuto
	 * @param nicknameDestinatario	nickname dello user a cui è inviata la richiesta di aiuto
	 * @param idAbilitaRichiesta	identificatore dell'abilità in cui si richiede aiuto
	 * @param descrizione			breve descrizione sull'aiuto richiesto
	 * @param momentoRichiesta		istante temporale che identifica il momento di invio della richiesta
	 * 
	 * @return		true, se l'operazione va a buon fine; false, altrimenti.
	 */
	boolean inviaRichiesta(String nicknameRichiedente, String nicknameDestinatario, long idAbilitaRichiesta, String descrizione, Calendar momentoRichiesta);
	
	/**
	 * Il metodo serve per consentire allo user ricevente della richiesta di aiuto identificata dall'id passato come
	 * parametro, di accettarla
	 * 
	 * @param id		della richiesta di aito da accettare
	 * @param momentoAccettazione	istante temporale che identifica il momento di accettazione della richiesta
	 * 
	 * @return		true, se l'operazione va a buon fine; false, altrimenti.
	 */
	boolean accettaRichiesta(long id, Calendar momentoAccettazione);
	
	/**
	 * Il metodo serve per eliminare la richiesta di aiuto identificata dall'id passato come parametro (rifiutare una richiesta)
	 * 
	 * @param id		della richiesta di aiuto da eliminare
	 * 
	 * @return		true, se l'operazione va a buon fine; false, altrimenti.
	 */
	boolean rifiutaRichiesta(long id);

}
