package vote.controller;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vote.dao.VoteDAO;

/**
 * Servlet implementation class VoteController
 */
public class VoteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VoteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		requestPro(req, resp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		requestPro(req, resp);
	}
	
	protected void requestPro(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = req.getRequestURI();
		String context = req.getContextPath();
		String command = uri.substring(context.length());
		String site = null;
		
		System.out.println("command : " + command);
		
		VoteDAO vDao = new VoteDAO();
		
		switch(command) {
			case "/main.do":
				site = "index.jsp";
				break;
			case "/searchMember.do":
				site = vDao.selectMember(req, resp);
				break;
			case "/voteList.do":
				site = "voteList.jsp";
				break;
			case "/infoVote.do":
				site = vDao.selectAll(req, resp);
				break;
			case "/resultVote.do":
				site = vDao.selectResult(req, resp);
				break;
			case "/vote.do":
				int result = vDao.insertVote(req, resp);
				resp.setContentType("text/html; charset=UTF-8");
				PrintWriter out = resp.getWriter();
				if(result == 1) {
					out.println("<script>");
					out.println("alert('투표하기 정보가 정상적으로 등록 되었습니다!'); location.href='"+context+"'; ");
					out.println("</script>");
					out.flush();
				}else {
					out.println("<script>");
					out.println("alert('등록실패!'); location.href='"+context+"'; ");
					out.println("</script>");
					out.flush();
				}
				break;			
			
			default : break;
		}
		
		RequestDispatcher dispatcher = req.getRequestDispatcher(site);
		dispatcher.forward(req, resp);		
	}

}
