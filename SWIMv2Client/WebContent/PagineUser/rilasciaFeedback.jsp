<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="entity.Aiuto" %>
<%@ page import="entity.Abilita" %>
<%@ page import="entity.User" %>
<%@ page import="utility.Messaggio" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="../header.html" %>
	</head>
	<body>
		<div id="pagina">
			<%@ include file="../logoPiccolo.html" %>			
			<%@ include file="../gestioneMessaggio.jsp" %>
			<div id="body">
				<div id="boxLeft">
					<div id="titoloBox">
						<center>Esprimi la tua valutazione</center>
					</div>
					<form action="/SWIMv2Client/RilasciaFeedback" method="post">
						<div id="informazioniBox">
							<%
								Aiuto aiuto = (Aiuto) request.getAttribute("aiutoValutato");
								User user = aiuto.getUserDestinatario();
								Abilita abilita = aiuto.getAbilitaRichiesta();
								Calendar data = aiuto.getMomentoAccettazione();
							%>
							<p>
								<label for="vNumerica">In numeri:</label>
								<select id="vNumerica" name="vNumerica">
									<option value="0">&#9734;&#9734;&#9734;&#9734;&#9734;</option>
									<option value="1">&#9733;&#9734;&#9734;&#9734;&#9734;</option>
									<option value="2">&#9733;&#9733;&#9734;&#9734;&#9734;</option>
									<option value="3">&#9733;&#9733;&#9733;&#9734;&#9734;</option>
									<option value="4">&#9733;&#9733;&#9733;&#9733;&#9734;</option>
									<option value="5">&#9733;&#9733;&#9733;&#9733;&#9733;</option>
								</select>
							</p>
							<p>
								<label for="vEstesa">A parole:</label>
								<textarea id="vEstesa" name="vEstesa" maxlength="140" rows="7" required="required"></textarea>
							</p>
							<input name="aiutoValutato" type="hidden" value="<%= aiuto.getId() %>">
						</div>
						<center>
							<input id="pulsante" type="submit" value="Rilascia feedback">
						</center>
					</form>
				</div>
				<div id="boxRight">
					<div id="titoloBox">
						<center>Indicazioni</center>
					</div>
					<div id="indicazioni">
						<p>
							Qui puoi esprimere il tuo giudizio su un aiuto che hai ricevuto. 
							La valutazione si compone di due parti: un voto su scala numerica da un minimo di 0 ad un massimo di 5 (consigliamo la scelta dello 0 se l'utente a cui hai chiesto aiuto non ha tenuto fede all'impegno preso) ed un commento libero a parole per un massimo di 140 caratteri.
							<br>
							<br>
							Qui di seguito riportiamo l'aiuto che stai valutando:
							<br>
							il giorno <%= data.get(GregorianCalendar.DAY_OF_MONTH) %>.<%= data.get(GregorianCalendar.MONTH) + 1 %>.<%= data.get(GregorianCalendar.YEAR) %> <strong>@<%= user.getNickname() %></strong> ha accettato la tua richiesta di aiuto per l'abilit&agrave; <strong><%= abilita.getNome() %></strong>.
							La descrizione della richiesta di aiuto inviata recitava: <i>&ldquo;<%= aiuto.getDescrizione() %>&rdquo;</i>
						</p>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>