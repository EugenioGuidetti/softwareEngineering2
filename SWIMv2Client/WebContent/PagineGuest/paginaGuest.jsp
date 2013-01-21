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
			<div id="menuGuest">
				<ul>
					<li>
						<a href="PaginaGuest">
							<button type="button">&rarr; PaginaGuest</button>
						</a>
					</li>
					<li>
						<a href="Logout">
							<button type="button">Home Page</button>
						</a>
					</li>
				</ul>
			</div>
			<div id="body">
				<div id="boxLeft">
					<div id="titoloBox">
						<center>Ricerca</center>
					</div>
					<form action="RicercaGuest" method="post">
						<div id="informazioniBox">
							<center>Filtro sul nome:</center>
							<p>
								<label for="nome">Nome:</label>
								<input id="nome" name="nome" type="text" maxlength="255">
							</p>
							<p>
								<label for="cognome">Cognome:</label>
								<input id="cognome" name="cognome" type="text" maxlength="255">
							</p>
							<div id="filtroAbilita">
								<center>Filtro per abilita:</center>
								<br>
								<%
									@SuppressWarnings("unchecked")
									List<Abilita> abilitaSistema = 
										(List<Abilita>) request.getAttribute("abilitaSistema");
									for(Abilita abilita: abilitaSistema) {
								%>
										<label for="abilita<%= abilita.getId() %>">
											<strong><%= abilita.getNome() %></strong>
										</label>
										<input id="abilita<%= abilita.getId() %>" name="abilita" type="radio" value="<%= abilita.getId() %>">
										<br>
								<%
									}
								%>
							</div>
							<div id="filtriRicerca">
								<br>
								<center>Scegli un filtro:</center>
								<br>
								<input id="filtro1" name="filtroRicerca" type="radio" value="perNome">
								<label for="filtro1">Ricerca per nome</label>
								<br>
								<input id="filtro2" name="filtroRicerca" type="radio" value="perAbilita">
								<label for="filtro2">Ricerca per abilita</label>
							</div>
						</div>
						<center>
							<input id="pulsante" type="submit" value="Esegui la ricerca">
						</center>
					</form>
				</div>
				<div id="boxRight">
					<div id="titoloBox">
						<center>Risultati della ricerca</center>
					</div>
					<div id="informazioniBox">
						<%
							@SuppressWarnings("unchecked")
							List<User> risultatiRicerca = 
								(List<User>) request.getAttribute("risultatiRicerca");
							if(risultatiRicerca != null) {
								for(User user: risultatiRicerca) {
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
							}
						%>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>