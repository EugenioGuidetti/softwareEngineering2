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
import utility.Utilita;

public class Ricerca extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private RequestDispatcher dispatcher;
	private Context context;
	private GestoreAbilitaRemote gestoreAbilita;

	public Ricerca() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if( Utilita.controlloSessione(request, response)){
			//esiste una sessione utente

			try {
				context = new InitialContext();
				gestoreAbilita = (GestoreAbilitaRemote) context.lookup("GestoreAbilitaJNDI");
				request.setAttribute("abilitaSistema", gestoreAbilita.getAbilitaSistema());
			} catch (NamingException e) {
				request.setAttribute("messaggio", Comunicazione.erroreCaricamentoAbilita());
			} finally {
				request.setAttribute("paginaAttuale", "ricerca");
				dispatcher = request.getRequestDispatcher("PagineUser/ricerca.jsp");
				dispatcher.forward(request, response);			
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
