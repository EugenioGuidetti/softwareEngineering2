<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="utility.Messaggio" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>SWIMv2 - Admin</title>
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
			<div id="menuAdmin">
				<!-- scrivere il men� per l'admin -->
			</div>
			<div id="body">
				<div id="boxLeft">
					<div id="titoloBox">
						<center>Informazioni personali</center>
					</div>
					<div id="informazioniBox">
						<div id="infoAdmin">							
							<div id="avatar">
								<img src="<%= request.getAttribute("avatar") %>" width="70" height="70">
							</div>
							<div id="testo"><%= request.getAttribute("nome") %> <%= request.getAttribute("cognome") %><br>&rarr; <%= request.getSession().getAttribute("nickname") %></div>
						</div>
						<div id="altreInfoAdmin">Email:<br><%= request.getAttribute("email") %></div>
					</div>
				</div>
				<div id="boxRight">
					<div id="titoloBox">
						<center>Crea una nuova abilit&agrave;</center>
					</div>
					<form action="CreaAbilita" method="post">
						<div id="informazioniBox">
							<p>
								<label for="nome">Nome:</label>
								<input id="nome" name="nome" type="text" maxlength="255" required="required">
							</p>
							<p>
								<label for="descrizione">Descrizione:</label>
								<textarea id="descrizione" name="descrizione" maxlength="140" rows="7" required="required"></textarea>
							</p>
							<p>
								<label for="icona">Icona:</label>
								<input id="icona" name="icona" type="file" accept="image/*">
							</p>
						</div>
						<center>
							<input id="pulsante" type="submit" value="Crea abilit&agrave;">
						</center>
					</form>
				</div>
			</div>	
		</div>
	</body>
</html>