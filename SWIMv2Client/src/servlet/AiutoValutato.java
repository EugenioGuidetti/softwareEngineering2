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
import session.GestoreAiutoRemote;
import utility.Comunicazione;

public class AiutoValutato extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private RequestDispatcher dispatcher;
	private Context context;
	private GestoreAiutoRemote gestoreAiuto;
	private String idAiuto;

	public AiutoValutato() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("index.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		idAiuto = request.getParameter("aiutoValutato");
		try {
			context = new InitialContext();
			gestoreAiuto = (GestoreAiutoRemote) context.lookup("GestoreAiutoJNDI");
			try {
				long id = Long.parseLong(idAiuto);
				request.setAttribute("aiutoValutato", gestoreAiuto.getAiuto(id));				
			} catch (NumberFormatException numberFormatE) {
				request.setAttribute("messaggio", Comunicazione.erroreCaricamentoAiuto());
			}
		} catch (NamingException e) {
			request.setAttribute("messaggio", Comunicazione.erroreCaricamentoAiuto());			
		} finally {
			dispatcher = request.getRequestDispatcher("PagineUser/rilasciaFeedback.jsp");
			dispatcher.forward(request, response);
		}
	}
}


