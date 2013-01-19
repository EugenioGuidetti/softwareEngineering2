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

public class CreaAbilita extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public CreaAbilita() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("PaginaAdmin");
		Context context;
		GestoreAbilitaRemote gestoreAbilita;
		String nome = request.getParameter("nome");
		String descrizione = request.getParameter("descrizione");
		String iconaPath = null;
		try {
			context = new InitialContext();
			gestoreAbilita = (GestoreAbilitaRemote) context.lookup("GestoreAbilitaJNDI");
			gestoreAbilita.crea(nome, descrizione, iconaPath);
			if(gestoreAbilita.crea(nome, descrizione, iconaPath)) {
				request.setAttribute("messaggio", Comunicazione.confermaCreazioneAbilita());
				dispatcher.forward(request, response);
			} else {
				request.setAttribute("messaggio", Comunicazione.erroreCreazioneAbilita());
				dispatcher.forward(request, response);
			}
		} catch (NamingException e) {
			request.setAttribute("messaggio", Comunicazione.erroreCreazioneAbilita());
			dispatcher.forward(request, response);
		}
	}

}
