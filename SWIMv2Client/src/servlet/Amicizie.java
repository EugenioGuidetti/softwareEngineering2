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

public class Amicizie extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private RequestDispatcher dispatcher;
	private Context context;
	private GestoreAmiciziaRemote gestoreAmicizia;
	private String nickname;

    public Amicizie() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		nickname = (String) request.getSession().getAttribute("nickname");
		try {
			context = new InitialContext();
			gestoreAmicizia = (GestoreAmiciziaRemote) context.lookup("GestoreAmiciziaJNDI");
			request.setAttribute("amicizie", gestoreAmicizia.getAmicizieAllacciate(nickname));
		} catch (NamingException e) {
			request.setAttribute("messaggio", Comunicazione.erroreCaricamentoInformazioni());
		} finally {
			dispatcher = request.getRequestDispatcher("PagineUser/modificaAmicizie.jsp");
			dispatcher.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
