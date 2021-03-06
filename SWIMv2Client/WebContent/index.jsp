<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="java.util.Scanner" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.FileNotFoundException" %>
<%@ page import="java.util.regex.Pattern" %>
<%@ page import="java.util.regex.Matcher" %>
<%@ page import="utility.Messaggio" %>
<%@ page import="utility.TipoMessaggio" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="header.html" %>
	</head>
	<body>
		<div id="pagina">
			<%@ include file="logoGrande.html" %>
			<%@ include file="gestioneMessaggio.jsp" %>
			<div id="body">
				<div id="boxLeft">
					<div id="titoloBox">
						<center>Effettua la registrazione</center>
					</div>
					<form action="/SWIMv2Client/Registrazione" method="post">
						<div id="informazioniBox">
							<p>
								<label for="rNome">Nome:</label>
								<input id="rNome" name="rNome" type="text" maxlength="255" required="required">
							</p>
							<p>
								<label for="rCognome">Cognome:</label>
								<input id="rCognome" name="rCognome" type="text" maxlength="255" required="required">
							</p>
							<p>
								<label for="rSesso">Sesso:</label>
								<select id="rSesso" name="rSesso">
									<option value="Femmina">Femmina</option>
									<option value="Maschio">Maschio</option>
								</select>
							</p>
							<p>
								<label for="rAnnoNascita">Anno di nascita:</label>
								<select id="rAnnoNascita" name="rAnnoNascita">
									<%
										GregorianCalendar now = new GregorianCalendar();
										for(int i = now.get(GregorianCalendar.YEAR); i > 1850; i--) {
									%>
											<option value="<%= i %>"><%= i %></option>
									<%
										}
									%>
								</select>
							</p>
							<p>
								<label for="rCitta">Citt&agrave;:</label>
								<select id="rCitta" name="rCitta">
									<%
									    String citt� = null;
								        Scanner reader = null;
								        String filePath = "/WEB-INF/comuniItaliani.txt";
								        File file = new File(getServletContext().getRealPath(filePath));
								        try {
								            reader = new Scanner(file);
								        } catch(FileNotFoundException e) {
								        	System.out.println(e.getMessage());
								        }
								        if(reader != null) {
								            while (reader.hasNextLine()) {
								                citt� = reader.nextLine();
								    %>
								    			<option value=" <%= citt� %> "><%= citt� %></option>
								    <%
								            }
								        }
								    %>
								</select>
							</p>
							<p>
								<label for="rNickname">Nickname:</label>
								<input id="rNickname" name="rNickname" type="text" maxlength="25" required="required">
							</p>
							<p>
								<label for="rPassword">Password:</label>
								<input id="rPassword" name="rPassword" type="password" maxlength="255" required="required">
							</p>
							<p>
								<label for="rEmail">Email:</label>
								<input id="rEmail" name="rEmail" type="text" maxlength="255" required="required">
							</p>
						</div>
						<center>
							<input id="pulsante" type="submit" value="Continua">
						</center>
					</form>
				</div>
				<div id="boxRight">
					<div id="titoloBox">
						<center>Esegui l'accesso</center>
					</div>
					<form action="/SWIMv2Client/Login" method="post">
						<div id="informazioniBox">
							<p>
								<label for="nickname">Nickname:</label>
								<input id="nickname" name="nickname" type="text" maxlength="25" required="required">
							</p>
							<p>
								<label for="password">Password:</label>
								<input id="password" name="password" type="password" maxlength="255" required="required">
							</p>
						</div>
						<div id="recuperaPassword">
 							Hai dimenticato la password? <a href="recuperaPassword.jsp">clicca qui per resettarla </a> 
 						</div>
						<center>
							<input id="pulsante" type="submit" value="Login">
						</center>
					</form>
				</div>
				<div id="boxRight">
					<div id="titoloBox">
						<center>Accedi senza registrarti</center>
					</div>
					<div id="informazioniBox">
						<p></p>
					</div>
					<center>
						<a href="/SWIMv2Client/PaginaGuest">
							<button id=pulsante type="button">Accesso Guest</button>
						</a>
					</center>
				</div>
			</div>
		</div>
		
		<%@ include file="credits.html" %>
			
	</body>
</html>