package com.test.mymall.web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.test.mymall.dao.ItemDao;
import com.test.mymall.vo.Item;



@WebServlet("/GetItemList")
public class GetItemList extends HttpServlet {
	private ItemDao itemDao;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int currentPage = 1;
		// request.getParameter("currentPage");
		int rowPerPage = 10;
		itemDao = new ItemDao();
		ArrayList<Item> list = itemDao.selectItemList(currentPage, rowPerPage);
		request.setAttribute("list", list);
		// forward itemList.jsp
	}
}