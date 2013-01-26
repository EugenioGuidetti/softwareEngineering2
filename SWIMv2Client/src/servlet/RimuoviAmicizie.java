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

public class RimuoviAmicizie extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private RequestDispatcher dispatcher;
	private Context context;
	private GestoreAmiciziaRemote gestoreAmicizia;
	private String[] amicizieScelte;

    public RimuoviAmicizie() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		amicizieScelte = request.getParameterValues("amicizieScelte");
		try {
			context = new InitialContext();
			gestoreAmicizia = (GestoreAmiciziaRemote) context.lookup("GestoreAmiciziaJNDI");
			if(amicizieScelte != null) {
				for(String idAmicizia: amicizieScelte) {
					try {
						long id = Long.parseLong(idAmicizia);
						gestoreAmicizia.rimuovi(id);						
					} catch (NumberFormatException numberFormatE) {
						request.setAttribute("messaggio", Comunicazione.erroreModficaAmicizie());
					}
				}
			}
		} catch (NamingException e) {
			request.setAttribute("messaggio", Comunicazione.erroreModficaAmicizie());			
		} finally {
			dispatcher = request.getRequestDispatcher("PaginaUser");
			dispatcher.forward(request, response);			
		}
	}

}
