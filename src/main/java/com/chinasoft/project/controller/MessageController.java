package com.chinasoft.project.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.filefilter.AndFileFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chinasoft.project.DAO.DBAccess;
import com.chinasoft.project.model.messages;
import com.mysql.jdbc.interceptors.SessionAssociationInterceptor;

@Controller
public class MessageController {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@RequestMapping("/sendmessages")
	public String sendmessages(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String send="";
		String message = request.getParameter("message");
		HttpSession session = request.getSession();
		String itcode = (String)session.getAttribute("itcode");
		String nickname = (String)session.getAttribute("nickname");
		SimpleDateFormat fmtDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String message_time = fmtDate.format(new Date());
		if (message != null && !message.equals("")) {//输入信息不为空，向数据库中插入
			DBAccess.addMessages(itcode, nickname, message, message_time, jdbcTemplate);
			out.print(send);
		}
		return "chat";
	}
	
	@RequestMapping("/showmessages")
	public void showmessages(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8;pageEncoding=UTF-8");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		String itcode = (String)session.getAttribute("itcode");
		List<messages> messages = DBAccess.getMessages(jdbcTemplate);
		String send = "";
		for (int i = messages.size()-1 ; i >= 0 ; --i) {
			String message_time = messages.get(i).getMessage_time();
			String nickname = messages.get(i).getNickname();
			String message = messages.get(i).getMessage();
			String itcode_in_messages = messages.get(i).getItcode();
			session.setAttribute("itcode_in_messages", itcode_in_messages);
			if (itcode.equals(itcode_in_messages)) {//当前用户的发言记录
				//send = send+"<br><br>"+"<tr><td></td><td></td><td>"+message_time+"</td></tr>"+"<tr><td>"+message+"</td><td>&nbsp;&nbsp;"+nickname+"</td><td>"+"<img src='showmessagesicon'>"+"</td></tr>";
				send = send+"<br><br>"+"<span style='float:right'>"+message_time+"</span>"+"<br>"+"<span style='float:right'>"+message+"&nbsp;&nbsp;"+nickname+"<img src='showmessagesicon' width='50' height='50'>";
			} else {
				//send = send+"<br><br>"+"<tr><td>"+message_time+"</td><td></td><td></td></tr>"+"<tr><td>"+"<img src='showmessagesicon'>"+"</td><td>"+nickname+"&nbsp;&nbsp;</td><td>"+message+"</td></tr>";
				send = send+"<br><br>"+message_time+"<br>"+"<img src='showmessagesicon' width='50' height='50'>"+nickname+"&nbsp;&nbsp;"+message;
			}
		}
		out.print(send);
	}
	
}
