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

public class GestioneProposte extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

    public GestioneProposte() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher;
		Context context;
		GestorePropostaAbilitaRemote gestorePropostaAbilita;
		try {
			context = new InitialContext();
			gestorePropostaAbilita = (GestorePropostaAbilitaRemote) context.lookup("GestorePropostaAbilitaJNDI");
			request.setAttribute("proposteNonVisionate", gestorePropostaAbilita.getProposteNonVisionate());
			request.setAttribute("proposteVisionate", gestorePropostaAbilita.getProposteVisionate());
			dispatcher = request.getRequestDispatcher("PagineAdmin/gestioneProposte.jsp");
			dispatcher.forward(request, response);
		} catch (NamingException e) {
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
