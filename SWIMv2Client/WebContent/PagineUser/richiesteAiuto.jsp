<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="entity.Aiuto" %>
<%@ page import="entity.User" %>
<%@ page import="entity.Abilita" %>
<%@ page import="utility.Messaggio" %>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.GregorianCalendar"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="../header.html" %>	
	</head>
	<body>
		<div id="pagina">
			<%@ include file="../logoPiccolo.html" %>			
			<%@ include file="../gestioneMessaggio.jsp" %>
			<%@ include file="menuUser.jsp" %>
			<div id="body">
				<div id="boxLeft">
					<div id="titoloBox">
						<center>Richieste di aiuto inviate</center>
					</div>
					<div id="informazioniBox">
						<%
							@SuppressWarnings("unchecked")
							List<Aiuto> richiesteInviate = 
								(List<Aiuto>) request.getAttribute("richiesteInviate");
							if(!richiesteInviate.isEmpty()) {
								for(Aiuto richiesta: richiesteInviate) {
									User user = richiesta.getUserDestinatario();
									Abilita abilita = richiesta.getAbilitaRichiesta();
									Calendar data = richiesta.getMomentoRichiesta();
						%>
									<div id="richiestaInviata">
										<div id="corpoRichiesta">
											Il <%= data.get(GregorianCalendar.DAY_OF_MONTH) %>.<%= data.get(GregorianCalendar.MONTH) + 1 %>.<%= data.get(GregorianCalendar.YEAR) %> hai inviato una richiesta di aiuto a <strong>@<%= user.getNickname() %></strong> per l'abilit&agrave; <strong><%= abilita.getNome() %></strong>.
											<br>
											La richiesta aveva la seguente descrizione: <i>&ldquo;<%= richiesta.getDescrizione() %>&rdquo;</i>
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
						<center>Richieste di aiuto ricevute</center>
					</div>
					<form action="/SWIMv2Client/RichiesteAiutoActions" method="post">
						<div id="informazioniBox">
							<%
								@SuppressWarnings("unchecked")
								List<Aiuto> richiesteRicevute = 
									(List<Aiuto>) request.getAttribute("richiesteRicevute");
								if(!richiesteRicevute.isEmpty()) {
									for(Aiuto richiesta: richiesteRicevute) {
										User user = richiesta.getUserRichiedente();
										Abilita abilita = richiesta.getAbilitaRichiesta();
										Calendar data = richiesta.getMomentoRichiesta();
							%>
										<div id="richiestaRicevuta">
											<input name="richiesteScelte" type="checkbox" value="<%= richiesta.getId() %>">
											<div id="corpoRichiesta">
												Il <%= data.get(GregorianCalendar.DAY_OF_MONTH) %>.<%= data.get(GregorianCalendar.MONTH) + 1 %>.<%= data.get(GregorianCalendar.YEAR) %> hai ricevuto una richiesta di aiuto da parte di <strong>@<%= user.getNickname() %></strong> per l'abilit&agrave; <strong><%= abilita.getNome() %></strong>.
												<br>
												La richiesta ha i seguenti dettagli: <i>&ldquo;<%= richiesta.getDescrizione() %>&rdquo;</i>
											</div>
										</div>
							<%		
									}
								}
							%>
							<div id="richiesteActions">
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
		<%@ include file="../credits.html" %>
	</body>
</html>