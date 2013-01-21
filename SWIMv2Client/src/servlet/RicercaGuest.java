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
import session.GestoreAbilitaRemote;
import session.GestoreUserRemote;
import utility.Comunicazione;

public class RicercaGuest extends HttpServlet {
	
	private static final long serialVersionUID = 1L;	
	private static final String PER_NOME = "perNome";
	private static final String PER_ABILITA = "perAbilita";
	
	private RequestDispatcher dispatcher;
	private Context context;
	private GestoreUserRemote gestoreUser;
	private GestoreAbilitaRemote gestoreAbilita;
	private String nome;
	private String cognome;
	private String abilita;
	private String filtroRicerca;

    public RicercaGuest() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		nome = request.getParameter("nome");
		cognome = request.getParameter("cognome");
		abilita = request.getParameter("abilita");
		filtroRicerca = request.getParameter("filtroRicerca");

		try {
			context = new InitialContext();
			gestoreUser = (GestoreUserRemote) context.lookup("GestoreUserJNDI");
			gestoreAbilita = (GestoreAbilitaRemote) context.lookup("GestoreAbilitaJNDI");
			request.setAttribute("abilitaSistema", gestoreAbilita.getAbilitaSistema());
			
			if(filtroRicerca != null) {
				//ha selezionato un tipo di ricerca
				
				if(filtroRicerca.equals(PER_ABILITA) && abilita != null) {
					//ha selezionato la ricerca per abilità scegliendo un'abilità
					
					try {
						long id = Long.parseLong(abilita);
						request.setAttribute("risultatiRicerca", gestoreUser.ricercaPerAbilita(id));
					} catch (NumberFormatException numberFormatE) {
						request.setAttribute("messaggio", Comunicazione.erroreRicerca());					
					}
				}
				
				if(filtroRicerca.equals(PER_NOME)) {
					
					//ha selezionato la ricerca per nome
					if(!nome.equals("") && !cognome.equals("")) {
						//ha inserito sia nome che cognome
						request.setAttribute("risultatiRicerca", gestoreUser.ricercaPerNomeCognome(nome, cognome));
					}
					
					if(!nome.equals("") && cognome.equals("")) {
						//ha inserito solo il nome
						request.setAttribute("risultatiRicerca", gestoreUser.ricercaPerNome(nome));
					}
					
					if(nome.equals("") && !cognome.equals("")) {
						//ha inserito solo il cognome
						request.setAttribute("risultatiRicerca", gestoreUser.ricercaPerCognome(cognome));
					}
				}
			}
			dispatcher = request.getRequestDispatcher("PagineGuest/paginaGuest.jsp");
			dispatcher.forward(request, response);
		} catch (NamingException e) {
			request.setAttribute("messaggio", Comunicazione.erroreRicerca());
			dispatcher = request.getRequestDispatcher("PagineGuest/paginaGuest.jsp");
			dispatcher.forward(request, response);
		}
	}

}
