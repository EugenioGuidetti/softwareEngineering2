package servlet;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import session.GestoreAdminRemote;
import session.GestoreUserRemote;
import utility.Comunicazione;
import utility.Utilita;

/**
 * Servlet implementation class CompletaModificaUser
 */
public class CompletaModificaUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private RequestDispatcher dispatcher;
	private Context context;
	private GestoreUserRemote gestoreUser;
	private Pattern pattern;
	private Matcher matcher;
	private String nickname;
	private String nome;
	private String cognome;
	private String password;
	private String email;
	private String sesso;
	private String citta;
	private String annoNascita;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CompletaModificaUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		nickname = (String) request.getSession().getAttribute("nickname");
		nome = request.getParameter("nNome");
		cognome = request.getParameter("nCognome");
		password = request.getParameter("nPassword");
		email = request.getParameter("nEmail");
		sesso = request.getParameter("nSesso");
		citta = request.getParameter("nCitta");
		annoNascita = request.getParameter("nAnnoNascita");
		
		try {
			context = new InitialContext();
			gestoreUser = (GestoreUserRemote) context.lookup("GestoreUserJNDI");

			//modifica email
			if(!email.equals("")){
				pattern = Pattern.compile(Utilita.EMAIL_PATTERN);
				matcher = pattern.matcher(email);
				if(!matcher.find()) {
					request.setAttribute("messaggio", Comunicazione.emailNonValida());
				} else {
					System.out.println("Sto per provare a modificare la email.");
					if(!gestoreUser.modificaEmail(nickname, email)) {
						request.setAttribute("messaggio", Comunicazione.erroreModificaInformazioni());
					}
				}
			}

			//modifica nome
			if(!nome.equals("")) {
				if(!gestoreUser.modificaNome(nickname, nome)) {
					request.setAttribute("messaggio", Comunicazione.erroreModificaInformazioni());
				}
			}

			//modifica cognome
			if(!cognome.equals("")) {
				if(!gestoreUser.modificaCognome(nickname, cognome)) {
					request.setAttribute("messaggio", Comunicazione.erroreModificaInformazioni());
				}
			}

			//modifica password
			if(!password.equals("")) {
				if(!gestoreUser.modificaPassword(nickname, password)) {
					request.setAttribute("messaggio", Comunicazione.erroreModificaInformazioni());
				}
			}
			
			//modifica sesso
			if(!sesso.equals("")){
				if(!gestoreUser.modificaSesso(nickname, sesso)){
					request.setAttribute("messaggio", Comunicazione.erroreModificaInformazioni());
				}
			}
			
			//modifica citta
			if(!citta.equals("")){
				if(!gestoreUser.modificaCitta(nickname, citta)){
					request.setAttribute("messaggio", Comunicazione.erroreModificaInformazioni());
				}
			}
			
			//modifica anno nascita
			if(!annoNascita.equals("")){
				if(!gestoreUser.modificaAnnoNascita(nickname, Integer.parseInt(annoNascita))){
					request.setAttribute("messaggio", Comunicazione.erroreModificaInformazioni());
				}
			}
			
			
			//modifica dell'avatar
			
			
			
			
			
			
			
			
			
		} catch (Exception e) {
			request.setAttribute("messaggio", Comunicazione.erroreModificaInformazioni());
		} finally {
			dispatcher = request.getRequestDispatcher("PaginaAdmin");
			dispatcher.forward(request, response);
		}





	}

}
