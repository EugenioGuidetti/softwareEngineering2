package servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
	private File output;
	private FileInputStream inputStream;
	private FileOutputStream outputStream;

	private RequestDispatcher dispatcher;
	private Context context;
	private GestoreUserRemote gestoreUser;
	private GestoreAbilitaRemote gestoreAbilita;
	private String nickname;
	private File avatar;
	private String[] abilitaScelte;
	private Set<Abilita> abilitaDichiarate;

	public CompletamentoRegistrazione() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		abilitaDichiarate = new HashSet<Abilita>();

		tempDirectory = new File(System.getProperty("java.io.tmpdir"));
		tempDirPath = tempDirectory.getCanonicalPath();

		//verifico le dimensioni della post
		try {
			context = new InitialContext(); 
			gestoreAbilita = (GestoreAbilitaRemote) context.lookup("GestoreAbilitaJNDI");
			gestoreUser = (GestoreUserRemote) context.lookup("GestoreUserJNDI");

			multiRequest = new MultipartRequest(request, tempDirPath);
			System.out.println("sono qui");
			nickname = (String) request.getSession().getAttribute("nickname");
			System.out.println("nickname: " + nickname);
			abilitaScelte = multiRequest.getParameterValues("abilitaScelte");	

			//recupero dell'avatar
			avatar = multiRequest.getFile("rAvatar");

			if(avatar != null) {
				String nomeFileRicevuto = multiRequest.getOriginalFileName("rAvatar");

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
					String destinazione = getServletContext().getRealPath("/Immagini") + nomeFileAvatar;

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
					String avatarPath = Utilita.IMG_PATH_BASE + nomeFileAvatar.replace("\\", "");
					gestoreUser.modificaAvatar(nickname, avatarPath);
				}
			}
			//dichiaro le abilità scelte 
			if(abilitaScelte != null) {
				for(String abilitaScelta: abilitaScelte) {
					long id = Long.parseLong(abilitaScelta);
					abilitaDichiarate.add(gestoreAbilita.getAbilita(id));
				}
				gestoreUser.modificaAbilitaDichiarate(nickname, abilitaDichiarate);
			}
			//invalido la sessione di registrazione
			request.getSession().invalidate();

			//ritorno alla homepage
			request.setAttribute("messaggio", Comunicazione.registrazioneCompletata());
			dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);

		} catch (IOException ioEx) {
			request.setAttribute("messaggio", Comunicazione.fileTroppoGrande());
			dispatcher = request.getRequestDispatcher("registrazione.jsp");
			System.out.println("dispatcher: " + dispatcher.toString());
			dispatcher.forward(request, response);
		} catch (NamingException nEx) {
			//altrotipo di messaggio
		} catch (NumberFormatException numberFormatE) {
			//messaggio
		}
	}

}
