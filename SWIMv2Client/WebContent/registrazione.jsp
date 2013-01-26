<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.Abilita" %>
<%@ page import="utility.Messaggio" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="header.html" %>
	</head>
	<body>
		<div id="pagina">
			<%@ include file="logoGrande.html" %>
			<%@ include file="gestioneMessaggio.jsp" %>
			<div id="body">
				<div id="boxLeft">
					<div id="titoloBox">
						<center>Completa la registrazione</center>
					</div>
					<form action="/SWIMv2Client/CompletamentoRegistrazione" method="post" enctype="multipart/form-data">
						<div id="informazioniBox">
							<p>
								<label for="rAvatar">Avatar:</label>
								<input id="rAvatar" name="rAvatar" type="file" accept="image/*">
							</p>
							<center>Abilit&agrave;:</center>
							<br>
							<%
								@SuppressWarnings("unchecked")
								List<Abilita> abilitaSistema = (List<Abilita>) 
									request.getAttribute("abilitaSistema");
								for (Abilita abilita: abilitaSistema) {
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
							<input id="pulsante" type="submit" value="Concludi">
						</center>
					</form>
				</div>
				<div id="boxRight">
					<div id="titoloBox">
						<center>Indicazioni</center>
					</div>
					<div id="indicazioni">
						<p class="giustificato">
							Qui puoi scegliere di caricare un immagine, fino a 700KB, da usare come avatar, se non lo fai ti verrà assegnato l'avatar di default.
							<br>
							Inoltre puoi decidere di dichiarare subito le tue abilit&agrave; pubbliche, la scelta non è obbligatoria, potrai sempre dichiararle in un secondo momento dalla tua area personale.
						</p>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="credits.html" %>
	</body>
</html>