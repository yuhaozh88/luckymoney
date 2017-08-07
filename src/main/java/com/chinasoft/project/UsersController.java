package com.chinasoft.project;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.time.LocalTime;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.print.DocFlavor.INPUT_STREAM;
import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chinasoft.project.DAO.DBAccess;
import com.chinasoft.project.DAO.ImageDAO;
import com.chinasoft.project.model.users_short_info;
import com.mysql.jdbc.interceptors.SessionAssociationInterceptor;
@Controller

public class UsersController {
	
	public String cut(String _fullPath) {
		if (_fullPath != null) {
			int length = _fullPath.length();
			String path = "";
			StringBuilder sb = new StringBuilder(path);
			for (int i=length-1;_fullPath.charAt(i) != '\\';--i) {
				sb.insert(0, _fullPath.charAt(i));
			}
			path = sb.toString();
			return path;
		}
		return null;
	}
	//���ڷ������ݿ��bean
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@RequestMapping("/geticon")//�����ͷ���·������
	public void seticon (HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("image/jpeg");
		response.addHeader("Cache_Control",	 "no-cache");
		response.addHeader("Pragma", "no-cache");
		response.addDateHeader("Expires", 0);
		
		HttpSession session = request.getSession();
		ServletContext application = session.getServletContext();
		PrintWriter out = response.getWriter();
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		File repository = new File("resources/img");
		factory.setRepository(repository);
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		String icon_chosen = request.getParameter("icon_chosen");
		
		if (icon_chosen != null && !icon_chosen.equals("")) {//�û�ѡ��ͷ��
//			String name = LocalTime.now().toString() + cut(icon_chosen);
//			System.out.println(icon_chosen);
//			System.out.println(name);
//			File file = new File("icon_chosen");
//			FileInputStream input = new FileInputStream(file);
//			int length = (int)file.length();
//			byte[] img = new byte[length];
//			input.read(img);
//			input.close();
//			String path = "resources/img/"+name;
//			FileOutputStream output = new FileOutputStream(new File(path));
//			output.write(img,0,length);
//			output.close();
//			out.print(path);
			out.println(icon_chosen);
			
		} else {//�û�û���ϴ�ͷ��
			String path = "resources/img/default.jpg";
			out.print(path);
		}
	}
	@RequestMapping("/getrealname")
	public void getrealname(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8;pageEncoding=UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//��ù�����֤�Ƿ�Ϊ����˾Ա��
		String itcode = request.getParameter("itcode");
		String realname = DBAccess.getUsersRealname(itcode, jdbcTemplate);
		PrintWriter out = response.getWriter();
		if (realname != null) {
			out.print(realname);
		} else {
			out.print("");
		}
	}
	@RequestMapping("/login")
	public void login(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8;pageEncoding=UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpSession session = request.getSession();
		ServletContext application = session.getServletContext();
		String itcode = request.getParameter("itcode");
		String nickname = request.getParameter("nickname");
		String dept_name = request.getParameter("dept_name");
		String password = request.getParameter("password");
		String question = request.getParameter("question");
		String answer = request.getParameter("answer");
		String path = request.getParameter("icon");
		byte[] icon = ImageDAO.getIcon(path, request);
		boolean is_online = false;
		session.setAttribute("itcode", itcode);
		session.setAttribute("nickname",nickname);
		
		//usersOnlineΪһ��HashTable��keyΪsessionId��value��users_short_info���ñ����ڱ������������û���itcode��nickname
		if (application.getAttribute("usersOnline") == null) {
			Hashtable<String, users_short_info> usersOnline = new Hashtable<String, users_short_info>();
			users_short_info user = new users_short_info(itcode,nickname);
			String sessionId = session.getId();
			usersOnline.put(sessionId, user);
			application.setAttribute("usersOnline", usersOnline);
		} else {
			Hashtable<String, users_short_info> usersOnline = (Hashtable<String, users_short_info>)application.getAttribute("usersOnline");
			users_short_info user = new users_short_info(itcode, nickname);
			String sessionId = session.getId();
			usersOnline.put(sessionId, user);
			application.setAttribute("usersOnline", usersOnline);
		}
		DBAccess.insertUsersInfo(itcode, nickname, dept_name, icon, is_online, jdbcTemplate);
		DBAccess.insertUsersPwd(itcode, password, jdbcTemplate);
		DBAccess.insertUsersQuestion(itcode, question, answer, jdbcTemplate);
	}
	
	@RequestMapping("/getusersonline")
	public void getusersonline(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8;pageEncoding=UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String check = request.getParameter("usersonline");
		HttpSession session = request.getSession();
		ServletContext application = session.getServletContext();
		PrintWriter out = response.getWriter();
		
		String nameList = "";//����AJAX������ַ���
		if (check.equals("check")) {//��ȡ��ǰ���е������û���Ϣ
			Hashtable<String, users_short_info> usersOnline = (Hashtable<String, users_short_info>)application.getAttribute("usersOnline");
			Set<String> users = usersOnline.keySet();
			for (String user : users) {
				users_short_info temp = usersOnline.get(user);
				nameList += " <br>" + temp.getNickname();
			}
			out.print(nameList);
		} else {
			out.print(nameList);
		}
		
	}
}
