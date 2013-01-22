<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="utility.Messaggio" %>

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
				<img src="Immagini/logo.png" width="235" height="107">
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
						<center>Modifica le informazioni personali</center>
					</div>
					<form action="ModificaInfoAdmin" method="post">
						<div id="informazioniBox">
							<p>
								<label for="nNome">Nuovo nome:</label>
								<input id="nNome" name="nNome" type="text" maxlength="255">
							</p>
							<p>
								<label for="nCognome">Nuovo cognome:</label>
								<input id="nCognome" name="nCognome" type="text" maxlength="255">
							</p>
							<p>
								<label for="nPassword">Nuova password:</label>
								<input id="nPassword" name="nPassword" type="password" maxlength="255">
							</p>
							<p>
								<label for="nEmail">Nuova email:</label>
								<input id="nEmail" name="nEmail" type="text" maxlength="255">
							</p>							
						</div>
						<center>
							<input id="pulsante" type="submit" value="Modifica">
						</center>
					</form>
				</div>
				<div id="boxRight">
					<div id="titoloBox">
						<center>Indicazioni</center>
					</div>
					<div id="indicazioni">
						<p>Testo sul fatto che vanno inseriti solo i dati nuovi che si vogliono aggiornare</p>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>