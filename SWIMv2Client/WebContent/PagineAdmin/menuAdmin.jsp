<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%
	String paginaAttuale = (String) request.getAttribute("paginaAttuale");
%>
<div id="menuAdmin">
	<ul>
		<li>
			<a href="/SWIMv2Client/PaginaAdmin">
				<button type="button">
					<%
						if(paginaAttuale != null) {
							if(paginaAttuale.equals("paginaAdmin")) {
					%>
								&rarr; Pagina Admin
					<%
							}
						} else {
					%>
								Pagina Admin
					<%
						}
					%>
				</button>
			</a>
		</li>
		<li>
			<a href="/SWIMv2Client/GestioneProposte">
				<button type="button">
					<%
						if(paginaAttuale != null) {
							if(paginaAttuale.equals("paginaAdmin")) {
					%>
								&rarr; Gestione Proposte
					<%
							}
						} else {
					%>								
								Gestione Proposte
					<%
						}
					%>
				</button>
			</a>
		</li>
		<li>
			<a href="/SWIMv2Client/MonitorSistema">
				<button type="button">
					<%
						if(paginaAttuale != null) {
							if(paginaAttuale.equals("paginaAdmin")) {
					%>
								&rarr; Monitor Sistema
					<%
							}
						} else {
					%>								
								Monitor Sistema
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