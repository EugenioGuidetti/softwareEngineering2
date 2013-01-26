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

	private RequestDispatcher dispatcher;
	private Context context;
	private GestoreProfiloRemote gestoreProfilo;
	private GestoreUserRemote gestoreUser;
	private GestoreAbilitaRemote gestoreAbilita;
	private Pattern pattern;
	private Matcher matcher;
	private String nome;
	private String cognome;
	private String sesso;
	private int annoNascita;
	private String citta;
	private String nickname;
	private String password;
	private String email;
	private String avatar;

	public Registrazione() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		nome = (String) request.getParameter("rNome");
		cognome = (String) request.getParameter("rCognome");
		sesso = (String) request.getParameter("rSesso");
		annoNascita = Integer.parseInt(request.getParameter("rAnnoNascita"));
		citta = (String) request.getParameter("rCitta");
		nickname = (String) request.getParameter("rNickname");
		password = (String) request.getParameter("rPassword");
		email = (String) request.getParameter("rEmail");
		
		if(sesso.equals(Utilita.SESSO_MASCHIO)) {
			//utente maschio
			avatar = Utilita.USER_DEFAULT_AVATAR_MASCHIO;
		} else {
			//utente femmina
			avatar = Utilita.USER_DEFAULT_AVATAR_FEMMINA;
		}
		try {
			context = new InitialContext();
			gestoreProfilo = (GestoreProfiloRemote) context.lookup("GestoreProfiloJNDI");
			gestoreUser = (GestoreUserRemote) context.lookup("GestoreUserJNDI");
			gestoreAbilita = (GestoreAbilitaRemote) context.lookup("GestoreAbilitaJNDI");
			
			pattern = Pattern.compile(Utilita.NICKNAME_PATTERN);
			matcher = pattern.matcher(nickname);
			if(!matcher.find()) {
				request.setAttribute("messaggio", Comunicazione.nicknameNonValido());
				dispatcher = request.getRequestDispatcher("index.jsp");
				dispatcher.forward(request, response);
			} else {
				pattern = Pattern.compile(Utilita.EMAIL_PATTERN);
				matcher = pattern.matcher(email);
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

					//invia mail di confermazione
					Utilita.sendMail(nickname, password, cognome, nome, email, Utilita.OGGETTO_MAIL_REGISTRAZIONE, Utilita.MESSAGGIO_REGISTRAZIONE);
					request.getSession().setAttribute("nickname", nickname);
					request.setAttribute("abilitaSistema", gestoreAbilita.getAbilitaSistema());
					request.setAttribute("messaggio", Comunicazione.registrazioneCompletata());
					dispatcher = request.getRequestDispatcher("registrazione.jsp");
					dispatcher.forward(request, response);
				}
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
}
