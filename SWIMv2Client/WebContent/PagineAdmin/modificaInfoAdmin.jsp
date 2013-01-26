<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

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
						<center>Modifica le informazioni personali</center>
					</div>
					<form action="/SWIMv2Client/CompletaModificaAdmin" method="post">
						<div id="informazioniBox">
							<p>
								<label for="nNome">Nome:</label>
								<input id="nNome" name="nNome" type="text" maxlength="255">
							</p>
							<p>
								<label for="nCognome">Cognome:</label>
								<input id="nCognome" name="nCognome" type="text" maxlength="255">
							</p>
							<p>
								<label for="nPassword">Password:</label>
								<input id="nPassword" name="nPassword" type="password" maxlength="255">
							</p>
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
						<p>Testo sul fatto che vanno compilati solo i campi che si intende modificare, gli altri vanno lasciati bianchi</p>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="../credits.html" %>
	</body>
</html>