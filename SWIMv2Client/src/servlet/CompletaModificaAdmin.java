package servlet;

import java.io.IOException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Admin;
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

	private static final long serialVersionUID = 1L;

	public CompletaModificaAdmin() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if( Utilita.controlloSessione(request, response)){
			//esiste una sessione utente

			nickname = (String) request.getSession().getAttribute("nickname");
			nome = request.getParameter("nNome");
			cognome = request.getParameter("nCognome");
			password = request.getParameter("nPassword");

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
						Admin admin = gestoreAdmin.getAdmin(nickname);
						Utilita.sendMail(nickname, admin.getPassword(), admin.getCognome(), admin.getNome(), admin.getEmail(), Utilita.OGGETTO_MAIL_MODIFICA, Utilita.MESSAGGIO_MODIFICA);
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

}
