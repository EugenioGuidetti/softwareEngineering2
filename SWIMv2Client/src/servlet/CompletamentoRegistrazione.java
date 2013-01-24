package servlet;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.oreilly.servlet.MultipartRequest;
import entity.Abilita;
import session.GestoreAbilitaRemote;
import session.GestoreUserRemote;
import utility.Comunicazione;
import utility.Utilita;

public class CompletamentoRegistrazione extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private MultipartRequest multiRequest;
	private File tempDirectory;
	private String tempDirPath;

	private RequestDispatcher dispatcher;
	private Context context;
	private GestoreUserRemote gestoreUser;
	private GestoreAbilitaRemote gestoreAbilita;
	private String nickname;
	private File avatar;
	private String[] abilitaScelte;
	private Set<Abilita> abilitaDichiarate;
	private String avatarPath;

	public CompletamentoRegistrazione() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nomeFileRicevuto;
		String estensioneAvatar;
		String nomeFileAvatar;
		String destinazione;

		abilitaDichiarate = new HashSet<Abilita>();		
		tempDirectory = new File(System.getProperty("java.io.tmpdir"));
		tempDirPath = tempDirectory.getCanonicalPath();

		//verifico le dimensioni della post
		try {
			context = new InitialContext(); 
			gestoreAbilita = (GestoreAbilitaRemote) context.lookup("GestoreAbilitaJNDI");
			gestoreUser = (GestoreUserRemote) context.lookup("GestoreUserJNDI");

			multiRequest = new MultipartRequest(request, tempDirPath);
			nickname = (String) request.getSession().getAttribute("nickname");

			//recupero il file di cui fare l'upload ed il suo nome
			avatar = multiRequest.getFile("rAvatar");

			//verifico se l'utente ha caricato un file
			if(avatar != null) {
				nomeFileRicevuto = multiRequest.getOriginalFileName("rAvatar");

				//COSTRUISCO IL NOME DEL FILE UPLOADATO
				estensioneAvatar = nomeFileRicevuto.substring(nomeFileRicevuto.lastIndexOf('.'));
				nomeFileAvatar = '\\' + nickname + estensioneAvatar;

				//COSTRUISCO IL PATH DI DESTINAZIONE DELL'AVATAR SUL SERVER
				destinazione = getServletContext().getRealPath("/Immagini/Avatar") + nomeFileAvatar;

				//EFFETTUO L'UPLOAD
				avatarPath = Utilita.uploadFile(avatar, Utilita.TIPO_UPLOAD_REGISTRAZIONE, nomeFileAvatar, destinazione);

				//MODIFICO L'AVATAR DELLO USER NEL DATABASE
				gestoreUser.modificaAvatar(nickname, avatarPath);
			}

			//DICHIARO LE ABILITA' SCELTE 
			abilitaScelte = multiRequest.getParameterValues("abilitaScelte");
			if(abilitaScelte != null) {
				for(String abilitaScelta: abilitaScelte) {
					long id = Long.parseLong(abilitaScelta);
					abilitaDichiarate.add(gestoreAbilita.getAbilita(id));
				}
				gestoreUser.modificaAbilitaDichiarate(nickname, abilitaDichiarate);
			}

			//invalido la sessione di registrazione
			request.setAttribute("messaggio", Comunicazione.confermaAvatarAbilita());
			request.getSession().invalidate();
			dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);

		} 
		catch (IOException ioEx) {
			request.getSession().setAttribute("messaggio", Comunicazione.fileAvatarTroppoGrande());
			response.sendRedirect("index.jsp");
		} 
		catch (NamingException nEx) {
			request.setAttribute("messaggio", Comunicazione.erroreServlet());
			//invalido la sessione di registrazione
			request.getSession().invalidate();
			dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);	
		} 
		catch (NumberFormatException numberFormatE) {
			request.setAttribute("messaggio", Comunicazione.erroreServlet());
			//invalido la sessione di registrazione
			request.getSession().invalidate();
			dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);
		}

	}

}
