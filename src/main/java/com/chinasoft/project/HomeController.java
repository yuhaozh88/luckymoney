package com.chinasoft.project;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chinasoft.project.DAO.DBAccess;
import com.chinasoft.project.DAO.ImageDAO;
import com.chinasoft.project.model.users_short_info;
import com.mysql.jdbc.JDBC4DatabaseMetaData;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * 自动转向注册界面
	 * @param model
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping("/")
	public String register(Model model, HttpServletRequest httpServletRequest) {
		return "register";
	}
	
	/**
	 * 注册界面提交后向数据库插入itcode, password, question, answer, nickname信息
	 * @param request
	 * @param response
	 */
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
		String itcode = request.getParameter("itcode");
		String nickname = request.getParameter("nickname");
		String dept_name = request.getParameter("dept_name");
		String password = request.getParameter("password");
		String question = request.getParameter("question");
		String answer = request.getParameter("answer");
		int money = 0;
		byte[] icon = new byte [0];
		boolean is_online = false;
		session.setAttribute("itcode", itcode);
		session.setAttribute("nickname",nickname);
		
		DBAccess.insertUsersInfo(itcode, nickname, dept_name, icon, is_online, jdbcTemplate);
		DBAccess.insertUsersPwd(itcode, password, jdbcTemplate);
		DBAccess.insertUsersQuestion(itcode, question, answer, jdbcTemplate);
		DBAccess.insertWallet(itcode, money, jdbcTemplate);
	}
	
	/**
	 * 点击注销按钮注销用户
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8;pageEncoding=UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpSession session = request.getSession();
		session.removeAttribute("itcode");
		session.removeAttribute("nickname");
		return "register";
	}
}
