package servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

	private MultipartRequest multiRequest;
	private File tempDirectory;
	private String tempDirPath;
	private File output;
	private FileInputStream inputStream;
	private FileOutputStream outputStream;

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

		boolean inviaMail = false;
		
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


			//recupero dell'avatar
			avatar = multiRequest.getFile("nAvatar");

			//modifica dell'avatar
			if(avatar != null){
				
				String nomeFileRicevuto = multiRequest.getOriginalFileName("nAvatar");

				//controllo dimensioni file
				long dimensioneAvatarKB = avatar.length() / 1024;
				if(dimensioneAvatarKB > Utilita.DIMENSIONE_MAX_IMG) {

					//immagine troppo grande
					throw new IOException();
				} else {

					//modifico nome del file dell'avatar
					String estensioneAvatar = nomeFileRicevuto.substring(nomeFileRicevuto.lastIndexOf('.'));
					String nomeFileAvatar = '\\' + nickname + estensioneAvatar;

					//costruisco il path di destinazione dell'avatar(sul server)
					String destinazione = getServletContext().getRealPath("/Immagini/Avatar") + nomeFileAvatar;

					//creo gli stream
					output = new File(destinazione);
					inputStream = new FileInputStream(avatar);
					outputStream = new FileOutputStream(output);

					//passaggio dei byte dalla sorgente alla destinazione
					while(inputStream.available() > 0) {
						outputStream.write(inputStream.read());
					}
					inputStream.close();
					outputStream.close();

					//costruisco path da memorizzare nell'entità user
					String avatarPath = Utilita.AVATAR_PATH_BASE + nomeFileAvatar.replace("\\", "");
					gestoreUser.modificaAvatar(nickname, avatarPath);
				}
			}
			
			if(inviaMail){
				String emailUser = gestoreUser.getUser(nickname).getEmail();
				//mail o password modificata... devo inviare una mail di conferma
				Utilita.sendMail(nickname, password, cognome, nome, emailUser, Utilita.OGGETTO_MAIL_MODIFICA, Utilita.MESSAGGIO_MODIFICA);
			}

			dispatcher = request.getRequestDispatcher("PaginaUser");
			dispatcher.forward(request, response);
			
		} catch (IOException ioEx) {
			request.getSession().setAttribute("messaggio", Comunicazione.fileAvatarTroppoGrandeModifica());
			response.sendRedirect("PaginaUser");
		} catch (NamingException nEx) {
			request.setAttribute("messaggio", Comunicazione.erroreServlet());
			dispatcher = request.getRequestDispatcher("PaginaUser");
			dispatcher.forward(request, response);	
		} catch (Exception e) {
			request.setAttribute("messaggio", Comunicazione.erroreModificaInformazioni());
			dispatcher = request.getRequestDispatcher("PaginaUser");
			dispatcher.forward(request, response);
		} 
	}

}

