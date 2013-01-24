package servlet;

import java.io.IOException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import session.GestoreAdminRemote;
import utility.Comunicazione;
import utility.Utilita;

public class CompletaModificaAdmin extends HttpServlet {
	
	private RequestDispatcher dispatcher;
	private Context context;
	private GestoreAdminRemote gestoreAdmin;
	private String nickname;
	private String nome;
	private String cognome;
	private String password;
	private String email;
	
	private static final long serialVersionUID = 1L;

    public CompletaModificaAdmin() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		nickname = (String) request.getSession().getAttribute("nickname");
		nome = request.getParameter("nNome");
		cognome = request.getParameter("nCognome");
		password = request.getParameter("nPassword");
		email = Utilita.EMAIL_ADMIN;
		
		try {
		context = new InitialContext();
		gestoreAdmin = (GestoreAdminRemote) context.lookup("GestoreAdminJNDI");
		
		//modifica nome
		if(!nome.equals("")) {
			if(!gestoreAdmin.modificaNome(nickname, nome)) {
				request.setAttribute("messaggio", Comunicazione.erroreModificaInformazioni());
			}
		}
		
		//modifica cognome
		if(!cognome.equals("")) {
			if(!gestoreAdmin.modificaCognome(nickname, cognome)) {
				request.setAttribute("messaggio", Comunicazione.erroreModificaInformazioni());
			}
		}
		
		//modifica password
		if(!password.equals("")) {
			if(!gestoreAdmin.modificaPassword(nickname, password)) {
				request.setAttribute("messaggio", Comunicazione.erroreModificaInformazioni());
			}
			else{
				//invio la mail di conferma
				Utilita.sendMail(nickname, password, cognome, nome, email, Utilita.OGGETTO_MAIL_MODIFICA, Utilita.MESSAGGIO_MODIFICA);
				request.setAttribute("messaggio", Comunicazione.confermaModificaInformazioni());
			}
		}
		} catch (Exception e) {
			request.setAttribute("messaggio", Comunicazione.erroreModificaInformazioni());
		} finally {
			dispatcher = request.getRequestDispatcher("PaginaAdmin");
			dispatcher.forward(request, response);
		}
	}

}
