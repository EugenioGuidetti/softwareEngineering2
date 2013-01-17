package servlet;

import java.io.IOException;
import java.util.List;
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
import entity.Abilita;
import session.GestoreAbilitaRemote;
import session.GestoreProfiloRemote;
import session.GestoreUserRemote;
import utility.Comunicazione;
import utility.Messaggio;
import utility.TipoMessaggio;
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
		try {
			Context context = new InitialContext();
			GestoreProfiloRemote gestoreProfilo = (GestoreProfiloRemote) context.lookup("GestoreProfiloJNDI");
			GestoreUserRemote gestoreUser = (GestoreUserRemote) context.lookup("GestoreUserJNDI");
			GestoreAbilitaRemote gestoreAbilita = (GestoreAbilitaRemote) context.lookup("GestoreAbilitaJNDI");
			RequestDispatcher dispatcher;
			String nome = (String) request.getParameter("rNome");
			String cognome = (String) request.getParameter("rCognome");
			String sesso = (String) request.getParameter("rSesso");
			int annoNascita = Integer.parseInt(request.getParameter("rAnnoNascita"));
			String citta = (String) request.getParameter("rCitta");
			String nickname = (String) request.getParameter("rNickname");
			String password = (String) request.getParameter("rPassword");
			String email = (String) request.getParameter("rEmail");
			String avatar = Utilita.USER_DEFAULT_AVATAR;
			
			Pattern pattern = Pattern.compile(Utilita.EMAIL_PATTERN);
			Matcher matcher = pattern.matcher(email);
			if(!matcher.find()) {
				Messaggio messaggio = new Messaggio(TipoMessaggio.ERRORE, Comunicazione.EMAIL_NON_VALIDA);
				request.setAttribute("messaggio", messaggio);
				dispatcher = request.getRequestDispatcher("index.jsp");
				dispatcher.forward(request, response);
			} else if(!gestoreProfilo.disponibilitaNickname(nickname)) {
				Messaggio messaggio = new Messaggio(TipoMessaggio.AVVISO, Comunicazione.NICKNAME_NON_LIBERO);
				request.setAttribute("messaggio", messaggio);
				dispatcher = request.getRequestDispatcher("index.jsp");
				dispatcher.forward(request, response);
			} else {
				gestoreUser.registra(nickname, password, email, nome, cognome, avatar, citta, sesso, annoNascita);
				request.setAttribute("nickname", nickname);
				List<Abilita> abilitaSistema = gestoreAbilita.getAbilitaSistema();
				for(Abilita abilita : abilitaSistema) {
					request.setAttribute("abilita" + abilitaSistema.indexOf(abilita), abilita);
				}
				dispatcher = request.getRequestDispatcher("registrazione.jsp");
				dispatcher.forward(request, response);
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
}
