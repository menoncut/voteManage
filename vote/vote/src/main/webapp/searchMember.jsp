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
<title>후보조회</title>
</head>
<body>
<%
@SuppressWarnings("unchecked")
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
	
<h2 style="text-align: center">후보조회</h2><br>
<form style="display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	text-align: center;">
<table border="1">
	<tr>
		<th>후보번호</th>
		<th>성명</th>
		<th>소속정당</th>
		<th>학력</th>
		<th>주민번호</th>
		<th>지역구</th>
		<th>대표전화</th>
	</tr>
<%
for(int i = 0; i < list.size(); i++){
%>
	<tr>
		<td style="width: 90px"><%=list.get(i).getM_no() %></td>
		<td style="width: 90px"><%=list.get(i).getM_name() %></td>
		<td style="width: 90px"><%=list.get(i).getP_code() %></td>
		<td style="width: 90px"><%=list.get(i).getP_school() %></td>
		<td style="width: 240px"><%=list.get(i).getM_jumin() %></td>
		<td style="width: 90px"><%=list.get(i).getM_city() %></td>
		<td style="width: 240px"><%=list.get(i).getP_tel() %></td>
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

