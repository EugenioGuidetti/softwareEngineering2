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
import session.GestorePropostaAbilitaRemote;
import utility.Comunicazione;

public class GestioneProposteActions extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final String VISIONA = "visiona";
	private static final String CANCELLA = "cancella";

    public GestioneProposteActions() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher;
		Context context;
		GestorePropostaAbilitaRemote gestoreProposta;
		String[] proposteScelte = request.getParameterValues("proposteScelte");
		String azione = request.getParameter("azione");
		try {
			context = new InitialContext();
			gestoreProposta = (GestorePropostaAbilitaRemote) context.lookup("GestorePropostaAbilitaJNDI");
			if(proposteScelte != null && azione != null) {
				for(String idProposta: proposteScelte) {
					try {
						long id = Long.parseLong(idProposta);
						if(azione.equals(VISIONA)) {
							gestoreProposta.visionaProposta(id);
						}
						if(azione.equals(CANCELLA)) {
							gestoreProposta.cancellaProposta(id);
						}
					} catch (NumberFormatException numberFormatE) {
						request.setAttribute("messaggio", Comunicazione.erroreModificaProposte());
						dispatcher = request.getRequestDispatcher("PagineAdmin/gestioneProposte.jsp");
						dispatcher.forward(request, response);
					}
				}
			}
			response.sendRedirect("GestioneProposte");
		} catch (NamingException e) {
			request.setAttribute("messaggio", Comunicazione.erroreModificaProposte());
			dispatcher = request.getRequestDispatcher("PagineAdmin/gestioneProposte.jsp");
			dispatcher.forward(request, response);
		}
	}

}
