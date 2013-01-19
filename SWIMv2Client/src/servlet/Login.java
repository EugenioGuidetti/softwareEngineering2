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
import session.GestoreProfiloRemote;
import utility.Comunicazione;

public class Login extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final String USER = "user";
	private static final String ADMIN = "admin";
    
    public Login() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher;
		Context contex;
		GestoreProfiloRemote gestoreProfilo;
		String ruolo;
		String nickname = request.getParameter("nickname");
		String password = request.getParameter("password");
		try {
			contex = new InitialContext();
			gestoreProfilo = (GestoreProfiloRemote) contex.lookup("GestoreProfiloJNDI");
			if(!gestoreProfilo.controlloCredenziali(nickname, password)) {
				request.setAttribute("messaggio", Comunicazione.credenzialiNonValide());
				dispatcher = request.getRequestDispatcher("index.jsp");
				dispatcher.forward(request, response);
			} else {
				ruolo = gestoreProfilo.getRuolo(nickname);
				if(ruolo.equals(USER)) {
					request.getSession().setAttribute("nickname", nickname);
					response.sendRedirect("PagineUser/paginaUser.jsp");
				}
				if(ruolo.equals(ADMIN)) {
					request.getSession().setAttribute("nickname", nickname);
					response.sendRedirect("PagineAdmin/paginaAdmin.jsp");
				}
			}
		} catch (NamingException e) {
			request.setAttribute("messaggio", Comunicazione.erroreServlet());
			dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);
		}
	}

}