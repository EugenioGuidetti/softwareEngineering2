package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entity.User;
import session.GestoreUserRemote;
import utility.Comunicazione;

public class RicercaUser extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final String PER_NOME = "perNome";
	private static final String PER_ABILITA = "perAbilita";
	private static final String SISTEMA = "sistema";
	private static final String AMICI = "amici";

	private RequestDispatcher dispatcher;
	private Context context;
	private GestoreUserRemote gestoreUser;
	private String nickname;
	private String nome;
	private String cognome;
	private String abilita;
	private String dominioRicerca;
	private String filtroRicerca;
	private List<User> risultatiRicerca;
	
    public RicercaUser() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		nickname = (String) request.getSession().getAttribute("nickname");
		nome = request.getParameter("nome");
		cognome = request.getParameter("cognome");
		abilita = request.getParameter("abilita");
		dominioRicerca = request.getParameter("dominioRicerca");
		filtroRicerca = request.getParameter("filtroRicerca");
		request.setAttribute("nomeCercato", nome);
		request.setAttribute("cognomeCercato", cognome);
		request.setAttribute("abilitaCercata", abilita);
		request.setAttribute("filtroUsato", filtroRicerca);
		request.setAttribute("dominioScelto", dominioRicerca);
		try {
			context = new InitialContext();
			gestoreUser = (GestoreUserRemote) context.lookup("GestoreUserJNDI");			
			risultatiRicerca = new ArrayList<User>();
			
			if(dominioRicerca != null && filtroRicerca != null) {
				//ha selezionato un domino ed un tipo di ricerca
				
				if(filtroRicerca.equals(PER_ABILITA) /*&& abilita != null*/) {
					// ha selezionato la ricerca per abilità
					
					if(abilita.equals("")) {
						request.setAttribute("messaggio", Comunicazione.erroreRicercaAbilita());
					} else {
						try {
							long id = Long.parseLong(abilita);
							if(dominioRicerca.equals(SISTEMA)) {
								//ha scelto come dominio tutti gli user
								
								risultatiRicerca = gestoreUser.ricercaPerAbilita(id);
							}
							if(dominioRicerca.equals(AMICI)) {
								//ga scelto come dominio gli amici
								
								risultatiRicerca = gestoreUser.ricercaAmiciPerAbilita(nickname, id);
							}
						} catch (NumberFormatException numberFormatE) {
							request.setAttribute("messaggio", Comunicazione.erroreRicerca());					
						}						
					}
				}
				
				if(filtroRicerca.equals(PER_NOME)) {
					//ha selezionato la ricerca per nome
					
					if(!nome.equals("") && !cognome.equals("")) {
						//ha inserito sia nome che cognome
						
						if(dominioRicerca.equals(SISTEMA)) {
							//ha scelto come dominio tutti gli user
							risultatiRicerca = gestoreUser.ricercaPerNomeCognome(nome, cognome);
						}
						
						if(dominioRicerca.equals(AMICI)) {
							//ha scelto come dominio gli amici
							risultatiRicerca = gestoreUser.ricercaAmiciPerNomeCognome(nickname, nome, cognome);
						}
					}
					
					if(!nome.equals("") && cognome.equals("")) {
						//ha inserito solo il nome
						
						if(dominioRicerca.equals(SISTEMA)) {
							//ha scelto come dominio tutti gli user
							risultatiRicerca = gestoreUser.ricercaPerNome(nome);
						}
						
						if(dominioRicerca.equals(AMICI)) {
							//ha scelto come dominio gli amici
							risultatiRicerca = gestoreUser.ricercaAmiciPerNome(nickname, nome);
						}
					}
					
					if(nome.equals("") && !cognome.equals("")) {
						//ha inserito solo il cognome
						
						if(dominioRicerca.equals(SISTEMA)) {
							//ha scelto come dominio tutti gli user
							risultatiRicerca = gestoreUser.ricercaPerCognome(cognome);
						}
						
						if(dominioRicerca.equals(AMICI)) {
							//ha scelto come dominio gli amici
							risultatiRicerca = gestoreUser.ricercaAmiciPerCognome(nickname, cognome);
						}
					}
				}
			}			
			if(!risultatiRicerca.isEmpty()) {
				//rimuovo lo user che ha eseguito la ricerca dai risultati
				
				risultatiRicerca.remove(gestoreUser.getUser(nickname));
			}
			request.setAttribute("risultatiRicerca", risultatiRicerca);
		} catch (NamingException e) {
			request.setAttribute("messaggio", Comunicazione.erroreRicerca());
		} finally {
			dispatcher = request.getRequestDispatcher("Ricerca");
			dispatcher.forward(request, response);
		}
	}

}
