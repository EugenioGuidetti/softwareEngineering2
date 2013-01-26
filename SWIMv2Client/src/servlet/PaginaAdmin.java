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
	
	private RequestDispatcher dispatcher;
	private Context context;
	private GestoreAdminRemote gestoreAdmin;
	private Admin admin;
	private String nickname;

    public PaginaAdmin() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		nickname = (String) request.getSession().getAttribute("nickname");
		try {
			context = new InitialContext();
			gestoreAdmin = (GestoreAdminRemote) context.lookup("GestoreAdminJNDI");
			admin = gestoreAdmin.getAdmin(nickname);
			request.setAttribute("nomeCompleto", admin.getNome() + " " + admin.getCognome());
			request.setAttribute("email", admin.getEmail());
			request.setAttribute("avatar", admin.getAvatarPath());
		} catch (NamingException e) {
			request.setAttribute("messaggio", Comunicazione.erroreCaricamentoInformazioni());
		} finally {
			request.setAttribute("paginaAttuale", "paginaAdmin");
			dispatcher = request.getRequestDispatcher("PagineAdmin/paginaAdmin.jsp");
			dispatcher.forward(request, response);			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
