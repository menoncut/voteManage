<%@page import="vote.dto.MemberDTO"%>
<%@page import="oracle.net.aso.m"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="vote.vo.MemberVO"%>
<%@ page import="java.util.List"%>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="dbpkg.DBCon" %>
<%@ page import="vote.dao.VoteDAO"%>
<%@ page import="jakarta.servlet.http.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>후보자등수</title>
</head>
<body>
<%
List<MemberDTO> list = new ArrayList<MemberDTO>();
list = (List<MemberDTO>)request.getAttribute("list");
request.setCharacterEncoding("UTF-8");
%>
<jsp:include page="header.jsp" />
	
<section style="position: fixed;
	top: 80px;
	width: 100%;
	height: 100%;
	background-color: lightgray;">
	
<h2 style="text-align: center">후보자등수</h2><br>
<form style="display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	text-align: center;">
<table border="1">
	<tr>
		<th>후보번호</th>
		<th>성명</th>
		<th>총투표건수</th>
	</tr>
<%
for(int i = 0; i < list.size(); i++){
%>
	<tr>
		<td style="width: 90px"><%=list.get(i).getM_no() %></td>
		<td style="width: 90px"><%=list.get(i).getM_name() %></td>
		<td style="width: 90px"><%=list.get(i).getV_total() %></td>
	</tr>	
	<%
}
%>
</table>
</form>
</section>

<jsp:include page="footer.jsp" />	
</body>
</html>

