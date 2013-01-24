package servlet;

import java.io.IOException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import session.GestorePropostaAbilitaRemote;
import utility.Comunicazione;

public class ProponiAbilita extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private RequestDispatcher dispatcher;
	private Context context;
	private GestorePropostaAbilitaRemote gestoreProposta;
	private String nickname;
	private String nomeAbilita;
	private String descrizioneAbilita;

    public ProponiAbilita() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		nickname = (String) request.getSession().getAttribute("nickname");
		nomeAbilita = request.getParameter("nome");
		descrizioneAbilita = request.getParameter("descrizione");
		try {
			context = new InitialContext();
			gestoreProposta = (GestorePropostaAbilitaRemote) context.lookup("GestorePropostaAbilitaJNDI");
			
			if(!gestoreProposta.inviaProposta(nickname, nomeAbilita, descrizioneAbilita)) {
				request.setAttribute("messaggio", Comunicazione.erroreInvioProposta());
				dispatcher = request.getRequestDispatcher("PagineUser/proponiAbilita.jsp");
				dispatcher.forward(request, response);
			} else {
				request.setAttribute("messaggio", Comunicazione.confermaInvioProposta());
				dispatcher = request.getRequestDispatcher("PaginaUser");
				dispatcher.forward(request, response);				
			}
		} catch (NamingException e) {
			request.setAttribute("messaggio", Comunicazione.erroreInvioProposta());
			dispatcher = request.getRequestDispatcher("PagineUser/proponiAbilita.jsp");
			dispatcher.forward(request, response);			
		}
	}

}
