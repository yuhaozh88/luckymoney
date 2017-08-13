package com.chinasoft.project.controller;

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
	
	
	
	/**
	 * 用户注册时验证itcode是否存在并且判断用户是否已经注册
	 * @author yuhaozh88
	 * @param request
	 * @param response
	 * @throws IOException
	 */
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
		if (realname != null) {//itcode存在
			boolean is_registered = DBAccess.checkIsRegistered(itcode, jdbcTemplate);
			if (!is_registered) {//用户还没有注册过
				out.print(realname);
			} else {
				out.print("@@");
			}
		} else {//itcode
			out.print("");
		}
	}
	
	/**
	 * 获取当前所有用户列表，以字符串形式返回在线用户信息
	 * @author yuhaozh88
	 * @param request
	 * @param response
	 * @throws IOException
	 */
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
		HttpSession session = request.getSession();
		ServletContext application = session.getServletContext();
		PrintWriter out = response.getWriter();
		
		String nameList = "";
		
		Hashtable<String, users_short_info> usersOnline = (Hashtable<String, users_short_info>)application.getAttribute("usersOnline");
		Set<String> users = usersOnline.keySet();
		for (String user : users) {
			users_short_info temp = usersOnline.get(user);
			nameList += " <br>" + "用户：" + temp.getNickname();
		}
		out.print(nameList);
	}
}
