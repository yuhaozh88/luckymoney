package com.chinasoft.project;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chinasoft.project.DAO.DBAccess;
import com.chinasoft.project.DAO.ImageDAO;
import com.chinasoft.project.model.users_info;
import com.mysql.jdbc.interceptors.SessionAssociationInterceptor;

@Controller
public class IconController {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * 注册界面预览头像时候的映射
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/geticon")
	public void seticon (HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("image/jpeg");
		response.addHeader("Cache_Control",	 "no-cache");
		response.addHeader("Pragma", "no-cache");
		response.addDateHeader("Expires", 0);
		
		PrintWriter out = response.getWriter();
		String icon_chosen = request.getParameter("icon_chosen");
		
		if (icon_chosen != null && !icon_chosen.equals("")) {
			out.println(icon_chosen);
			
		} else {
			String path = "resources/img/default.jpg";
			out.print(path);
		}
	}
	
	
	/**
	 * 注册时上传头像的界面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/uploadicon")
	public String uploadicon (HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("image/jpeg");
		response.addHeader("Cache_Control",	 "no-cache");
		response.addHeader("Pragma", "no-cache");
		response.addDateHeader("Expires", 0);
		HttpSession session = request.getSession();
		String icon_chosen = request.getParameter("icon_chosen");
		if (icon_chosen != null && icon_chosen != "") {
			byte[] icon = ImageDAO.getIcon(request);
			String itcode = (String)session.getAttribute("itcode");
			DBAccess.updateUsersIcon(itcode, icon, jdbcTemplate);
		} else {
			byte[] icon = ImageDAO.getDefaultIcon(request);
			String itcode = (String)session.getAttribute("itcode");
			DBAccess.updateUsersIcon(itcode, icon, jdbcTemplate);
		}
		return "chat";
	}
	
	@RequestMapping("showicon")
	public void showicon(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String itcode = (String)session.getAttribute("itcode");
		byte[] image = DBAccess.getUsersIcon(itcode, jdbcTemplate);
		ByteArrayInputStream in = new ByteArrayInputStream(image);
		try {
			BufferedImage icon = ImageIO.read(in);
			ImageIO.write(icon, "jpg", response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
