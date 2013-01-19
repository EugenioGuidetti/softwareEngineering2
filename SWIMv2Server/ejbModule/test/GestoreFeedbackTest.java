package test;

import static org.junit.Assert.*;

import javax.naming.Context;

import org.junit.BeforeClass;
import org.junit.Test;

import session.GestoreFeedbackRemote;
import session.GestorePropostaAbilitaRemote;
import session.GestoreUserRemote;

public class GestoreFeedbackTest {
	/*
	 * Definisco il contesto per poter agganciare il test alle session
	 */
	static private Context jndiContext;  
	static private GestoreFeedbackRemote gestoreFeedbackRemote;
	static private GestoreUserRemote gestoreUserRemote;

	/*
	 * Definisco il contesto, agganciando i gestori remoti attraverso le lookup
	 * Definisco le condizioni iniziali del database inserendo l'utente admin
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		jndiContext = SupportoTest.getInitialContext();

		Object refFeedback = jndiContext.lookup("GestoreFeedbackJNDI");
		gestoreFeedbackRemote = (GestoreFeedbackRemote) refFeedback;

		Object refUser = jndiContext.lookup("GestoreUserJNDI");
		gestoreUserRemote = (GestoreUserRemote) refUser;
	}
	@Test
	public void test(){
		assertEquals(true, true);
	}
}
