package session;

import java.util.List;
import javax.ejb.Remote;
import entity.PropostaAbilita;

@Remote
public interface GestorePropostaAbilitaRemote {
	
	List<PropostaAbilita> getProposteNonVisionate();
	
	List<PropostaAbilita> getProposteVisionate();
	
	boolean inviaProposta(String nickname, String nomeAbilita, String descrizioneAbilita);
	
	boolean visionaProposta(long id);
	
	boolean cancellaProposta(long id);

}
