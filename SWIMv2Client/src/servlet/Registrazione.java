package servlet;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import session.GestoreAbilitaRemote;
import session.GestoreProfiloRemote;
import session.GestoreUserRemote;
import utility.Comunicazione;
import utility.Utilita;

public class Registrazione extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
    public Registrazione() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher;
		Context context;
		GestoreProfiloRemote gestoreProfilo;
		GestoreUserRemote gestoreUser;
		GestoreAbilitaRemote gestoreAbilita;
		String nome = (String) request.getParameter("rNome");
		String cognome = (String) request.getParameter("rCognome");
		String sesso = (String) request.getParameter("rSesso");
		int annoNascita = Integer.parseInt(request.getParameter("rAnnoNascita"));
		String citta = (String) request.getParameter("rCitta");
		String nickname = (String) request.getParameter("rNickname");
		String password = (String) request.getParameter("rPassword");
		String email = (String) request.getParameter("rEmail");
		String avatar = Utilita.USER_DEFAULT_AVATAR;
		try {
			context = new InitialContext();
			gestoreProfilo = (GestoreProfiloRemote) context.lookup("GestoreProfiloJNDI");
			gestoreUser = (GestoreUserRemote) context.lookup("GestoreUserJNDI");
			gestoreAbilita = (GestoreAbilitaRemote) context.lookup("GestoreAbilitaJNDI");			
			Pattern pattern = Pattern.compile(Utilita.EMAIL_PATTERN);
			Matcher matcher = pattern.matcher(email);
			if(!matcher.find()) {
				request.setAttribute("messaggio", Comunicazione.emailNonValida());
				dispatcher = request.getRequestDispatcher("index.jsp");
				dispatcher.forward(request, response);
			} else if(!gestoreProfilo.disponibilitaNickname(nickname)) {
				request.setAttribute("messaggio", Comunicazione.nicknameNonDisponibile());
				dispatcher = request.getRequestDispatcher("index.jsp");
				dispatcher.forward(request, response);
			} else {
				gestoreUser.registra(nickname, password, email, nome, cognome, avatar, citta, sesso, annoNascita);
				request.getSession().setAttribute("nickname", nickname);
				request.setAttribute("abilitaSistema", gestoreAbilita.getAbilitaSistema());
				dispatcher = request.getRequestDispatcher("registrazione.jsp");
				dispatcher.forward(request, response);
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
}
