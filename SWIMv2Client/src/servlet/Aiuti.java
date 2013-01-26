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
import session.GestoreAiutoRemote;
import utility.Comunicazione;
import utility.Utilita;

public class Aiuti extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private RequestDispatcher dispatcher;
	private Context context;
	private GestoreAiutoRemote gestoreAiuto;
	private String nickname;

	public Aiuti() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if( Utilita.controlloSessione(request, response)){
			//esiste una sessione utente

			nickname = (String) request.getSession().getAttribute("nickname");
			try {
				context = new InitialContext();
				gestoreAiuto = (GestoreAiutoRemote) context.lookup("GestoreAiutoJNDI");
				request.setAttribute("aiutiForniti", gestoreAiuto.getAiutiForniti(nickname));
				request.setAttribute("aiutiRicevuti", gestoreAiuto.getAiutiRicevuti(nickname));
			} catch (NamingException e) {
				request.setAttribute("messaggio", Comunicazione.erroreCaricamentoAiuti());
			} finally {
				request.setAttribute("paginaAttuale", "storicoAiuti");
				dispatcher = request.getRequestDispatcher("PagineUser/storicoAiuti.jsp");
				dispatcher.forward(request, response);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
