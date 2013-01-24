<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="utility.Messaggio" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>SWIMv2</title>
		<link rel="stylesheet" href="/SWIMv2Client/CSS/style.css">
	</head>
	<body>
		<div id="pagina">
			<%@ include file="../logoPiccolo.html" %>
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
						<center>Proponi una nuova abilita</center>
					</div>
					<form action="/SWIMv2Client/ProponiAbilita" method="post">
						<div id="informazioniBox">
							<p>
								<label for="nome">Nome:</label>
								<input id="nome" name="nome" type="text" maxlength="255" required="required">
							</p>
							<p>
								<label for="descrizione">Descrizione:</label>
								<textarea id="descrizione" name="descrizione" maxlength="140" rows="7" required="required"></textarea>
							</p>
						</div>
						<center>
							<input id="pulsante" type="submit" value="Invia proposta">
						</center>
					</form>
				</div>
				<div id="boxRight">
					<div id="titoloBox">
						<center>Indicazioni</center>
					</div>
					<div id="indicazioni">
						<p>
							Qui puoi proporre all'Admin una tua idea per una nuova abilit&agrave;.
							<br>
							Compila i campi inserendo il nome e la descrizione che hai pensato per l'abilit&agrave;.
						</p>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>