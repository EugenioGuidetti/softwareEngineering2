package servlet;

import java.io.IOException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import session.GestoreUserRemote;
import utility.Comunicazione;
import utility.Utilita;

/**
 * Servlet implementation class CancellaProfilo
 */
public class CancellaProfilo extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private String nickname;
	private Context context;
	private GestoreUserRemote gestoreUser;
	private RequestDispatcher dispatcher;


	public CancellaProfilo() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if( Utilita.controlloSessione(request, response)){
			//esiste una sessione utente

			//recupero il nickname del profilo dello user da eliminare
			nickname = (String) request.getSession().getAttribute("nickname");

			try {
				context = new InitialContext();
				gestoreUser = (GestoreUserRemote) context.lookup("GestoreUserJNDI");

				//rimuovo lo user
				if(gestoreUser.elimina(nickname)){
					//user eliminato: invalido la sessione e ritorno alla homepage
					//request.getSession().setAttribute("messaggio", Comunicazione.confermaCancellazioneProfilo());
					//response.sendRedirect("index.jsp");
					request.getSession().invalidate();
					request.setAttribute("messaggio", Comunicazione.confermaCancellazioneProfilo());
					dispatcher = request.getRequestDispatcher("confermaCancellazione.jsp");
					dispatcher.forward(request, response);
				}
				else{
					//errore nell'eliminazione dello user: ritorno alla pagina principale dello user con un messaggio di errore servlet
					request.setAttribute("messaggio", Comunicazione.erroreServlet());
					dispatcher = request.getRequestDispatcher("PaginaUser");
					dispatcher.forward(request, response);
				}			

			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
