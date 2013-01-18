package session;

import java.util.Calendar;
import java.util.List;
import javax.ejb.Remote;
import entity.Aiuto;

@Remote
public interface GestoreAiutoRemote {
	
	List<Aiuto> getRichiesteInviate(String nicknameRichiedente);
	
	List<Aiuto> getRichiesteRicevute(String nicknameDestinatario);
	
	List<Aiuto> getAiutiRicevutiEForniti(String nickname);
	
	boolean inviaRichiesta(String nicknameRichiedente, String nicknameDestinatario, long idAbilitaRichiesta, String descrizione, Calendar momentoRichiesta);
	
	boolean accettaRichiesta(long id, Calendar momentoAccettazione);
	
	boolean rifiutaRichiesta(long id);

}
