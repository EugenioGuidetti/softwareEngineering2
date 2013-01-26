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

	private RequestDispatcher dispatcher;
	private Context context;
	private GestorePropostaAbilitaRemote gestoreProposta;
	private String[] proposteScelte;
	private String azione;
	
    public GestioneProposteActions() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("index.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		proposteScelte = request.getParameterValues("proposteScelte");
		azione = request.getParameter("azione");
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
					}
				}
			}
		} catch (NamingException e) {
			request.setAttribute("messaggio", Comunicazione.erroreModificaProposte());
		} finally {
			dispatcher = request.getRequestDispatcher("GestioneProposte");
			dispatcher.forward(request, response);			
		}
	}

}
