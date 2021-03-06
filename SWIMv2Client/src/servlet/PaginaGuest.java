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
import session.GestoreAbilitaRemote;
import utility.Comunicazione;

public class PaginaGuest extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private RequestDispatcher dispatcher;
	private Context context;
	private GestoreAbilitaRemote gestoreAbilita;

    public PaginaGuest() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			context = new InitialContext();
			gestoreAbilita = (GestoreAbilitaRemote) context.lookup("GestoreAbilitaJNDI");
			request.setAttribute("abilitaSistema", gestoreAbilita.getAbilitaSistema());
		} catch (NamingException e) {
			request.setAttribute("messaggio", Comunicazione.erroreCaricamentoAbilita());
		} finally {
			dispatcher = request.getRequestDispatcher("PagineGuest/paginaGuest.jsp");
			dispatcher.forward(request, response);			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
