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
			<div id="boxCenter">
					<center>
						<a href="index.jsp" id="homepage" class = "noUnderline"> 
							<img src="/SWIMv2Client/Immagini/indietro.png" height="40" width="40">
							<br>
							<label class="special" for = "homepage">Torna alla homepage</label>
						</a>
					</center>
				</div>
		</div>
	</div>
	<%@ include file="../credits.html" %>
</body>
</html>