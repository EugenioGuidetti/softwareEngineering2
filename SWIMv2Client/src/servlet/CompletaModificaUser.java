package servlet;

import java.io.File;
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
import com.oreilly.servlet.MultipartRequest;
import entity.User;
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
	private File avatar;
	private String avatarPath;

	private MultipartRequest multiRequest;
	private File tempDirectory;
	private String tempDirPath;

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
		response.sendRedirect("index.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		boolean inviaMail = false;
		String nomeFileRicevuto;
		String estensioneAvatar;
		String nomeFileAvatar;
		String destinazione;

		tempDirectory = new File(System.getProperty("java.io.tmpdir"));
		tempDirPath = tempDirectory.getCanonicalPath();

		try {
			multiRequest = new MultipartRequest(request, tempDirPath);

			nickname = (String) request.getSession().getAttribute("nickname");
			nome = multiRequest.getParameter("nNome");
			cognome = multiRequest.getParameter("nCognome");
			password = multiRequest.getParameter("nPassword");
			email = multiRequest.getParameter("nEmail");
			sesso = multiRequest.getParameter("nSesso");
			citta = multiRequest.getParameter("nCitta");
			annoNascita = multiRequest.getParameter("nAnnoNascita");

			context = new InitialContext();
			gestoreUser = (GestoreUserRemote) context.lookup("GestoreUserJNDI");

			//recupero il file di cui fare l'upload ed il suo nome
			avatar = multiRequest.getFile("nAvatar");

			//verifico se l'utente ha caricato un file
			if(avatar != null) {
				nomeFileRicevuto = multiRequest.getOriginalFileName("nAvatar");

				//COSTRUISCO IL NOME DEL FILE UPLOADATO
				estensioneAvatar = nomeFileRicevuto.substring(nomeFileRicevuto.lastIndexOf('.'));
				nomeFileAvatar = '\\' + nickname + estensioneAvatar;

				//COSTRUISCO IL PATH DI DESTINAZIONE DELL'AVATAR SUL SERVER
				destinazione = getServletContext().getRealPath("/Immagini/Avatar") + nomeFileAvatar;

				//EFFETTUO L'UPLOAD
				avatarPath = Utilita.uploadFile(avatar, Utilita.TIPO_UPLOAD_MODIFICA, nomeFileAvatar, destinazione);

				//MODIFICO L'AVATAR DELLO USER NEL DATABASE
				gestoreUser.modificaAvatar(nickname, avatarPath);
			}

			//se l'immagine caricata non superava i 700kb allora procedo a modificare gli altri campi

			//modifica email
			if(!email.equals("")){
				pattern = Pattern.compile(Utilita.EMAIL_PATTERN);
				matcher = pattern.matcher(email);
				if(!matcher.find()) {
					request.setAttribute("messaggio", Comunicazione.emailNonValida());
				} else {
					if(!gestoreUser.modificaEmail(nickname, email)) {
						request.setAttribute("messaggio", Comunicazione.erroreModificaInformazioni());
					}
					else{
						//metto un flag sul fatto che devo inviare una mail
						inviaMail = true;
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
				else{
					//metto un flag sul fatto che devo inviare una mail
					inviaMail = true;
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

			//la mail viene inviata solo se si è modificata la password o il precedente indirizzo mail
			if(inviaMail){
				User user = gestoreUser.getUser(nickname);
				//mail o password modificata... devo inviare una mail di conferma
				Utilita.sendMail(nickname, user.getPassword(), user.getCognome(), user.getNome(), user.getEmail(), Utilita.OGGETTO_MAIL_MODIFICA, Utilita.MESSAGGIO_MODIFICA);
			}


		} catch (IOException ioEx) {
			request.getSession().setAttribute("messaggio", Comunicazione.fileAvatarTroppoGrandeModifica());
		} catch (NamingException nEx) {
			request.setAttribute("messaggio", Comunicazione.erroreServlet());
		} catch (Exception e) {
			request.setAttribute("messaggio", Comunicazione.erroreModificaInformazioni());
		} finally {
			dispatcher = request.getRequestDispatcher("PaginaUser");
			dispatcher.forward(request, response);			
		}
	}
}
