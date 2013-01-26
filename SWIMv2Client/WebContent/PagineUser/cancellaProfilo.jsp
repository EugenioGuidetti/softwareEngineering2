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
			<%@ include file="menuUser.jsp" %>
			<div id="body">
				<div id="containerLeft">
					<div id="boxLeft">
						<center>
							<a href="/SWIMv2Client/PagineUser/modificaInfoUser.jsp" id="homepage" class = "noUnderline"> 
								<img src="/SWIMv2Client/Immagini/indietro.png" height="40" width="40">
								<br>
								<label class="special" for = "homepage">Torna alla modifica dei dati</label>
							</a>
						</center>
					</div>
					<div id="boxLeft">
						<div id="titoloBox">
							<center>Cancella il tuo profilo</center>
						</div>
						<div id="informazioniBox">
							<p></p>
						</div>
						<center>
							<a href="/SWIMv2Client/CancellaProfilo">
								<button id="pulsante" type="button">Cancella</button>
							</a>
						</center>
					</div>
				</div>
				<div id="boxRight">
					<div id="titoloBox">
						<center>Indicazioni</center>
					</div>
					<div id="indicazioni">
						<p>Se procedi ora la procedura di cancellazione avr&agrave; luogo ed il tuo profilo, con tutto ci&ograve; che lo riguarda, verr&agrave; eliminita in modo irreversibile.</p>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="../credits.html" %>
	</body>
</html>