package session;

import javax.ejb.Remote;

import entity.ReputazioneAbilita;

@Remote
public interface GestoreUtilitiesRemote {

	/**
	 * Il metodo serve per recuperare il numero di feedback e la media delle valutazioni associate a tali feedback che si
	 * riferiscno agli aiuti forniti dallo user associato al nickname passato come parametro riguardanti l'abilità 
	 * identificata dal parametro
	 * 
	 * @param nickname		dello user di cui si vogliono ottenere il numero di feedback e la media delle valutazioni riguardanti i feedback sugli aiuti da lui forniti
	 * @param idAbilita		id dell'abilità riguardante gli aiuti dei quali si vogliono recupare i feedback
	 * 
	 * @return		oggetto reputazioneAbilita contenente le informazioni recuperate
	 */
	ReputazioneAbilita getReputazioneAbilita(String nickname, long idAbilita);
	
	
}
