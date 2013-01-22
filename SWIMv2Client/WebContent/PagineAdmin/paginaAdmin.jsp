<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="utility.Messaggio" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>SWIMv2 - Admin</title>
		<link rel="stylesheet" href="/SWIMv2Client/CSS/style.css">
	</head>
	<body>
		<div id="pagina">
			<div id="logo">
				<img src="/SWIMv2Client/Immagini/logo.png" width="235" height="107">
			</div>
			<%
				Messaggio messaggio = null;
				if(request.getAttribute("messaggio") != null) {
					messaggio = (Messaggio) request.getAttribute("messaggio");
				} else if(request.getSession().getAttribute("messaggio") != null) {
					messaggio = (Messaggio) request.getSession().getAttribute("messaggio");
					request.getSession().removeAttribute("messaggio");
				}
				if(messaggio != null) {
			%>
					<div id="messaggio<%= messaggio.getTipo().toString() %>">
						<center><%= messaggio.getTesto() %></center>
					</div>
			<%
				}
			%>
			<div id="menuAdmin">
				<ul>
					<li>
						<a href="PaginaAdmin">
							<button type="button">&rarr; Pagina Admin</button>
						</a>
					</li>
					<li>
						<a href="GestioneProposte">
							<button type="button">Gestione Proposte</button>
						</a>
					</li>
					<li>
						<a href="MonitorSistema">
							<button type="button">Monitor Sistema</button>
						</a>
					</li>
					<li>
						<a href="Logout">
							<button type="button">Logout</button>
						</a>
					</li>
				</ul>
			</div>
			<div id="body">
				<div id="boxLeft">
					<div id="titoloBox">
						<center>Informazioni personali</center>
					</div>
					<div id="informazioniBox">
						<div id="infoProfilo">							
							<div id="avatar">
								<img src="<%= request.getAttribute("avatar") %>" width="65" height="65">
							</div>
							<div id="testo"><%= request.getAttribute("nome") %> <%= request.getAttribute("cognome") %><br>@<%= request.getSession().getAttribute("nickname") %></div>
						</div>
						<div id="altreInfoProfilo">Email: <%= request.getAttribute("email") %></div>
					</div>
					<center>
						<a href="PagineAdmin/modificaInfoAdmin.jsp">
							<button id="pulsante" type="button">Modifica informazioni</button>
						</a>
					</center>
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