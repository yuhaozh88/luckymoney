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
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
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
		String itcode = request.getParameter("itcode");
		String realname = DBAccess.getUsersRealname(itcode, jdbcTemplate);
		PrintWriter out = response.getWriter();
		if (realname != null) {
			out.print(realname);
		} else {
			out.print("");
		}
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
		
		String nameList = "";
		if (check.equals("check")) {
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
