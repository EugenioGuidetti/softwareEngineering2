package servlet;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.Context;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import session.GestoreAdminRemote;

public class ModificaInfoAdmin extends HttpServlet {
	
	private RequestDispatcher dispatcher;
	private Context context;
	private GestoreAdminRemote gestoreAdmin;
	private Pattern pattern;
	private Matcher matcher;
	private String nickname;
	private String nome;
	private String cognome;
	private String password;
	private String email;
	
	private static final long serialVersionUID = 1L;

    public ModificaInfoAdmin() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		nickname = (String) request.getSession().getAttribute("nickname");
		System.out.println("nickname: " + nickname);
		nome = request.getParameter("nNome");
		System.out.println("nome: " + nome);
		cognome = request.getParameter("cognome");
		System.out.println("cognome: " + cognome);
		password = request.getParameter("nPassword");
		System.out.println("password: " + password);
		email = request.getParameter("nEmail");
		System.out.println("email: " + email);
	}

}
