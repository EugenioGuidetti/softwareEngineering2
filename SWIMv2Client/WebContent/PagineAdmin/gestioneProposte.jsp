<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="entity.PropostaAbilita" %>
<%@ page import="java.util.List" %>

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
					<div id="informazioniBox">
						<form action="" method="post">
							<%
								@SuppressWarnings("unchecked")
								List<PropostaAbilita> proposteNonVisionate = 
									(List<PropostaAbilita>) request.getAttribute("proposteNonVisionate");
								for(PropostaAbilita proposta: proposteNonVisionate) {
							%>
									<div id="proposta">
										<input name="proposteScelte" type="checkbox" value="<%= proposta.getId() %>">
										<div id="corpoProposta">
											@<%= proposta.getUserProponente().getNickname() %> ha proposto l'abilit&agrave;:
											<strong><%= proposta.getNome() %></strong><br>
											<i>&ldquo;<%= proposta.getDescrizione() %>&rdquo;</i>
										</div>
									</div>
							<%
								}
							%>
							<!-- aggiungere la scelta visiona/cancella -->
						</form>
					</div>
				</div>
				<div id="boxRight">
					<div id="titoloBox">
						<center>Proposte abilit&agrave; visionate</center>
					</div>
					<div id="informazioniBox">
						<%
							
						%>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>