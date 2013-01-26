<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.User" %>
<%@ page import="entity.Abilita" %>
<%@ page import="entity.ReputazioneAbilita" %>
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
			<%@ include file="menuUser.jsp" %>
			<div id="body">				
				<div id="containerLeft">
					<div id="boxLeft">
						<div id="titoloBox">
							<center>Informazioni personali</center>
						</div>
						<div id="informazioniBox">
							<div id="infoProfilo">
								<div id="avatar">
									<img src="<%= request.getAttribute("avatar") %>" width="65" height="65">
								</div>
								<div id="testo">
									<%= request.getAttribute("nomeCompleto") %>
									<br>
									@<%= request.getSession().getAttribute("nickname") %>
								</div>
							</div>
							<div id="altreInfoProfilo">
								Email: <%= request.getAttribute("email") %><br>
								Sesso: <%= request.getAttribute("sesso") %><br>
								Anno di nascita: <%= request.getAttribute("annoNascita") %><br>
								Citt&agrave;: <%= request.getAttribute("citta") %><br>
							</div>
						</div>
						<center>
							<a href="PagineUser/modificaInfoUser.jsp">
								<button id="pulsante" type="button">Modifica informazioni</button>
							</a>
						</center>
					</div>				
					<div id="boxLeft">
						<div id="titoloBox">
							<center>Abilit&agrave; dichiarate</center>
						</div>
						<div id="informazioniBox">
							<%
								@SuppressWarnings("unchecked")
								List<Abilita> abilitaDichiarate = 
									(List<Abilita>) request.getAttribute("abilitaDichiarate");
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
						<center>
							<a href="/SWIMv2Client/Abilita">
								<button id="pulsante" type="button">Modifica le tue abilit&agrave;</button>
							</a>
						</center>
						<center>
							<a href="PagineUser/proponiAbilita.jsp">
								<button class="pulsantiVicini" id="pulsante" type="button">Proponi una nuova abilit&agrave;</button>
							</a>
						</center>
					</div>
				</div>
				<div id="boxRight">
					<div id="titoloBox">
						<center>Amicizie allacciate</center>
					</div>
					<div id="informazioniBox">
						<%
							@SuppressWarnings("unchecked")
							List<User> amici = 
								(List<User>) request.getAttribute("amici");
							if(amici != null) {
								for(User user : amici) {
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
					<center>
						<a href="/SWIMv2Client/Amicizie">
							<button id="pulsante" type="button">Modifica amicizie</button>
						</a>
					</center>
				</div>
			</div>
		</div>
		<%@ include file="../credits.html" %>
	</body>
</html>