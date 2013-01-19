package test;

import static org.junit.Assert.*;

import javax.naming.Context;

import org.junit.BeforeClass;
import org.junit.Test;

import session.GestoreAmiciziaRemote;
import session.GestorePropostaAbilitaRemote;
import session.GestoreUserRemote;

public class GestoreAmiciziaTest {
	/*
	 * Definisco il contesto per poter agganciare il test alle session
	 */
	static private Context jndiContext;  
	static private GestoreAmiciziaRemote gestoreAmiciziaRemote;
	static private GestoreUserRemote gestoreUserRemote;

	/*
	 * Definisco il contesto, agganciando i gestori remoti attraverso le lookup
	 * Definisco le condizioni iniziali del database inserendo l'utente admin
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		jndiContext = SupportoTest.getInitialContext();

		Object refAmicizia = jndiContext.lookup("GestoreAmiciziaJNDI");
		gestoreAmiciziaRemote = (GestoreAmiciziaRemote) refAmicizia;

		Object refUser = jndiContext.lookup("GestoreUserJNDI");
		gestoreUserRemote = (GestoreUserRemote) refUser;
	}

	@Test
	public void test(){
		assertEquals(true, true);
	}
}
