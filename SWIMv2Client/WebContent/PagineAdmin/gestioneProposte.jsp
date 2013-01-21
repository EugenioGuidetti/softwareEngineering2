<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="entity.PropostaAbilita" %>
<%@ page import="java.util.List" %>
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
							<button type="button">&rarr; Gestione Proposte</button>
						</a>
					</li>
					<li>
						<a>
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
						<center>Proposte abilit&agrave; non visionate</center>
					</div>					
					<form action="GestioneProposteActions" method="post">
						<div id="informazioniBox">
							<%
								@SuppressWarnings("unchecked")
								List<PropostaAbilita> proposteNonVisionate = 
									(List<PropostaAbilita>) request.getAttribute("proposteNonVisionate");
								for(PropostaAbilita proposta: proposteNonVisionate) {
							%>
									<div id="proposta">
										<input name="proposteScelte" type="checkbox" value="<%= proposta.getId() %>">
										<div id="corpoProposta">
											<strong><%= proposta.getNome() %></strong><br>
											<i>&ldquo;<%= proposta.getDescrizione() %>&rdquo;</i><br>
											proposta da @<%= proposta.getUserProponente().getNickname() %>
										</div>
									</div>
							<%
								}
							%>
							<div id="proposteActions">
								<input id="azione1" name="azione" type="radio" value="visiona">
								<label for="azione1">Segna come visionata/e</label>
								<br>
								<input id="azione2" name="azione" type="radio" value="cancella">
								<label for="azione2">Cancella la/e proposta/e</label>
							</div>
						</div>
						<center>
							<input id="pulsante" type="submit" value="Procedi">
						</center>
					</form>
				</div>
				<div id="boxRight">
					<div id="titoloBox">
						<center>Proposte abilit&agrave; visionate</center>
					</div>
					<form action="GestioneProposteActions" method="post">
						<div id="informazioniBox">
							<%
								@SuppressWarnings("unchecked")
								List<PropostaAbilita> proposteVisionate = 
									(List<PropostaAbilita>) request.getAttribute("proposteVisionate");
								for(PropostaAbilita proposta: proposteVisionate) {
							%>
									<div id="proposta">
										<input name="proposteScelte" type="checkbox" value="<%= proposta.getId() %>">
										<div id="corpoProposta">
											<strong><%= proposta.getNome() %></strong><br>
											<i>&ldquo;<%= proposta.getDescrizione() %>&rdquo;</i><br>
											proposta da @<%= proposta.getUserProponente().getNickname() %>
										</div>
									</div>
							<%
								}
							%>
							<input name="azione" type="hidden" value="cancella">							
						</div>
						<center>
							<input id="pulsante" type="submit" value="Cancella ">
						</center>
					</form>
				</div>
			</div>
		</div>
	</body>
</html>