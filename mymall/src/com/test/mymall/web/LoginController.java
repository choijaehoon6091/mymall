package com.test.mymall.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.test.mymall.dao.MemberDao;
import com.test.mymall.vo.Member;



@WebServlet("/login")
public class LoginController extends HttpServlet {
	private MemberDao memberDao;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("loginMember") == null) {
			System.out.println("login.jsp forward");
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
		} else {
			System.out.println("로그인중입니다 ... ");
			response.sendRedirect("/index");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// boolean MemberDao.login(Member) 
		boolean isLogin = false; 
		Member member = new Member();
		memberDao = new MemberDao();
		
		member.setId(request.getParameter("id"));
		member.setPw(request.getParameter("pw"));
		isLogin = memberDao.loginMember(member);
		
		if(isLogin) {
			HttpSession session = request.getSession();
			session.setAttribute("loginMember", request.getParameter("id"));
			request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath()+"/login");
		}
	}
}
