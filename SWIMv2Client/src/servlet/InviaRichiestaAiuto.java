package servlet;

import java.io.IOException;
import java.util.GregorianCalendar;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import session.GestoreAiutoRemote;
import utility.Comunicazione;
import utility.Utilita;

public class InviaRichiestaAiuto extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private RequestDispatcher dispatcher;
	private Context context;
	private GestoreAiutoRemote gestoreAiuto;
	private String nickname;
	private String nicknameDestinatario;
	private String abilita;
	private String descrizione;

	public InviaRichiestaAiuto() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if( Utilita.controlloSessione(request, response)){
			//esiste una sessione utente

			nickname = (String) request.getSession().getAttribute("nickname");
			nicknameDestinatario = request.getParameter("nicknameDestinatario");
			abilita = request.getParameter("abilita");
			descrizione = request.getParameter("descrizione");
			try {
				context = new InitialContext();
				gestoreAiuto = (GestoreAiutoRemote) context.lookup("GestoreAiutoJNDI");
				try {
					long id = Long.parseLong(abilita);
					if(!gestoreAiuto.inviaRichiesta(nickname, nicknameDestinatario, id, descrizione, new GregorianCalendar())) {
						request.setAttribute("messaggio", Comunicazione.erroreRichiestaAiuto());
					} else {
						request.setAttribute("messaggio", Comunicazione.confermaRichiestaAiuto());
					}
				} catch (NumberFormatException numberFormate) {
					request.setAttribute("messaggio", Comunicazione.erroreRichiestaAiuto());
				}
			} catch (NamingException e) {
				request.setAttribute("messaggio", Comunicazione.erroreRichiestaAiuto());
			} finally {
				dispatcher = request.getRequestDispatcher("Ricerca");
				dispatcher.forward(request, response);
			}
		}

	}
}
