<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="entity.Abilita" %>
<%@ page import="entity.User" %>
<%@ page import="utility.Messaggio" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Insert title here</title>
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
			<div id="menu">
				<ul>
					<li>
						<a href="PaginaAdmin">
							<button type="button">Pagina Admin</button>
						</a>
					</li>
					<li>
						<a href="GestioneProposte">
							<button type="button">Gestione Proposte</button>
						</a>
					</li>
					<li>
						<a href="MonitorSistema">
							<button type="button">&rarr; Monitor Sistema</button>
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
						<center>Abilit&agrave; disponibili</center>
					</div>
					<div id="informazioniBox">
						<%
							@SuppressWarnings("unchecked")
							List<Abilita> abilitaSistema = 
								(List<Abilita>) request.getAttribute("abilitaSistema");
							for(Abilita abilita: abilitaSistema) {
						%>
								<div id="abilita">
									<div id="corpoAbilita">
										<strong><%= abilita.getNome() %></strong>
										<br>
										<i>&ldquo;<%= abilita.getDescrizione() %>&rdquo;</i>
									</div>
									<div id="iconaAbilita">
										<img src="<%= abilita.getIconaPath() %>" width="65" height="65">
									</div>
								</div>
						<%
							}
						%>
					</div>
				</div>
				<div id="boxRight">
					<div id="titoloBox">
						<center>User registrati</center>
					</div>
					<div id="informazioniBox">
						<%
							@SuppressWarnings("unchecked")
							List<User> userSistema = 
								(List<User>) request.getAttribute("userSistema");
							for(User user: userSistema) {
						%>
								<div id="infoProfilo">							
									<div id="avatar">
										<img src="<%= user.getAvatarPath() %>" width="65" height="65">
									</div>
									<div id="testo">
										<%= user.getNome() %> <%= user.getCognome() %>
										<br>
										@<%= user.getNickname() %>
									</div>
								</div>
						<%
							}
						%>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>