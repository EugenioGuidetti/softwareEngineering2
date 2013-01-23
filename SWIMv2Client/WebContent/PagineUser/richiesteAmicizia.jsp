<%@page import="java.util.Calendar"%>
<%@page import="java.util.GregorianCalendar"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="entity.Amicizia" %>
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
			<div id="logo">
				<img src="/SWIMv2Client/Immagini/logo.png">
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
			<div id="menuUser">
				<ul>
					<li>
						<a href="/SWIMv2Client/PaginaUser">
							<button type="button">Pagina Personale</button>
						</a>
					</li>
					<li>
						<a href="/SWIMv2Client/RichiesteAmicizia">
							<button type="button">&rarr; Richieste Amicizia</button>
						</a>
					</li>
					<li>
						<a href="/SWIMv2Client/RichiesteAiuto">
							<button type="button">Richieste Aiuto</button>
						</a>
					</li>
					<li>
						<a href="/SWIMv2Client/StoricoAiuti">
							<button type="button">Storico Aiuti</button>
						</a>
					</li>
					<li>
						<a href="/SWIMv2Client/RicercaUser">
							<button type="button">Ricerca</button>
						</a>
					</li>
					<li>
						<a href="/SWIMv2Client/Logout">
							<button type="button">Logout</button>
						</a>
					</li>
				</ul>
			</div>
			<div id="body">
				<div id="boxLeft">
					<div id="titoloBox">
						<center>Richieste di amicizia inviate</center>
					</div>
					<div id="informazioniBox">
						<%
							@SuppressWarnings("unchecked")
							List<Amicizia> richiesteInviate = 
								(List<Amicizia>) request.getAttribute("richiesteInviate");
							if(!richiesteInviate.isEmpty()) {
								for(Amicizia richiesta: richiesteInviate) {
									Calendar data = richiesta.getMomentoRichiesta();
						%>
									<div id="richiestaAmiciziaInviata">
										<div id="corpoRichiesta">
										Il <%= data.get(GregorianCalendar.DAY_OF_MONTH) %>.<%= data.get(GregorianCalendar.MONTH) + 1 %>.<%= data.get(GregorianCalendar.YEAR) %> hai inviato una richiesta di amicizia a <strong>@<%= richiesta.getUserDestinatario().getNickname() %></strong>, che non ha ancora ricevuto risposta
										</div>
									</div>
						<%
								}
							}
						%>
					</div>
				</div>
				<div id="boxRight">
					<div id="titoloBox">
						<center>Richieste di amicizia ricevute</center>
					</div>
					<form action="AmicizieActions" method="post">
						<div id="informazioniBox">
							<%
								@SuppressWarnings("unchecked")
								List<Amicizia> richiesteRicevute = 
									(List<Amicizia>) request.getAttribute("richiesteRicevute");
								if(!richiesteRicevute.isEmpty()) {
									for(Amicizia richiesta: richiesteRicevute) {
							%>
										<div id="richiestaAmiciziaRicevuta">
											<input name="amicizieScelte" type="checkbox" value="<%= richiesta.getId() %>">
											<div id="corpoRichiesta">
												Hai ricevuto una richiesta di amicizia da parte di <strong>@<%= richiesta.getUserRichiedente().getNickname() %></strong>
											</div>											
										</div>
							<%
									}
								}
							%>
							<div id="amicizieActions">
								<br>
								<input id="azione1" name="azione" type="radio" value="accetta">
								<label for="azione1"> Accetta la/e richiesta/e</label>
								<br>
								<input id="azione2" name="azione" type="radio" value="rifiuta">
								<label for="azione2">Rifiuta la/e richiesta/e</label>
							</div>
						</div>
						<center>
							<input id="pulsante" type="submit" value="Procedi">
						</center>
					</form>
				</div>
			</div>
		</div>
	</body>
</html>