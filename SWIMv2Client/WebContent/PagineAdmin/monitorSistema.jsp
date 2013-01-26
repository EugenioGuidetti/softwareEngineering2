<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
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
			<%@ include file="menuAdmin.jsp" %>
			<div id="body">
				<div id="boxLeft">
					<div id="titoloBox">
						<center>Abilit&agrave; disponibili</center>
					</div>
					<div id="informazioniBox">
						<%
							@SuppressWarnings("unchecked")
							List<Abilita> abilitaSistema = 
								(List<Abilita>) request.getAttribute("abilitaSistema");
							for(Abilita abilita: abilitaSistema) {
						%>
								<div id="abilita">
									<div id="corpoAbilita">
										<strong><%= abilita.getNome() %></strong>
										<br>
										<i class = "giustificato">&ldquo;<%= abilita.getDescrizione() %>&rdquo;</i>
									</div>
									<div id="iconaAbilita">
										<img src="<%= abilita.getIconaPath() %>" width="65" height="65">
									</div>
								</div>
						<%
							}
						%>
					</div>
				</div>
				<div id="boxRight">
					<div id="titoloBox">
						<center>User registrati</center>
					</div>
					<div id="informazioniBox">
						<%
							@SuppressWarnings("unchecked")
							List<User> userSistema = 
								(List<User>) request.getAttribute("userSistema");
							for(User user: userSistema) {
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
						%>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="../credits.html" %>
	</body>
</html>