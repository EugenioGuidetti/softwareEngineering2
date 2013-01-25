package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ GestoreAbilitaTest.class, GestoreAdminTest.class,
		GestoreAiutoTest.class, GestoreAmiciziaTest.class,
		GestoreFeedbackTest.class, GestoreProfiloTest.class,
		GestorePropostaAbilitaTest.class, GestoreUserTest.class, GestoreUtilitiesTest.class })
public class AllTests {

}
