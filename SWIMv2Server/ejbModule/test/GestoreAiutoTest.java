package test;

import static org.junit.Assert.*;

import javax.naming.Context;

import org.junit.BeforeClass;
import org.junit.Test;

import session.GestoreAiutoRemote;
import session.GestorePropostaAbilitaRemote;
import session.GestoreUserRemote;

public class GestoreAiutoTest {
	/*
	 * Definisco il contesto per poter agganciare il test alle session
	 */
	static private Context jndiContext;  
	static private GestoreAiutoRemote gestoreAiutoRemote;
	static private GestoreUserRemote gestoreUserRemote;

	/*
	 * Definisco il contesto, agganciando i gestori remoti attraverso le lookup
	 * Definisco le condizioni iniziali del database inserendo l'utente admin
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		jndiContext = SupportoTest.getInitialContext();

		Object refAiuto = jndiContext.lookup("GestoreAiutoJNDI");
		gestoreAiutoRemote = (GestoreAiutoRemote) refAiuto;

		Object refUser = jndiContext.lookup("GestoreUserJNDI");
		gestoreUserRemote = (GestoreUserRemote) refUser;
	}

	@Test
	public void test(){
		assertEquals(true, true);
	}


}
