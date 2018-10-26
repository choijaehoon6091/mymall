package com.test.mymall.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.test.mymall.dao.MemberDao;
import com.test.mymall.vo.Member;


@WebServlet("/AddMemberController")
public class AddMemberController extends HttpServlet {
	// 1. 라우터
	// 2. 모델 호출
	// 3. 뷰 렌더링
	private MemberDao memberDao = new MemberDao();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/addMember.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Member member = new Member();
		memberDao = new MemberDao();
		member.setId(request.getParameter("id"));
		member.setPw(request.getParameter("pw"));	
		member.setLevel(Integer.parseInt(request.getParameter("level")));
		int row = memberDao.insertMember(member);	//값을 받아서 할수있는처리?????????????
		response.sendRedirect(request.getContextPath()+"/login");
	}
}
