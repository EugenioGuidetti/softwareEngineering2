<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="java.util.Scanner" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.FileNotFoundException" %>
<%@ page import="java.util.regex.Pattern" %>
<%@ page import="java.util.regex.Matcher" %>
<%@ page import="utility.Messaggio" %>
<%@ page import="utility.TipoMessaggio" %>

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
						<center>Modifica le informazioni personali</center>
					</div>
					<form action="/SWIMv2Client/CompletaModificaUser" method="post" enctype="multipart/form-data">
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
								<label for="nSesso">Sesso:</label>
								<select id="nSesso" name="nSesso">
									<option vaule=""></option>
									<option value="Femmina">Femmina</option>
									<option value="Maschio">Maschio</option>
								</select>
							</p>
							
							<p>
								<label for="nAnnoNascita">Anno di nascita:</label>
								<select id="nAnnoNascita" name="nAnnoNascita">
									<option vaule=""></option>
									<%
										GregorianCalendar now = new GregorianCalendar();
										for(int i = now.get(GregorianCalendar.YEAR); i > 1850; i--) {
									%>
											<option value="<%= i %>"><%= i %></option>
									<%
										}
									%>
								</select>
							</p>
							
							<p>
								<label for="nCitta">Citt&agrave;:</label>
								<select id="nCitta" name="nCitta">
									<option vaule=""></option>
									<%
									    String città = null;
								        Scanner reader = null;
								        String filePath = "/WEB-INF/comuniItaliani.txt";
								        File file = new File(getServletContext().getRealPath(filePath));
								        try {
								            reader = new Scanner(file);
								        } catch(FileNotFoundException e) {
								        	System.out.println(e.getMessage());
								        }
								        if(reader != null) {
								            while (reader.hasNextLine()) {
								                città = reader.nextLine();
								    %>
								    			<option value=" <%= città %> "><%= città %></option>
								    <%
								            }
								        }
								    %>
								</select>
							</p>
							<p>
								<label for="nPassword">Password:</label>
								<input id="nPassword" name="nPassword" type="password" maxlength="255">
							</p>
							<p>
								<label for="nEmail">Email:</label>
								<input id="nEmail" name="nEmail" type="text" maxlength="255">
							</p>
							
							<p>
								<label for="nAvatar">Avatar:</label>
								<input id="nAvatar" name="nAvatar" type="file" accept="image/*">
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