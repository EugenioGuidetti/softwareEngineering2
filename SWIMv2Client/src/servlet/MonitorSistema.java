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

	private RequestDispatcher dispatcher;
	private Context context;
	private GestoreAbilitaRemote gestoreAbilita;
	private GestoreUserRemote gestoreUser;

    public MonitorSistema() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			context = new InitialContext();
			gestoreAbilita = (GestoreAbilitaRemote) context.lookup("GestoreAbilitaJNDI");
			gestoreUser = (GestoreUserRemote) context.lookup("GestoreUserJNDI");
			request.setAttribute("abilitaSistema", gestoreAbilita.getAbilitaSistema());
			request.setAttribute("userSistema", gestoreUser.getUserSistema());
		} catch (NamingException e) {
			request.setAttribute("messaggio", Comunicazione.erroreCaricamentoMonitor());
		} finally {
			request.setAttribute("paginaAttuale", "monitorSistema");
			dispatcher = request.getRequestDispatcher("PagineAdmin/monitorSistema.jsp");
			dispatcher.forward(request, response);			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
