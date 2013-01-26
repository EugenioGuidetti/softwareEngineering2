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

public class RichiesteAiutoActions extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final String ACCETTA = "accetta";
	private static final String RIFIUTA = "rifiuta";
	
	private RequestDispatcher dispatcher;
	private Context context;
	private GestoreAiutoRemote gestoreAiuto;
	private String[] richiesteScelte;
	private String azione;

    public RichiesteAiutoActions() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("index.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		richiesteScelte = request.getParameterValues("richiesteScelte");
		azione = request.getParameter("azione");
		try {
			context = new InitialContext();
			gestoreAiuto = (GestoreAiutoRemote) context.lookup("GestoreAiutoJNDI");
			if(richiesteScelte != null && azione != null) {
				for(String idRichiesta: richiesteScelte) {
					try {
						long id = Long.parseLong(idRichiesta);
						if(azione.equals(ACCETTA)) {
							gestoreAiuto.accettaRichiesta(id, new GregorianCalendar());
						}
						if(azione.equals(RIFIUTA)) {
							gestoreAiuto.rifiutaRichiesta(id);
						}
					} catch (NumberFormatException numberFormatE) {
						request.setAttribute("messaggio", Comunicazione.erroreModificaRichiesteAmicizia());
					}
				}
			}
		} catch (NamingException e) {
			request.setAttribute("messaggio", Comunicazione.erroreModificaRichiesteAmicizia());		
		} finally {
			dispatcher = request.getRequestDispatcher("RichiesteAiuto");
			dispatcher.forward(request, response);				
		}
	}

}
