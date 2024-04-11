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
<title>투표하기</title>
</head>
<body>
<%
%>
<jsp:include page="header.jsp" />
	
<section style="position: fixed;
	top: 80px;
	width: 100%;
	height: 100%;
	background-color: lightgray;">
	
<h2 style="text-align: center">투표하기</h2><br>
<form style="display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	text-align: center;" name="voteForm" method="post" action="vote.do">
<table border="1">
	<tr>
			<th style="width: 200px;">주민번호</th>
			<td colspan="3" style="width: 400px;">
			<input type="text" name="v_jumin" style="margin-left:-70px;"> 예 : 8906153154726
			</td>
		</tr>
		<tr>
			<th>성명</th>
			<td>
			<input type="text" name="v_name" style="margin-left:-220px;">
			</td>
		</tr>
		<tr>
			<th>투표번호</th>
			<td>
			<select name="m_no" style="margin-left:-310px;">
				<option></option>
				<option value="1">[1]김후보</option>
				<option value="2">[2]이후보</option>
				<option value="3">[3]박후보</option>
				<option value="4">[4]조후보</option>
				<option value="5">[5]최후보</option>
			</select>		
			</td>
		</tr>
		<tr>
			<th>투표시간</th>
			<td>
			<input type="text" name="v_time" placeholder="예)0930" style="margin-left:-220px;">
			</td>
		</tr>
		<tr>
			<th>투표장소</th>
			<td>
			<input type="text" name="v_area" placeholder="예)제1투표장" style="margin-left:-220px;">
			</td>
		</tr>
		<tr>
			<th>유권자확인</th>
			<td>
			<input style="margin-left:-250px;" type="radio" name="v_confirm" value="Y">확인 
			<input style="float:none;" type="radio" name="v_confirm" value="N">미확인
			</td>
		</tr>
		<tr>
			<td colspan="2" style="text-align:center">
			<button class="btn" type="submit" onclick="fn_submit();return false;">투표하기</button>
			<button class="btn" type="button" onclick="fn_reset()">다시하기</button>
			</td>
		</tr>

</table>
</form>
</section>

<jsp:include page="footer.jsp" />	
</body>
<script type="text/javascript">
	function fn_submit() {
		let frm = document.voteForm;
		
		let v_jumin = frm.v_jumin.value;
		if(v_jumin == ""){
			alert("주민번호가 입력되지 않았습니다.");
			frm.v_jumin.focus();
			return false;
		}
		let jumin = /([0-9]{2}(0[1-9]|1[0-2])(0[1-9]|[1,2][0-9]|3[0,1]))/;
		if(!jumin.test(v_jumin) || v_jumin.length < 13) {
			alert("유효하지 않은 주민번호 입니다. \n 숫자 13자리로 입력해주세요.");
			frm.v_jumin.focus();
			return false;			
		}		
		let v_name = frm.v_name.value;
		if(v_name == "") {
			alert("성명이 입력되지 않았습니다.");
			frm.v_name.focus();
			return false;
		}
		let name = /^[가-힣]+$/;
		if(!name.test(v_name)) {
			alert("한글이름으로 입력해주세요.");
			frm.v_name.focus();
			return false;
		}
		if(f.m_no.value == ""){
			alert("후보번호가 선택되지 않았습니다!");
			f.m_no.focus(); return false;
		}
		if(f.v_time.value == ""){
			alert("투표시간이 입력되지 않았습니다!");
			f.v_time.focus(); return false;
		}
		if(f.v_time.value.length < 4 || f.v_time.value.length > 4){
			alert("투표시간을 숫자4자 로 입력해주세요! 예)0930");
			f.v_time.focus(); return false;
		}
		if(f.v_area.value == ""){
			alert("투표장소가 입력되지 않았습니다!");
			f.v_area.focus(); return false;
		}
		if(f.v_area.value != "제1투표장" && f.v_area.value != "제2투표장"){
			alert("투표장소를 제1투표장 or 제2투표장 으로 입력해주세요 :D");
			f.v_area.focus(); return false;
		}		
		if(f.v_confirm.value == ""){
			alert("유권자확인이 선택되지 않았습니다!");
			return false;
		}
		frm.submit();
	}
	
	function fn_reset() {
		alert("정보를 지우고 처음부터 다시 입력합니다!");
		location="voteList.jsp";
	}
	
	function fn_onload() {
		document.voteForm.v_jumin.focus();
		document.voteForm.onload();
	}
</script>









</html>

