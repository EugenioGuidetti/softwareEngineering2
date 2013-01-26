<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
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
							Email: <%= request.getAttribute("email") %>
						</div>
					</div>
					<center>
						<a href="PagineAdmin/modificaInfoAdmin.jsp">
							<button id="pulsante" type="button">Modifica informazioni</button>
						</a>
					</center>
				</div>
				<div id="boxRight">
					<div id="titoloBox">
						<center>Crea una nuova abilit&agrave;</center>
					</div>
					<form action="/SWIMv2Client/CreaAbilita" method="post" enctype="multipart/form-data">
						<div id="informazioniBox">
							<p>
								<label for="nome">Nome:</label>
								<input id="nome" name="nome" type="text" maxlength="255" required="required">
							</p>
							<p>
								<label for="descrizione">Descrizione:</label>
								<textarea id="descrizione" name="descrizione" maxlength="140" rows="7" required="required"></textarea>
							</p>
							<p>
								<label for="icona">Icona:</label>
								<input id="icona" name="icona" type="file" accept="image/*" required="required">
							</p>
						</div>
						<center>
							<input id="pulsante" type="submit" value="Crea abilit&agrave;">
						</center>
					</form>
				</div>
			</div>	
		</div>
	</body>
</html>