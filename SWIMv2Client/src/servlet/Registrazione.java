package servlet;

import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
		avatar = Utilita.USER_DEFAULT_AVATAR;
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

				//invia mail di confermazione
				sendMail(nickname, password, cognome, nome, email);
				request.getSession().setAttribute("nickname", nickname);
				request.setAttribute("abilitaSistema", gestoreAbilita.getAbilitaSistema());
				request.setAttribute("messaggio", Comunicazione.registrazioneCompletata());
				dispatcher = request.getRequestDispatcher("registrazione.jsp");
				dispatcher.forward(request, response);
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private void sendMail(String nickname, String password, String cognome, String nome, String email){

		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", Utilita.host);

		InitialContext ctx;
		try {
			ctx = new InitialContext();
			Session session = (Session) ctx.lookup("java:/Mail");

			MimeMessage message = new MimeMessage( session );
			try {
				message.setFrom( new InternetAddress(Utilita.from) );

				message.addRecipient(RecipientType.TO, new InternetAddress(email));
				message.setSubject( "Registrazione completata - SWIMv2");
				message.setText( 
						"Ciao "+ nome + " " + cognome +	",\n" +
						"la registrazione è stata completata con successo. \n\n" +
						"Queste sono le tue credenziali di accesso:\n" +
						"nickname: " + nickname +
						"\npassword: " + password +
						"\n\n Puoi cambiare la tua password in qualsiasi momento dalla tua area personale." +
						"\n\n\n@admin");
				Transport.send( message );
			}
			catch (MessagingException ex){
				System.err.println("Errore nell'invio della mail:" + ex);
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

}
