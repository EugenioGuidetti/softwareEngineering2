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
import session.GestoreAmiciziaRemote;
import utility.Comunicazione;

public class InviaRichiestaAmicizia extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private RequestDispatcher dispatcher;
	private Context context;
	private GestoreAmiciziaRemote gestoreAmicizia;
	private String nickname;
	private String nicknameDestinatario;

    public InviaRichiestaAmicizia() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		nickname = (String) request.getSession().getAttribute("nikcname");
		nicknameDestinatario = request.getParameter("nicknameDestinatario");
		try {
			context = new InitialContext();
			gestoreAmicizia = (GestoreAmiciziaRemote) context.lookup("GestoreAmiciziaJNDI");
			if(!gestoreAmicizia.inviaRichiesta(nickname, nicknameDestinatario, new GregorianCalendar())) {
				request.setAttribute("messaggio", Comunicazione.erroreRichiestaAmicizia());
			} else {
				request.setAttribute("messaggio", Comunicazione.confermaRichiestaAmicizia());
			}
		} catch (NamingException e) {
			request.setAttribute("messaggio", Comunicazione.erroreRichiestaAmicizia());
		} finally {
			dispatcher = request.getRequestDispatcher("Ricerca");
			dispatcher.forward(request, response);
		}
	}

}
