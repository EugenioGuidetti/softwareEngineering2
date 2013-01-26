<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%
	String paginaAttuale = (String) request.getAttribute("paginaAttuale");
%>
<div id="menuUser">
	<ul>
		<li>
			<a href="/SWIMv2Client/PaginaUser">
				<button type="button">
					<%
						if(paginaAttuale != null) {
							if(paginaAttuale.equals("paginaPersonale")) {
					%>
								&rarr; Pagina Personale
					<%
							} else {
					%>
								Pagina Personale
					<%
							}
						} else {
					%>
							Pagina Personale
					<%
						}
					%>
				</button>
			</a>
		</li>
		<li>
			<a href="/SWIMv2Client/RichiesteAmicizia">
				<button type="button">
					<%
						if(paginaAttuale != null) {
							if(paginaAttuale.equals("richiesteAmicizia")) {
					%>
								&rarr; Richieste Amicizia
					<%
							} else {
					%>
								Richieste Amicizia
					<%
							}
						} else {
					%>
							Richieste Amicizia
					<%
						}
					%>
				</button>
			</a>
		</li>
		<li>
			<a href="/SWIMv2Client/RichiesteAiuto">
				<button type="button">
					<%
						if(paginaAttuale != null) {
							if(paginaAttuale.equals("richiesteAiuto")) {
					%>
								&rarr; Richieste Aiuto
					<%
							} else {
					%>
								Richieste Aiuto
					<%
							}
						} else {
					%>
							Richieste Aiuto
					<%
						}
					%>
				</button>
			</a>
		</li>
		<li>
			<a href="/SWIMv2Client/Aiuti">
				<button type="button">
					<%
						if(paginaAttuale != null) {
							if(paginaAttuale.equals("storicoAiuti")) {
					%>
								&rarr; Storico Aiuti
					<%
							} else {
					%>
								Storico Aiuti
					<%
							}
						} else {
					%>
							Storico Aiuti
					<%
						}
					%>
				</button>
			</a>
		</li>
		<li>
			<a href="/SWIMv2Client/Ricerca">
				<button type="button">
					<%
						if(paginaAttuale != null) {
							if(paginaAttuale.equals("ricerca")) {
					%>
								&rarr; Ricerca
					<%
							} else {
					%>
								Ricerca
					<%
							}
						} else {
					%>
							Ricerca
					<%
						}
					%>
				</button>
			</a>
		</li>
		<li>
			<a href="/SWIMv2Client/Logout">
				<button type="button">Logout</button>
			</a>
		</li>
	</ul>
</div>