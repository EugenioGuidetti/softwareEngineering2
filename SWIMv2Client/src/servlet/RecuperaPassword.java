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
import entity.User;
import session.GestoreUserRemote;
import utility.Comunicazione;
import utility.Utilita;

/**
 * Servlet implementation class RecuperaPassword
 */
public class RecuperaPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Context context;
	private RequestDispatcher dispatcher;
	private GestoreUserRemote gestoreUser;
	private String nickname;


	public RecuperaPassword() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		boolean inviaMail = false;

		//recupero il nickname
		nickname = request.getParameter("nickname");

		try {
			context = new InitialContext();
			gestoreUser = (GestoreUserRemote) context.lookup("GestoreUserJNDI");
		
		//recuper lo user
		User user = gestoreUser.getUser(nickname);

		if(user == null){
			//non esiste nessuno user con quel nickname
			request.setAttribute("messaggio", Comunicazione.erroreUserInesistente());
		}
		else{

			//genero una nuova password
			String nuovaPassword = Utilita.generatePassword();
			

			//setto un nuova password
			if(!gestoreUser.modificaPassword(nickname, nuovaPassword)) {
				request.setAttribute("messaggio", Comunicazione.erroreModificaInformazioni());
			}
			else{
				request.setAttribute("messaggio", Comunicazione.confermaResetPassword());
				System.out.println("nuova password: " + nuovaPassword);
				inviaMail = true;
			}

			//invio la mail di modifica
			if(inviaMail){
				Utilita.sendMail(nickname, nuovaPassword, user.getCognome(), user.getNome(), user.getEmail(), Utilita.OGGETTO_MAIL_MODIFICA, Utilita.MESSAGGIO_MODIFICA);
			}
		}
		dispatcher = request.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);
		} catch (NamingException e) {
			request.setAttribute("messaggio", Comunicazione.erroreServlet());
			dispatcher = request.getRequestDispatcher("recuperaPassword.jsp");
			dispatcher.forward(request, response);	
		}
	}
}