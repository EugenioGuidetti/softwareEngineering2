package session;

import java.util.Calendar;
import java.util.List;
import javax.ejb.Remote;
import entity.Amicizia;

@Remote
public interface GestoreAmiciziaRemote {
	
	List<Amicizia> getRichiesteInviate(String nicknameRichiedente);
	
	List<Amicizia> getRichiesteRicevute(String nicknameDestinatario);
	
	boolean inviaRichiesta(String nicknameRichiedente, String nicknameDestinatario, Calendar momentoRichiesta);
	
	boolean accettaRichiesta(long id, Calendar momentoAccettazione);
	
	boolean rimuovi(long id);

}
