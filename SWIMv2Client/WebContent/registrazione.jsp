<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.Abilita" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>SWIMv2</title>
		<link rel="stylesheet" href="/SWIMv2Client/CSS/style.css">
	</head>
	<body>
		<div id="pagina">
			<div id="logo">
				<img src="/SWIMv2Client/Immagini/logo.png">
			</div>
			<div id="body">
				<div id="boxLeft">
					<div id="titoloBox">
						<center>Completa la registrazione</center>
					</div>
					<form action="CompletamentoRegistrazione" method="post">
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
								for (Abilita abilita : abilitaSistema) {
							%>
									<div id="checkAbilita">
										<input name="abilitaScelte" type="checkbox" value="<%= abilita.getId() %>">
										<div id="corpoAbilita">
											<strong><%=abilita.getNome()%></strong>
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
						<p>Testo sul fatto che le scelte contenute in questa pagina sono del tutto opzionali rispetto alla creazione del profilo</p>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>