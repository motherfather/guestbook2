package com.bit2016.guestbook.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit2016.guestbook.dao.GuestBookDao;
import com.bit2016.guestbook.vo.GuestBookVo;

@WebServlet("/gb")
public class GuestBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String actionName = request.getParameter("a");
		
		if ("add".equals(actionName)) {
			String name = request.getParameter("name");
			String password = request.getParameter("pass");
			String content = request.getParameter("content");
			
			GuestBookVo vo = new GuestBookVo();
			vo.setName(name);
			vo.setPassword(password);
			vo.setContent(content);
			
			GuestBookDao dao = new GuestBookDao();
			dao.insert(vo);
			
			response.sendRedirect("/guestbook2/gb");
		} else if ("deleteform".equals(actionName)) {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/deleteform.jsp");
			rd.forward(request, response);
		} else if ("delete".equals(actionName)) {
			String password = request.getParameter("password");
			String no = request.getParameter("no");
			
			GuestBookVo vo = new GuestBookVo();
			long no1 = Long.parseLong(no);
			new GuestBookDao().delete(no1, password);
			
			response.sendRedirect("/guestbook2/gb");
			
		} else {
			GuestBookDao dao = new GuestBookDao();
			List<GuestBookVo> list = dao.getList();
			
			request.setAttribute("list", list);
			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/index.jsp");
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
