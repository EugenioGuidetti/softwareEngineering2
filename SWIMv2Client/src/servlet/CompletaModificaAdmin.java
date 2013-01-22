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
import utility.Comunicazione;
import utility.Utilita;

public class CompletaModificaAdmin extends HttpServlet {
	
	private RequestDispatcher dispatcher;
	private Context context;
	private GestoreAdminRemote gestoreAdmin;
	private Pattern pattern;
	private Matcher matcher;
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
		email = request.getParameter("nEmail");
		try {
		context = new InitialContext();
		gestoreAdmin = (GestoreAdminRemote) context.lookup("GestoreAdminJNDI");
		
		//modifica email
		if(!email.equals("")){
			pattern = Pattern.compile(Utilita.EMAIL_PATTERN);
			matcher = pattern.matcher(email);
			if(!matcher.find()) {
				request.setAttribute("messaggio", Comunicazione.emailNonValida());
			} else {
				System.out.println("Sto per provare a modificare la email.");
				if(!gestoreAdmin.modificaEmail(nickname, email)) {
					request.setAttribute("messaggio", Comunicazione.erroreModificaInformazioni());
				}
			}
		}
		
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
		}
		} catch (Exception e) {
			request.setAttribute("messaggio", Comunicazione.erroreModificaInformazioni());
		} finally {
			dispatcher = request.getRequestDispatcher("PaginaAdmin");
			dispatcher.forward(request, response);
		}
	}

}
