package test;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class SupportoTest {

	/**
     * Tale metodo server per ottenere il Context da utilizzare per riuscire ad ottenere i riferimenti ai gestori remoti
     * definiti nel packge session
     * 
     * @return Context utilizzato per la lookup
     * @throws NamingException Eccezione lanciata nel caso il metodo non riesca ad ottenere il Context
     */
    public static Context getInitialContext() throws NamingException {
           
            Hashtable<String,String> env = new Hashtable<String,String>();
   
            env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
            env.put(Context.PROVIDER_URL, "localhost:1099");        
            return new InitialContext(env);        
    }
	
}
