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
import session.GestoreAmiciziaRemote;
import utility.Comunicazione;
import utility.Utilita;

public class RichiesteAmicizia extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private RequestDispatcher dispatcher;
	private Context context;
	private GestoreAmiciziaRemote gestoreAmicizia;
	private String nickname;

	public RichiesteAmicizia() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if( Utilita.controlloSessione(request, response)){
			//esiste una sessione utente

			nickname = (String) request.getSession().getAttribute("nickname");
			try {
				context = new InitialContext();
				gestoreAmicizia = (GestoreAmiciziaRemote) context.lookup("GestoreAmiciziaJNDI");
				request.setAttribute("richiesteInviate", gestoreAmicizia.getRichiesteInviate(nickname));
				request.setAttribute("richiesteRicevute", gestoreAmicizia.getRichiesteRicevute(nickname));
			} catch (NamingException e) {
				request.setAttribute("messaggio", Comunicazione.erroreCaricamentoInformazioni());
			} finally {
				request.setAttribute("paginaAttuale", "richiesteAmicizia");
				dispatcher = request.getRequestDispatcher("PagineUser/richiesteAmicizia.jsp");
				dispatcher.forward(request, response);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
