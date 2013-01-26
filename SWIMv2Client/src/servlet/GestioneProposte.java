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

public class GestioneProposte extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private RequestDispatcher dispatcher;
	private Context context;
	private GestorePropostaAbilitaRemote gestoreProposta;

    public GestioneProposte() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			context = new InitialContext();
			gestoreProposta = (GestorePropostaAbilitaRemote) context.lookup("GestorePropostaAbilitaJNDI");
			request.setAttribute("proposteNonVisionate", gestoreProposta.getProposteNonVisionate());
			request.setAttribute("proposteVisionate", gestoreProposta.getProposteVisionate());
		} catch (NamingException e) {
			request.setAttribute("messaggio", Comunicazione.erroreCaricamentoProposte());
		} finally {
			request.setAttribute("paginaAttuale", "gestioneProposte");
			dispatcher = request.getRequestDispatcher("PagineAdmin/gestioneProposte.jsp");
			dispatcher.forward(request, response);			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
