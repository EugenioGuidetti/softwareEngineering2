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

public class RichiesteAiuto extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private RequestDispatcher dispatcher;
	private Context context;
	private GestoreAiutoRemote gestoreAiuto;
	private String nickname;

    public RichiesteAiuto() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		nickname = (String) request.getSession().getAttribute("nickname");
		try {
			context = new InitialContext();
			gestoreAiuto = (GestoreAiutoRemote) context.lookup("GestoreAiutoJNDI");
			request.setAttribute("richiesteInviate", gestoreAiuto.getRichiesteInviate(nickname));
			request.setAttribute("richiesteRicevute", gestoreAiuto.getRichiesteRicevute(nickname));
		} catch (NamingException e) {
			request.setAttribute("messaggio", Comunicazione.erroreCaricamentoRichiesteAiuto());
		} finally {
			request.setAttribute("paginaAttuale", "richiesteAiuto");
			dispatcher = request.getRequestDispatcher("PagineUser/richiesteAiuto.jsp");
			dispatcher.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
