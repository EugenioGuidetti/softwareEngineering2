<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
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
			<div id="body">
			 <div id="boxLeft">
			 	<div id="titoloBox">
			 		<center>Modifica le abilit&agrave; dichiarate</center>
			 	</div>
			 	<form action="/SWIMv2Client/ModificaAbilita" method="post">
			 		<div id="informazioniBox">
			 			<%
			 				@SuppressWarnings("unchecked")
			 				List<Abilita> abilitaSistema = 
			 					(List<Abilita>) request.getAttribute("abilitaSistema");
			 				for(Abilita abilita: abilitaSistema) {
			 			%>
			 					<div id="checkAbilita">
			 						<input name="abilitaScelte" type="checkbox" value="<%= abilita.getId() %>">
			 						<div id="corpoAbilita">
			 							<strong><%= abilita.getNome() %></strong>
			 							<br>
			 							<i>&ldquo;<%= abilita.getDescrizione() %>&rdquo;</i>
			 						</div>
			 						<div id="iconaAbilita">
			 							<img src="<%= abilita.getIconaPath() %>" width="65" height="65">
			 						</div>
			 					</div>
			 			<%
			 				}
			 			%>
			 		</div>
			 		<center>
			 			<input id="pulsante" type="submit" value="Modifica">
			 		</center>
			 	</form>
			 </div>
			 <div id="boxRight">
			 	<div id="titoloBox">
			 		<center>Indicazioni</center>
			 	</div>
			 	<div id="indicazioni">
			 		<p>
			 			Qui puoi modificare le tue abilit&agrave; dichiarate.
			 			<br>
			 			Le abilit&agrave; nelle quali ti sei reso disponibile a fornire aiuto sono:
			 			<br>
			 			<%
			 				@SuppressWarnings("unchecked")
			 				List<Abilita> abilitaUser = 
			 					(List<Abilita>) request.getAttribute("abilitaUser");
			 				if(!abilitaUser.isEmpty()) {
			 					for(Abilita abilitaDichiarata : abilitaUser) {
			 			%>
			 						<strong><%= abilitaDichiarata.getNome() %></strong>
			 						<br>
			 			<%
			 					}
			 				} else {
			 			%>
			 					Nessuna
			 			<%
			 				}
			 			%>
			 		</p>			 		
			 	</div>
			 </div>
			</div>
		</div>
	</body>
</html>