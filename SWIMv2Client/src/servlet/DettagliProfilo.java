package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import session.GestoreAbilitaRemote;
import session.GestoreAmiciziaRemote;
import session.GestoreUserRemote;
import session.GestoreUtilitiesRemote;
import utility.Comunicazione;
import entity.Abilita;
import entity.ReputazioneAbilita;
import entity.User;

public class DettagliProfilo extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private RequestDispatcher dispatcher;
	private Context context;
	private GestoreUserRemote gestoreUser;
	private GestoreAbilitaRemote gestoreAbilita;
	private GestoreAmiciziaRemote gestoreAmicizia;
	private GestoreUtilitiesRemote gestoreUtilities;
	private String nickname;
	private String nicknameCercato;
	private User user;
	private List<Abilita> abilitaDichiarate;
	private Map<Abilita, ReputazioneAbilita> abilitaValutate;

	public DettagliProfilo() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("index.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		nickname = (String) request.getSession().getAttribute("nickname");
		nicknameCercato = request.getParameter("userCercato");
		request.setAttribute("nomeCercato", request.getParameter("nomeCercato"));
		request.setAttribute("cognomeCercato", request.getParameter("cognomeCercato"));
		request.setAttribute("abilitaCercata", request.getParameter("abilitaCercata"));
		request.setAttribute("filtroUsato", request.getParameter("filtroUsato"));
		request.setAttribute("dominioScelto", request.getParameter("dominioScelto"));
		try {
			context = new InitialContext();
			gestoreUser = (GestoreUserRemote) context.lookup("GestoreUserJNDI");
			gestoreAbilita = (GestoreAbilitaRemote) context.lookup("GestoreAbilitaJNDI");
			gestoreAmicizia = (GestoreAmiciziaRemote) context.lookup("GestoreAmiciziaJNDI");
			user = gestoreUser.getUser(nicknameCercato);
			request.setAttribute("userCercato", user);
			request.setAttribute("amicizia", gestoreAmicizia.controllaAmici(nickname, nicknameCercato));
			abilitaDichiarate = gestoreAbilita.getAbilitaUser(nicknameCercato);
			if(!abilitaDichiarate.isEmpty()) {
				gestoreUtilities = (GestoreUtilitiesRemote) context.lookup("GestoreUtilitiesJNDI");
				abilitaValutate = new HashMap<Abilita, ReputazioneAbilita>();
				for(Abilita abilita: abilitaDichiarate) {
					abilitaValutate.put
					(abilita, gestoreUtilities.getReputazioneAbilita(nicknameCercato, abilita.getId()));
				}
				request.setAttribute("abilitaDichiarate", abilitaDichiarate);
				request.setAttribute("abilitaValutate", abilitaValutate);
			}
		} catch (NamingException e) {
			request.setAttribute("messaggio", Comunicazione.erroreCaricamentoInformazioni());
		} finally {
			dispatcher = request.getRequestDispatcher("PagineUser/dettagliProfilo.jsp");
			dispatcher.forward(request, response);
		}
	}
}


