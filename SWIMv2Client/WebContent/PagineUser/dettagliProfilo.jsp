<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.User" %>
<%@ page import="entity.Abilita" %>
<%@ page import="entity.ReputazioneAbilita" %>

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
				<div id="containerLeft">
					<div id="boxLeft">
						<form action="RicercaUser" method="post">
							<%
								String nomeCercato = (String) request.getAttribute("nomeCercato");
								String cognomeCercato = (String) request.getAttribute("cognomeCercato");
								String abilitaCercata= (String) request.getAttribute("abilitaCercata");
								String filtroUsato = (String) request.getAttribute("filtroUsato");
								String dominioScelto = (String) request.getAttribute("dominioScelto");
							%>
							<input name="nome" type="hidden" value="<%= nomeCercato %>">
							<input name="cognome" type="hidden" value="<%= cognomeCercato %>">
							<input name="abilita" type="hidden" value="<%= abilitaCercata %>">
							<input name="filtroRicerca" type="hidden" value="<%= filtroUsato %>">
							<input name="dominioRicerca" type="hidden" value="<%= dominioScelto %>">
							<center>
								<input id="pulsante" type="submit" value="Risultati della ricerca">
							</center>
						</form>
					</div>
					<div id="boxLeft">
						<div id="titoloBox">
							<center>Invia richiesta di aiuto</center>
						</div>
						<form action="/SWIMv2Client/InviaRichiestaAiuto" method="post">
							<div id="informazioniBox">
								<%
									User userCercato = (User) request.getAttribute("userCercato");
									@SuppressWarnings("unchecked")
									List<Abilita> abilitaDichiarate = 
										(List<Abilita>) request.getAttribute("abilitaDichiarate");
								%>
								<p>
									<label for="abilita">Abilit&agrave;</label>
									<select id="abilita" name="abilita">
										<%
											for(Abilita abilita: abilitaDichiarate) {
										%>
												<option value="<%= abilita.getId() %>"><%= abilita.getNome() %></option>
										<%
											}
										%>
									</select>
								</p>
								<p>
									<label for="descrizione">Descrizione:</label>
									<textarea id="descrizione" name="descrizione" maxlength="140" rows="7" required="required"></textarea>
								</p>
							</div>
							<input name="nicknameDestinatario" type="hidden" value="<%= userCercato.getNickname() %>">
							<center>
								<input id="pulsante" type="submit" value="Invia richiesta aiuto">
							</center>
						</form>
					</div>
					<div id="boxLeft">
						<div id="titoloBox">
							<center>Invia richiesta di Amicizia</center>
						</div>
						<form action="/SWIMv2Client/InviaRichiestaAmicizia" method="post">
							<%
								Boolean amicizia = (Boolean) request.getAttribute("amicizia");
								String disabilitato = "";
								if(amicizia) {
									disabilitato = "disabled";
							%>
									<div id="indicazioni">
										<p>Non puoi inviare una richiesta di amicizia a <strong>@<%= userCercato.getNickname() %>, questo vuol dire che o siete gi� amici o che esiste gi� una richiesta in attesa di una risposta.</strong></p>
									</div>
							<%
								}
							%>
							<input name="nicknameDestinatario" type="hidden" value="<%= userCercato.getNickname() %>">
							<center>
								<input id="pulsante" type="submit" value="Invia richiesta amicizia" disabled="<%= disabilitato %>">
							</center>
						</form>
					</div>
				</div>
				<div id="containerRight">
					<div id="boxRight">
						<div id="titoloBox">
							<center>Informazioni personali user</center>
						</div>
						<div id="informazioniBox">
							<div id="infoProfilo">
								<div id="avatar">
									<img src="<%= request.getAttribute("avatar") %>" width="65" height="65">
								</div>
								<div id="testo">
									<%= userCercato.getNome() %> <%= userCercato.getCognome() %>
									<br>
									@<%= userCercato.getNickname() %>
								</div>
							</div>
							<div id="altreInfoProfilo">
								Email: <%= userCercato.getEmail() %><br>
								Sesso: <%= userCercato.getSesso() %><br>
								Anno di nascita: <%= userCercato.getAnnoNascita() %><br>
								Citt&agrave;: <%= userCercato.getCitta() %><br>
							</div>
						</div>
					</div>
					<div id="boxRight">
						<div id="titoloBox">
							<center>Abilit&agrave; dichiarate dallo user</center>
						</div>
						<div id="informazioniBox">
							<%
								@SuppressWarnings("unchecked")
								Map<Abilita, ReputazioneAbilita> abilitaUser = 
									(Map<Abilita, ReputazioneAbilita>) request.getAttribute("abilitaValutate");
								if(abilitaDichiarate != null) {
									for(Abilita abilita: abilitaDichiarate) {
							%>
										<div id="abilita">
											<div id="corpoAbilita">
												<strong><%= abilita.getNome() %></strong> (<%= abilitaUser.get(abilita).getNumeroFeedbackRicevuti() %>)
												<br>
												<img class="stelle" src="/SWIMv2Client/Immagini/stelle<%= abilitaUser.get(abilita).getMediaValutazioniFeedback() %>.png" width="179" height="34">											
											</div>
											<div id="iconaAbilita">
												<img src="<%= abilita.getIconaPath() %>" width="65" height="65">
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
		</div>
	</body>
</html>