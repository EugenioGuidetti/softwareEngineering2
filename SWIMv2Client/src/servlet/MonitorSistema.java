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
import session.GestoreUserRemote;
import utility.Comunicazione;

public class MonitorSistema extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

    public MonitorSistema() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher;
		Context context;
		GestoreAbilitaRemote gestoreAbilita;
		GestoreUserRemote gestoreUser;
		try {
			context = new InitialContext();
			gestoreAbilita = (GestoreAbilitaRemote) context.list("GestoreAbilitaJNDI");
			gestoreUser = (GestoreUserRemote) context.list("GestoreUserJNDI");
			request.setAttribute("abilitaSistema", gestoreAbilita.getAbilitaSistema());
			request.setAttribute("userSistema", gestoreUser.getUserSistema());
			dispatcher = request.getRequestDispatcher("PagineAdmin/monitorSistema.jsp");
			dispatcher.forward(request, response);
		} catch (NamingException e) {
			request.setAttribute("messaggio", Comunicazione.erroreCaricamentoMonitor());
			dispatcher = request.getRequestDispatcher("PagineAdmin/monitorSistema.jsp");
			dispatcher.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
