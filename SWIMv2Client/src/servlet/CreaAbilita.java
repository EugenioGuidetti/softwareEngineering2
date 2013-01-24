package servlet;

import java.io.File;
import java.io.IOException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.oreilly.servlet.MultipartRequest;
import session.GestoreAbilitaRemote;
import utility.Comunicazione;
import utility.Utilita;

public class CreaAbilita extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private MultipartRequest multiRequest;
	private File tempDirectory;
	private String tempDirPath;

	private RequestDispatcher dispatcher;
	private Context context;
	private GestoreAbilitaRemote gestoreAbilita;
	private File icona;
	private String nome;
	private String descrizione;
	private String iconaPath;



	public CreaAbilita() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nomeFileRicevuto;
		String estensioneIcona;
		String nomeFileIcona;
		String destinazione;
		
		dispatcher = request.getRequestDispatcher("PaginaAdmin");

		tempDirectory = new File(System.getProperty("java.io.tmpdir"));
		tempDirPath = tempDirectory.getCanonicalPath();

		iconaPath = null;
		try {
			context = new InitialContext();
			gestoreAbilita = (GestoreAbilitaRemote) context.lookup("GestoreAbilitaJNDI");

			multiRequest = new MultipartRequest(request, tempDirPath);

			//RECUPERO I PARAMETRI
			nome = multiRequest.getParameter("nome");
			descrizione = multiRequest.getParameter("descrizione");

			//recupero il file di cui fare l'upload ed il suo nome
			icona = multiRequest.getFile("icona"); //fileUpload
			nomeFileRicevuto = multiRequest.getOriginalFileName("icona");

			//COSTRUISCO IL NOME DEL FILE UPLOADATO
			
			estensioneIcona = nomeFileRicevuto.substring(nomeFileRicevuto.lastIndexOf('.'));
			//recupero il numero di abilità disponibili nel sistema per dare il nome alla nuova immagine
			long nomeSenzaEstensione = gestoreAbilita.getAbilitaSistema().size();
			nomeFileIcona = "\\" + (nomeSenzaEstensione + 1) + estensioneIcona;

			//COSTRUISCO IL PATH DI DESTINAZIONE DELL'ICONA SUL SERVER
			destinazione = getServletContext().getRealPath("/Immagini/Icone") + nomeFileIcona;
			
			//EFFETTUO L'UPLOAD
			iconaPath = Utilita.uploadFile(icona, Utilita.TIPO_UPLOAD_ICONA, nomeFileIcona, destinazione);
			
			//CREO L'ABILITA'
			if(gestoreAbilita.crea(nome, descrizione, iconaPath)) {
				//l'abilità è stata creata
				request.setAttribute("messaggio", Comunicazione.confermaCreazioneAbilita());
				dispatcher.forward(request, response);
			} 
			else {
				//c'è stato un errore durante la creazione dell'abilità
				request.setAttribute("messaggio", Comunicazione.erroreCreazioneAbilita());
				dispatcher.forward(request, response);
			}
		} 
		catch (NamingException n) {
			request.setAttribute("messaggio", Comunicazione.erroreCreazioneAbilita());
			dispatcher.forward(request, response);
		} 
		catch (IOException ioE) {
			//modificare messaggio: file immagine troppo grande
			request.getSession().setAttribute("messaggio", Comunicazione.fileIconaTroppoGrande());
			response.sendRedirect("PaginaAdmin");
		}
			
	}

}
