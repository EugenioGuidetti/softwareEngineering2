<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="header.html"%>
</head>
<body>

	<div id="pagina">
		<%@ include file="logoGrande.html"%>
		<%@ include file="gestioneMessaggio.jsp"%>
		<div id="body">
			<div id="containerLeft">
				<div id="boxLeft">
					<center>
						<a href="index.jsp" id="homepage" class = "noUnderline"> 
							<img src="/SWIMv2Client/Immagini/indietro.png" height="40" width="40">
							<br>
							<label class="special" for = "homepage">Torna alla homepage</label>
						</a>
					</center>
				</div>
				<div id="boxLeft">
					<div id="titoloBox">
						<center>Resetta la tua password</center>
					</div>
					<form action="/SWIMv2Client/RecuperaPassword" method="post">
						<div id="informazioniBox">
							<p>
								<label for="nickname">Nickname: </label> <input type="text"
									name="nickname" id="nickname" maxlength="255"
									required="required">
							</p>
						</div>
						<center>
							<input id="pulsante" type="submit" value="Reset password">
						</center>
					</form>
				</div>
			</div>
			<div id="boxRight">
				<div id="titoloBox">
					<center>Indicazioni</center>
				</div>
				<div id="indicazioni">
					<p>Inserisci il tuo nickname, ti verrà inviata una mail con una
						nuova password</p>
				</div>
			</div>
		</div>
	</div>
</body>
</html>