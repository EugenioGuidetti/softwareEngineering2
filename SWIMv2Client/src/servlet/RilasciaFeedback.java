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
import session.GestoreFeedbackRemote;
import utility.Comunicazione;

public class RilasciaFeedback extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private RequestDispatcher dispatcher;
	private Context context;
	private GestoreFeedbackRemote gestoreFeedback;
	private String valutazioneNumerica;
	private String valutazioneEstesa;
	private String aiutoValutato;

    public RilasciaFeedback() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		valutazioneNumerica = request.getParameter("vNumerica");
		valutazioneEstesa = request.getParameter("vEstesa");
		aiutoValutato = request.getParameter("aiutoValutato");
		try {
			context = new InitialContext();
			gestoreFeedback = (GestoreFeedbackRemote) context.lookup("GestoreFeedbackJNDI");
			try {
				int punteggio = Integer.parseInt(valutazioneNumerica);
				long idAiuto = Long.parseLong(aiutoValutato);
				if(!gestoreFeedback.rilascia(idAiuto, punteggio, valutazioneEstesa, new GregorianCalendar())) {
					request.setAttribute("messaggio", Comunicazione.erroreRilascioFeedback());
					dispatcher = request.getRequestDispatcher("PagineUser/rilasciaFeedback.jsp");
					dispatcher.forward(request, response);	
				} else {
					request.setAttribute("messaggio", Comunicazione.confermaRilascioFeedback());
					dispatcher = request.getRequestDispatcher("Aiuti");
					dispatcher.forward(request, response);	
				}
			} catch (NumberFormatException numberFormatE) {
				request.setAttribute("messaggio", Comunicazione.erroreRilascioFeedback());
				dispatcher = request.getRequestDispatcher("PagineUser/rilasciaFeedback.jsp");
				dispatcher.forward(request, response);				
			}
		} catch (NamingException e) {
			request.setAttribute("messaggio", Comunicazione.erroreRilascioFeedback());
			dispatcher = request.getRequestDispatcher("PagineUser/rilasciaFeedback.jsp");
			dispatcher.forward(request, response);
		}
	}

}
