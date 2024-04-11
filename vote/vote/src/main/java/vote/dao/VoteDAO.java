package vote.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vote.dto.MemberDTO;
import vote.dto.VoteDTO;

public class VoteDAO {
	
	Connection con = null;
	PreparedStatement psmt = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	public static Connection getConnection() throws Exception {
		Class.forName("oracle.jdbc.OracleDriver");
		Connection con = DriverManager.getConnection
				("jdbc:oracle:thin:@//localhost:1521/xe", "musthave", "1234");
		System.out.println("DB 연결 성공(DBConDAO)");
		return con;
	}
		
	public String selectMember(HttpServletRequest req, HttpServletResponse resp) {
		List<MemberDTO> list = new ArrayList<MemberDTO>();
		
		try {
			con = getConnection();
			
			String query = "SELECT ";
				   query+= " m.M_NO, ";
				   query+= " m.M_NAME, ";
				   query+= " p.P_NAME, ";
				   query+= " DECODE(m.P_SCHOOL, '1', '고졸', '2', '학사', '3', '석사', '박사') P_SCHOOL, ";
				   query+= " substr(m.M_JUMIN, 1, 6) || ";
				   query+= " '-' || substr(m.M_JUMIN, 7) M_JUMIN, ";
				   query+= " m.M_CITY, ";
				   query+= " substr(p.P_TEL1, 1, 2) || '-' || p.P_TEL2 || '-' || ";
				   query+= " (substr(p.P_TEL3, 4) || ";
				   query+= " substr(p.P_TEL3, 4) || ";
				   query+= " substr(p.P_TEL3, 4) || ";
				   query+= " substr(p.P_TEL3, 4)) P_TEL ";
				   query+= " FROM tbl_member_202005 m, tbl_party_202005 p ";
				   query+= " WHERE m.P_CODE = p.P_CODE";
				   stmt = con.createStatement();
				   rs = stmt.executeQuery(query);
				   
				   while(rs.next()) {
					   MemberDTO member = new MemberDTO();
					   member.setM_no(rs.getString(1));
					   member.setM_name(rs.getString(2));
					   member.setP_code(rs.getString(3));
					   member.setP_school(rs.getString(4));
					   member.setM_jumin(rs.getString(5));
					   member.setM_city(rs.getString(6));
					   member.setP_tel(rs.getString(7));
					   
					   list.add(member);
				   }
				   req.setAttribute("list", list);
				   		rs.close();
				   		stmt.close();
				   		con.close();
		} catch (Exception e) {
			System.out.println("후보조회 중 예외 발생");
			e.printStackTrace();
		}
			
		return "searchMember.jsp";
	}
	
	public int insertVote(HttpServletRequest req, HttpServletResponse resp) {
		int result = 0;
		
		try {
			con = getConnection();
			String v_jumin = req.getParameter("v_jumin");
			String v_name = req.getParameter("v_name");
			String m_no = req.getParameter("m_no");
			String v_time = req.getParameter("v_time");
			String v_area = req.getParameter("v_area");
			String v_confirm = req.getParameter("v_confirm");
			
			String query = "INSERT INTO tbl_vote_202005 VALUES(?,?,?,?,?,?)";
			psmt = con.prepareStatement(query);
			psmt.setString(1, v_jumin);
			psmt.setString(2, v_name);
			psmt.setString(3, m_no);
			psmt.setString(4, v_time);
			psmt.setString(5, v_area);
			psmt.setString(6, v_confirm);
			
			result = psmt.executeUpdate();
			System.out.println(result);
			psmt.close();
			con.close();
		} catch (Exception e) {
			System.out.println("투표하기 중 예외발생");
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String selectAll(HttpServletRequest req, HttpServletResponse resp) {
		List<VoteDTO> list = new ArrayList<VoteDTO>();
		
		try {
			con = getConnection();
			
			String query = "SELECT V_NAME, ";
				   query += " '19' || substr(V_JUMIN, 1, 2) || ";
				   query += " '년' || substr(V_JUMIN, 3, 2) || ";
				   query += " '월' || substr(V_JUMIN, 5, 2) || ";
				   query += " '일생' V_JUMIN, ";
				   query += " '만 ' || (to_number(to_char(sysdate, 'yyyy')) ";
				   query += " - to_number('19' || substr(V_JUMIN, 1, 2))) || '세' V_AGE, ";
				   query += " DECODE(substr(V_JUMIN, 7, 1), '1', '남', '여') V_GENDER, ";
				   query += " M_NO, ";
				   query += " substr(V_TIME, 1, 2) || ':' || substr(V_TIME, 3, 2) V_TIME, ";
				   query += " DECODE(V_CONFIRM, 'Y', '확인', '미확인') V_CONFIRM ";
				   query += " FROM tbl_vote_202005 ";
				   query += " WHERE V_AREA='제1투표장'";
				   
				   stmt = con.createStatement();
				   rs = stmt.executeQuery(query);
				   
				   while(rs.next()) {
					   VoteDTO vDto = new VoteDTO();
					   
					   vDto.setV_name(rs.getString(1));
					   vDto.setV_jumin(rs.getString(2));
					   vDto.setV_age(rs.getString(3));
					   vDto.setV_gender(rs.getString(4));
					   vDto.setM_no(rs.getString(5));
					   vDto.setV_time(rs.getString(6));
					   vDto.setV_confirm(rs.getString(7));
					   
					   list.add(vDto);
				   }
				   req.setAttribute("list", list);
				   rs.close();
				   stmt.close();
				   con.close();
		} catch (Exception e) {
			System.out.println("투표검수조회 중 예외 발생");
			e.printStackTrace();
		}
		
		return "searchVote.jsp";
	}
	
	public String selectResult(HttpServletRequest req, HttpServletResponse resp) {
		List<MemberDTO> list = new ArrayList<MemberDTO>();
		
		try {
			con = getConnection();
			
			String query = "SELECT ";
				   query += " m.M_NO, m.M_NAME, count(*) AS V_TOTAL ";
				   query += " FROM tbl_member_202005 m, tbl_vote_202005 v ";
				   query += " WHERE m.M_NO = v.M_NO AND v.V_CONFIRM='Y' ";
				   query += " GROUP BY m.M_NO, m.M_NAME ";
				   query += " ORDER BY V_TOTAL DESC";
				   
				   stmt = con.createStatement();
				   rs = stmt.executeQuery(query);
				   
				   while(rs.next()) {
					   MemberDTO mDto = new MemberDTO();
					   
					   mDto.setM_no(rs.getString(1));
					   mDto.setM_name(rs.getString(2));
					   mDto.setV_total(rs.getString(3));
					   
					   list.add(mDto);
				   }
				   req.setAttribute("list", list);
				   rs.close();
				   stmt.close();
				   con.close();
		} catch (Exception e) {
			System.out.println("후보자등수 조회 중 예외 발생");
			e.printStackTrace();
		}
		
		return "voteResult.jsp";
	}
}






















































