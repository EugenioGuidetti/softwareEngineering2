package servlet;

import java.io.IOException;
import java.util.ArrayList;
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
import entity.Abilita;
import entity.Amicizia;
import entity.ReputazioneAbilita;
import entity.User;
import session.GestoreAbilitaRemote;
import session.GestoreAmiciziaRemote;
import session.GestoreUserRemote;
import session.GestoreUtilitiesRemote;
import utility.Comunicazione;
import utility.Utilita;

public class PaginaUser extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private RequestDispatcher dispatcher;
	private Context context;
	private GestoreUserRemote gestoreUser;
	private GestoreAmiciziaRemote gestoreAmicizia;
	private GestoreAbilitaRemote gestoreAbilita;
	private GestoreUtilitiesRemote gestoreUtilities;
	private String nickname;
	private User user;
	private List<Amicizia> amicizie;
	private List<User> amici;
	private List<Abilita> abilitaDichiarate;
	private Map<Abilita, ReputazioneAbilita> abilitaValutate;

	public PaginaUser() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if( Utilita.controlloSessione(request, response)){
			//esiste una sessione utente

			nickname = (String) request.getSession().getAttribute("nickname");
			try {
				context = new InitialContext();
				gestoreUser = (GestoreUserRemote) context.lookup("GestoreUserJNDI");
				gestoreAmicizia = (GestoreAmiciziaRemote) context.lookup("GestoreAmiciziaJNDI");
				gestoreAbilita = (GestoreAbilitaRemote) context.lookup("GestoreAbilitaJNDI");
				user = gestoreUser.getUser(nickname);
				request.setAttribute("nomeCompleto", user.getNome() + " " + user.getCognome());
				request.setAttribute("avatar", user.getAvatarPath());
				request.setAttribute("sesso", user.getSesso());
				request.setAttribute("annoNascita", user.getAnnoNascita());
				request.setAttribute("citta", user.getCitta());
				request.setAttribute("email", user.getEmail());
				amicizie = gestoreAmicizia.getAmicizieAllacciate(nickname);
				if(!amicizie.isEmpty()) {
					amici = new ArrayList<User>();
					for(Amicizia amicizia: amicizie) {
						amici.add(amicizia.getUserDestinatario());
					}
					request.setAttribute("amici", amici);
				}
				abilitaDichiarate = gestoreAbilita.getAbilitaUser(nickname);
				if(!abilitaDichiarate.isEmpty()) {
					gestoreUtilities = (GestoreUtilitiesRemote) context.lookup("GestoreUtilitiesJNDI");
					abilitaValutate = new HashMap<Abilita, ReputazioneAbilita>();
					for(Abilita abilita: abilitaDichiarate) {
						abilitaValutate.put
						(abilita, gestoreUtilities.getReputazioneAbilita(nickname, abilita.getId()));
					}
					request.setAttribute("abilitaDichiarate", abilitaDichiarate);
					request.setAttribute("abilitaValutate", abilitaValutate);
				}
			} catch (NamingException e) {
				request.setAttribute("messaggio", Comunicazione.erroreCaricamentoInformazioni());
			} finally {
				request.setAttribute("paginaAttuale", "paginaPersonale");
				dispatcher = request.getRequestDispatcher("PagineUser/paginaUser.jsp");
				dispatcher.forward(request, response);			
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
