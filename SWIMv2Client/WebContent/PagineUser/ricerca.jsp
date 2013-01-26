<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
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
			<%@ include file="menuUser.jsp" %>
			<div id="body">
				<div id="boxLeft">
					<div id="titoloBox">
						<center>Ricerca</center>
					</div>
					<form action="/SWIMv2Client/RicercaUser" method="post">
						<div id="informazioniBox">
							<center class="underline">Filtro sul nome:</center>
							<p>
								<label for="nome">Nome:</label>
								<input id="nome" name="nome" type="text" maxlength="255">
							</p>
							<p>
								<label for="cognome">Cognome:</label>
								<input id="cognome" name="cognome" type="text" maxlength="255">
							</p>
							<div id="filtroAbilita">
								<center class="underline">Filtro per abilita:</center>
								<br>
								<label for="abilita">Abilit&agrave;:</label>
								<select id="abilita" name="abilita">
									<option value=""></option>
									<%
										@SuppressWarnings("unchecked")
										List<Abilita> abilitaSistema = 
											(List<Abilita>) request.getAttribute("abilitaSistema");
										for(Abilita abilita: abilitaSistema) {
									%>
											<option value="<%= abilita.getId() %>"><%= abilita.getNome() %></option>
									<%
										}
									%>
								</select>
							</div>
							<div id="dominioRicerca">
								<br>
								<center class="underline">Scegli il dominio della ricerca:</center>
								<br>
								<input id="dominio1" name="dominioRicerca" type="radio" value="sistema">
								<label for="dominio1">Ricerca tra tutti gli User</label>
								<br>
								<input id="dominio2" name="dominioRicerca" type="radio" value="amici">
								<label for="dominio2">Ricerca tra gli amici</label>
							</div>
							<div id="filtriRicerca">
								<br>
								<center class="underline">Scegli un filtro:</center>
								<br>
								<input id="filtro1" name="filtroRicerca" type="radio" value="perNome">
								<label for="filtro1">Ricerca per nome</label>
								<br>
								<input id="filtro2" name="filtroRicerca" type="radio" value="perAbilita">
								<label for="filtro2">Ricerca per abilita</label>
							</div>
						</div>
						<center>
							<input id="pulsante" type="submit" value="Esegui la ricerca">
						</center>
					</form>
				</div>
				<div id="boxRight">
					<div id="titoloBox">
						<center>Risultati della ricerca</center>
					</div>
						<%
							@SuppressWarnings("unchecked")
							List<User> risultatiRicerca = 
								(List<User>) request.getAttribute("risultatiRicerca");
							if(risultatiRicerca != null) {
								if(!risultatiRicerca.isEmpty()) {
						%>
									<div id="informazioniBox">
						<%
									String nomeCercato = (String) request.getAttribute("nomeCercato");
									String cognomeCercato = (String) request.getAttribute("cognomeCercato");
									String abilitaCercata = (String) request.getAttribute("abilitaCercata");
									String filtroUsato = (String) request.getAttribute("filtroUsato");
									String dominioScelto = (String) request.getAttribute("dominioScelto");
									for(User user: risultatiRicerca) {
						%>
										<form action="/SWIMv2Client/DettagliProfilo" method="post">
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
											<input name="userCercato" type="hidden" value="<%= user.getNickname() %>">
											<input name="nomeCercato" type="hidden" value="<%= nomeCercato %>">
											<input name="cognomeCercato" type="hidden" value="<%= cognomeCercato %>">
											<input name="abilitaCercata" type="hidden" value="<%= abilitaCercata %>">
											<input name="filtroUsato" type="hidden" value="<%= filtroUsato %>">
											<input name="dominioScelto" type="hidden" value="<%= dominioScelto %>">
											<input class="pulsanteRicerca" type="submit" value="Dettagli Profilo">
										</form>
						<%
									}
						%>
									
									</div>
						<%
								} else {
						%>
									<div id="indicazioni">
										<p>La ricerca non ha prodotto alcun risultato.</p>
									</div>
						<%
								}
							}
						%>
				</div>
			</div>
		</div>	
	</body>
</html>