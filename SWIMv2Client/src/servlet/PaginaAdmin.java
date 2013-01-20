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
import entity.Admin;
import session.GestoreAdminRemote;
import utility.Comunicazione;

public class PaginaAdmin extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

    public PaginaAdmin() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher;
		Context context;
		GestoreAdminRemote gestoreAdmin;
		Admin admin;
		String nickname = (String) request.getSession().getAttribute("nickname");
		try {
			context = new InitialContext();
			gestoreAdmin = (GestoreAdminRemote) context.lookup("GestoreAdminJNDI");
			admin = gestoreAdmin.getAdmin(nickname);
			request.setAttribute("nome", admin.getNome());
			request.setAttribute("cognome", admin.getCognome());
			request.setAttribute("email", admin.getEmail());
			request.setAttribute("avatar", admin.getAvatarPath());
			dispatcher = request.getRequestDispatcher("PagineAdmin/paginaAdmin.jsp");
			dispatcher.forward(request, response);
		} catch (NamingException e) {
			request.setAttribute("messaggio", Comunicazione.erroreServlet());
			request.getSession().invalidate();
			dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
