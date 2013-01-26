package servlet;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entity.Abilita;
import session.GestoreAbilitaRemote;
import session.GestoreUserRemote;
import utility.Comunicazione;
import utility.Utilita;

public class ModificaAbilita extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private RequestDispatcher dispatcher;
	private Context context;
	private GestoreUserRemote gestoreUser;
	private GestoreAbilitaRemote gestoreAbilita;
	private String nickname;
	private String[] abilitaScelte;
	private Set<Abilita> abilitaDichiarate;

	public ModificaAbilita() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if( Utilita.controlloSessione(request, response)){
			//esiste una sessione utente

			nickname = (String) request.getSession().getAttribute("nickname");
			abilitaScelte = request.getParameterValues("abilitaScelte");
			try {
				context = new InitialContext();
				gestoreUser = (GestoreUserRemote) context.lookup("GestoreUserJNDI");
				gestoreAbilita = (GestoreAbilitaRemote) context.lookup("GestoreAbilitaJNDI");
				abilitaDichiarate = new HashSet<Abilita>();
				if(abilitaScelte != null) {
					for(String idAbilita: abilitaScelte) {
						try {
							long id = Long.parseLong(idAbilita);
							abilitaDichiarate.add(gestoreAbilita.getAbilita(id));						
						} catch (NumberFormatException numberFormatE) {
							request.setAttribute("messaggio", Comunicazione.erroreModificaAbilita());
							dispatcher = request.getRequestDispatcher("Abilita");
							dispatcher.forward(request, response);
						}
					}
				}
				if(!gestoreUser.modificaAbilitaDichiarate(nickname, abilitaDichiarate)) {
					request.setAttribute("messaggio", Comunicazione.erroreModificaAbilita());
					dispatcher = request.getRequestDispatcher("Abilita");
					dispatcher.forward(request, response);				
				} else {
					request.setAttribute("messaggio", Comunicazione.confermaModificaAbilita());
					dispatcher = request.getRequestDispatcher("PaginaUser");
					dispatcher.forward(request, response);
				}
			} catch (NamingException e) {
				request.setAttribute("messaggio", Comunicazione.erroreModificaAbilita());
				dispatcher = request.getRequestDispatcher("Abilita");
				dispatcher.forward(request, response);			
			}
		}
	}
}
