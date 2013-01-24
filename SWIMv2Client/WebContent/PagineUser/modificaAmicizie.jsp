<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="entity.Amicizia" %>
<%@ page import="entity.User" %>
<%@ page import="utility.Messaggio" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>SWIMv2Client</title>
		<link rel="stylesheet" href="/SWIMv2Client/CSS/style.css">
	</head>
	<body>
		<div id="pagina">
		<div id="logo">
			<img src="/SWIMv2Client/Immagini/logo.png" width="235" height="107">
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
					<center>Modifica le amicizie allacciate</center>
				</div>
				<form action="/SWIMv2Client/RimuoviAmicizie" method="post">
					<div id="informazioniBox">
						<%
							@SuppressWarnings("unchecked")
							List<Amicizia> amicizie = 
								(List<Amicizia>) request.getAttribute("amicizie");
							if(!amicizie.isEmpty()) {
								for(Amicizia amicizia: amicizie) {
									User amico = amicizia.getUserDestinatario();
						%>
									<div id="amicizia">
										<input name="amicizieScelte" type="checkbox" value="<%= amicizia.getId() %>">										
										<div id="testo">
											<%= amico.getNome() %> <%= amico.getCognome() %>
											<br>
											@<%= amico.getNickname() %>
										</div>
										<div id="avatar">
											<img src="<%= amico.getAvatarPath() %>" width="65" height="65">
										</div>
									</div>
						<%
								}
							}
						%>
					</div>
					<center>
						<input id="pulsante" type="submit" value="Rimuovi">
					</center>
				</form>
			</div>
			<div id="boxRight">
				<div id="titoloBox">
					<center>Indicazioni</center>
				</div>
				<div id="indicazioni">
					<p>Testo sul fatto che dopo aver terminato delle amicizie sar� sempre possibile instaurarle di nuovo in un secondo momento</p>
				</div>
			</div>
		</div>
		</div>
	</body>
</html>