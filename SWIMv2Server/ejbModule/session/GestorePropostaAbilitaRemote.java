package session;

import java.util.List;
import javax.ejb.Remote;
import entity.PropostaAbilita;

@Remote
public interface GestorePropostaAbilitaRemote {
	
	/**
	 * Il metodo serve per recuperare tutte le proposte di abilità esistenti nel sistema annotate come proposte "non visionate"
	 * 
	 * @return		lista delle proposte di abilità presenti nel sistema annotate come proposte "non visionate"
	 */
	List<PropostaAbilita> getProposteNonVisionate();
	
	/**
	 * Il metodo serve per recuperare tutte le proposte di abilità esistenti nel sistema annotate come proposte "visionate"
	 * 
	 * @return		lista delle proposte di abilità presenti nel sistema annotate come proposte "visionate"
	 */
	List<PropostaAbilita> getProposteVisionate();
	
	/**
	 * Il metodo serve per inserire una nuova proposta di abilità nel sistema; gli attributi di tale proposta 
	 * sono passati come parametri. Al momento dell'invio la proposta viene automaticamente annotata come "non visionata"
	 * 
	 * @param nickname		dello user proponente
	 * @param nomeAbilita	della nuova abilità che lo user vorrebbe aggiungere
	 * @param descrizioneAbilita	della nuova abilità che lo user vorrebbe aggiungere
	 * 
	 * @return		true, se l'operazione va a buon fine; false, altrimenti
	 */
	boolean inviaProposta(String nickname, String nomeAbilita, String descrizioneAbilita);
	
	/**
	 * Il metodo serve per annotare la proposta, il cui identificativo è passato come parametro, come "visionata"
	 * 
	 * @param id	della proposta di abilità che si vuole annotare come "visionata"
	 * 
	 * @return		true, se l'operazione va a buon fine; false, altrimenti.
	 */
	boolean visionaProposta(long id);
	
	/**
	 * Il metodo serve per eliminare dal sistema la proposta associata all'identificatore passato come parametro 
	 * 
	 * @param id	della proposta di abilità che si vuole eliminare
	 * 
	 * @return		true, se l'operazione va a buon fine; false, altrimenti.
	 */
	boolean cancellaProposta(long id);

}
