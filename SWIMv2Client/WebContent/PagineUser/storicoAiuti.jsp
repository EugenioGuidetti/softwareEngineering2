<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="entity.Aiuto" %>
<%@ page import="entity.Feedback" %>
<%@ page import="entity.User" %>
<%@ page import="entity.Abilita" %>
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
			<div id="menuUser">
				<ul>
					<li>
						<a href="/SWIMv2Client/PaginaUser">
							<button type="button">Pagina Personale</button>
						</a>
					</li>
					<li>
						<a href="/SWIMv2Client/RichiesteAmicizia">
							<button type="button">Richieste Amicizia</button>
						</a>
					</li>
					<li>
						<a href="/SWIMv2Client/RichiesteAiuto">
							<button type="button">Richieste Aiuto</button>
						</a>
					</li>
					<li>
						<a href="/SWIMv2Client/Aiuti">
							<button type="button">&rarr; Storico Aiuti</button>
						</a>
					</li>
					<li>
						<a href="/SWIMv2Client/Ricerca">
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
						<center>Aiuti forniti</center>
					</div>
					<div id="informazioniBox">
						<%
							@SuppressWarnings("unchecked")
							List<Aiuto> aiutiForniti = 
								(List<Aiuto>) request.getAttribute("aiutiForniti");
							if(!aiutiForniti.isEmpty()) {
								for(Aiuto aiuto: aiutiForniti) {
									User user = aiuto.getUserRichiedente();
									Abilita abilita = aiuto.getAbilitaRichiesta();
									Calendar data1 = aiuto.getMomentoAccettazione();
									Feedback feedback = aiuto.getFeedRicevuto();
						%>
									<div id="aiuto">
										<div id="corpoAiuto">
											Il <%= data1.get(GregorianCalendar.DAY_OF_MONTH) %>.<%= data1.get(GregorianCalendar.MONTH) + 1 %>.<%= data1.get(GregorianCalendar.YEAR) %> hai accettato la richiesta di aiuto per l'abilit&agrave; <strong><%= abilita.getNome() %></strong> iniviata da <strong>@<%= user.getNickname() %></strong>.
											<br>
						<%
									if(feedback != null) {
										Calendar data2 = feedback.getMomentoRilascio();
						%>
											<br>
											Il <%= data2.get(GregorianCalendar.DAY_OF_MONTH) %>.<%= data2.get(GregorianCalendar.MONTH) + 1 %>.<%= data2.get(GregorianCalendar.YEAR) %> <strong>@<%= user.getNickname() %></strong> ha valutato il tuo aiuto:
											<center>
												<img class="imgNelTesto" src="/SWIMv2Client/Immagini/stelle<%= feedback.getValutazioneNumerica() %>.png" width="179" height="34">
											</center>
											<i>&ldquo;<%= feedback.getValutazioenEstesa() %>&rdquo;</i>
						<%
									}
						%>
										
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
						<center>Aiuti ricevuti</center>
					</div>
					<div id="informazioniBox">
						<%
							@SuppressWarnings("unchecked")
							List<Aiuto> aiutiRicevuti = 
								(List<Aiuto>) request.getAttribute("aiutiRicevuti");
							if(!aiutiRicevuti.isEmpty()) {
								for(Aiuto aiuto: aiutiRicevuti) {
									User user = aiuto.getUserDestinatario();
									Abilita abilita = aiuto.getAbilitaRichiesta();
									Calendar data1 = aiuto.getMomentoAccettazione();
									Feedback feedback = aiuto.getFeedRicevuto();
						%>
									<div id="aiuto">
										<div id="corpoAiuto">
											Il <%= data1.get(GregorianCalendar.DAY_OF_MONTH) %>.<%= data1.get(GregorianCalendar.MONTH) + 1 %>.<%= data1.get(GregorianCalendar.YEAR) %> <strong>@<%= user.getNickname() %></strong> ha accettato la tua richiesta di aiuto per l'abilit&agrave; <strong><%= abilita.getNome() %></strong>.
											<br>
						<%
									if(feedback != null) {
										Calendar data2 = feedback.getMomentoRilascio();
						%>
											<br>
											Il <%= data2.get(GregorianCalendar.DAY_OF_MONTH) %>.<%= data2.get(GregorianCalendar.MONTH) + 1 %>.<%= data2.get(GregorianCalendar.YEAR) %> hai valutato l'aiuto ricevuto da <strong>@<%= user.getNickname() %></strong>:
											<center>
												<img class="imgNelTesto" src="/SWIMv2Client/Immagini/stelle<%= feedback.getValutazioneNumerica() %>.png" width="179" height="34">
											</center>
											<i>&ldquo;<%= feedback.getValutazioenEstesa() %>&rdquo;</i><br>
						<%
									} else {
						%>
											<form action="/SWIMv2Client/AiutoValutato" method="post">
												<input name="aiutoValutato" type="hidden" value="<%= aiuto.getId() %>">
												<center>
													<input id="pulsante" class="pulsantiVicini" type="submit" value="Valuta l'aiuto">
												</center>
											</form>
						<%
									}
						%>
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