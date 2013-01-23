package servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
	private File output;
	private FileInputStream inputStream;
	private FileOutputStream outputStream;

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

			//recupero l'icona
			icona = multiRequest.getFile("icona");

			String nomeFileRicevuto = multiRequest.getOriginalFileName("icona");

			//controllo dimensioni file
			long dimensioneIconaKB = icona.length() / 1024;
			if(dimensioneIconaKB > Utilita.DIMENSIONE_MAX_IMG) {
				//immagine troppo grande
				throw new IOException();
			} 
			else {
				//modifico nome del file dell'avatar
				String estensioneIcona = nomeFileRicevuto.substring(nomeFileRicevuto.lastIndexOf('.'));

				//recupero il numero di abilità disponibili nel sistema per dare il nome alla nuova immagine
				long nomeSenzaEstensione = gestoreAbilita.getAbilitaSistema().size();
				String nomeFileIcona = "\\" + (nomeSenzaEstensione + 1) + estensioneIcona;

				//costruisco il path di destinazione dell'avatar(sul server)
				String destinazione = getServletContext().getRealPath("/Immagini/Icone") + nomeFileIcona;				

				//creo gli stream
				output = new File(destinazione);
				inputStream = new FileInputStream(icona);
				outputStream = new FileOutputStream(output);

				//passaggio dei byte dalla sorgente alla destinazione
				while(inputStream.available() > 0) {
					outputStream.write(inputStream.read());
				}
				inputStream.close();
				outputStream.close();

				//costruisco path da memorizzare nell'entità abilita
				iconaPath = Utilita.ICONA_PATH_BASE + nomeFileIcona.replace("\\", "");
			}

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
			response.sendRedirect("/SWIMv2Client/PagineAdmin/paginaAdmin.jsp");
		}
	}

}
