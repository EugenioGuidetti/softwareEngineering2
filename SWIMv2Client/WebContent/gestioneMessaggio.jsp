<%@ page import="utility.Messaggio" %>
<%
	Messaggio messaggio = null;
	
	//recupero messaggio dalla request
	messaggio = (Messaggio) request.getAttribute("messaggio");
	if(messaggio == null){
		//recuper messaggio dalla session e lo invalido
		messaggio = (Messaggio) request.getSession().getAttribute("messaggio");
		request.getSession().removeAttribute("messaggio");
	}
	if(messaggio != null) {
%>
		<div id="messaggio<%= messaggio.getTipo().toString() %>">
			<%= messaggio.getTesto() %>
		</div>
<%
	}
%>