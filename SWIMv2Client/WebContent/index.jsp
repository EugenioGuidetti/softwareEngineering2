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
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>SWIMv2</title>
		<link rel="stylesheet" href="CSS/style.css">
	</head>
	<body>
		<div id="pagina">
			<div id="logo">
				<img src="Immagini/logo.png">
			</div>
			<%
				Messaggio messaggio = (Messaggio) request.getAttribute("messaggio");
				if(messaggio != null) {
			%>
					<div id="messaggio<%= messaggio.getTipo().toString() %>">
						<center><%= messaggio.getTesto() %></center>
					</div>
			<%
				}
			%>
			<div id="body">
				<div id="boxLeft">
					<div id="titoloBox">
						<center>Effettua la registrazione</center>
					</div>
					<form action="Registrazione" method="post">
						<div id="informazioniBox">
							<p>
								<label for="rNome">Nome:</label>
								<input id="rNome" name="rNome" type="text" maxlength="20" required="required">
							</p>
							<p>
								<label for="rCognome">Cognome:</label>
								<input id="rCognome" name="rCognome" type="text" maxlength="20" required="required">
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
									    String città = null;
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
								                città = reader.nextLine();
								    %>
								    			<option value=" <%= città %> "><%= città %></option>
								    <%
								            }
								        }
								    %>
								</select>
							</p>
							<p>
								<label for="rNickname">Nickname:</label>
								<input id="rNickname" name="rNickname" type="text" maxlength="20" required="required">
							</p>
							<p>
								<label for="rPassword">Password:</label>
								<input id="rPassword" name="rPassword" type="password" maxlength="20" required="required">
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
					<form action="" method="post">
						<div id="informazioniBox">
							<p>
								<label for="nickname">Nickname:</label>
								<input id="nickname" name="nickname" type="text" maxlength="20" required="required">
							</p>
							<p>
								<label for="password">Password:</label>
								<input id="password" name="password" type="password" maxlength="20" required="required">
							</p>
						</div>
						<center>
							<input id="pulsante" type="submit" value="Login">
						</center>
					</form>
				</div>
				<div id="boxRight">
					<div id="titoloBox">
						<center>Accedi senza regstrarti</center>
					</div>
					<form action="" method="post">
						<div id="informazioniBox">
							<p></p>
						</div>
						<center>
							<input id="pulsante" type="submit" value="Accesso Guest">
						</center>
					</form>
				</div>
			</div>
		</div>		
	</body>
</html>