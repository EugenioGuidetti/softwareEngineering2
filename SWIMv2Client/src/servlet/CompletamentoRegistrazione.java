package servlet;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entity.Abilita;
import session.GestoreAbilitaRemote;
import session.GestoreUserRemote;
import utility.Comunicazione;

public class CompletamentoRegistrazione extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    public CompletamentoRegistrazione() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher;
		Context context;
		GestoreUserRemote gestoreUser;
		GestoreAbilitaRemote gestoreAbilita;
		String nickname = (String) request.getAttribute("nickname");
		String[] abilitaScelte = request.getParameterValues("abilitaScelte");		
		Set<Abilita> abilitaDichiarate = new HashSet<Abilita>();	
		try { 
			context = new InitialContext(); 
			gestoreAbilita = (GestoreAbilitaRemote) context.lookup("GestoreAbilitaJNDI");
			gestoreUser = (GestoreUserRemote) context.lookup("GestoreUserJNDI");
			if(abilitaScelte != null) {
				for(String abilitaScelta: abilitaScelte) {
					try {
						long id = Long.parseLong(abilitaScelta);
						abilitaDichiarate.add(gestoreAbilita.getAbilita(id));
					} catch (NumberFormatException numberFormatE) {
						numberFormatE.printStackTrace();
					}
				}
				gestoreUser.modificaAbilitaDichiarate(nickname, abilitaDichiarate);
			}
			request.setAttribute("messaggio", Comunicazione.registrazioneCompletata());
			dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);
		} catch (NamingException namingE) {
			namingE.printStackTrace();
		}
	}

}
