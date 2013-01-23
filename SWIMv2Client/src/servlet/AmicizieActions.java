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

public class AmicizieActions extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final String ACCETTA = "accetta";
	private static final String RIFIUTA = "rifiuta";
	
	private RequestDispatcher dispatcher;
	private Context context;
	private GestoreAmiciziaRemote gestoreAmicizia;
	private String[] richiesteScelte;
	private String azione;

    public AmicizieActions() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		richiesteScelte = request.getParameterValues("richiesteScelte");
		azione = request.getParameter("azione");
		try {
			context = new InitialContext();
			gestoreAmicizia = (GestoreAmiciziaRemote) context.lookup("GestoreAmiciziaJNDI");
			if(richiesteScelte != null && azione != null) {
				for(String idRichiesta: richiesteScelte) {
					try {
						long id = Long.parseLong(idRichiesta);
						if(azione.equals(ACCETTA)) {
							gestoreAmicizia.accettaRichiesta(id, new GregorianCalendar());
						}
						if(azione.equals(RIFIUTA)) {
							gestoreAmicizia.rimuovi(id);
						}
					} catch (NumberFormatException numberFormatE) {
						request.setAttribute("messaggio", Comunicazione.erroreModificaRichiesteAmicizia());
						dispatcher = request.getRequestDispatcher("PagineUser/richiesteAmicizia.jsp");
						dispatcher.forward(request, response);
					}
				}
			}
			response.sendRedirect("RichiesteAmicizia");
		} catch (NamingException e) {
			request.setAttribute("messaggio", Comunicazione.erroreModificaRichiesteAmicizia());
			dispatcher = request.getRequestDispatcher("PagineUser/richiesteAmicizia.jsp");
			dispatcher.forward(request, response);			
		}
	}

}
