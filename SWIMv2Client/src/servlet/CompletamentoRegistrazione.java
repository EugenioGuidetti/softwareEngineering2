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
		try {
			RequestDispatcher dispatcher;
			Context context = new InitialContext();
			GestoreAbilitaRemote gestoreAbilita = (GestoreAbilitaRemote) context.lookup("GestoreAbilitaJNDI");
			GestoreUserRemote gestoreUser = (GestoreUserRemote) context.lookup("GestoreUserJNDI");
			String nickname = (String) request.getAttribute("nickname");
			String[] abilitaScelte = request.getParameterValues("abilitaScelte");			
			Set<Abilita> abilitaDichiarate = new HashSet<Abilita>();
			request.setAttribute("messaggio", Comunicazione.registrazioneCompletata());
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
			dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);
		} catch (NamingException namingE) {
			namingE.printStackTrace();
		}
	}

}
