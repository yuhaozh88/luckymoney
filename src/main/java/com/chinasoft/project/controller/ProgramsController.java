package com.chinasoft.project.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.taglibs.standard.tag.common.fmt.FormatDateSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chinasoft.project.DAO.DBAccess;
import com.chinasoft.project.model.programs;

@Controller
public class ProgramsController {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@RequestMapping("/dashang")
	public void dashang(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String itcode = (String)session.getAttribute("itcode");
		List<programs> list = DBAccess.getAllPrograms(jdbcTemplate);
		String trade_type = "打赏";
		SimpleDateFormat fmtDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (programs program : list ) {
			try {
				int pid = program.getPid();
				String input_name = String.valueOf(pid);
				//将用户打赏的金额转换为分为单位
				int account_change = 100 * Integer.valueOf(request.getParameter(input_name));
				int money_left = DBAccess.getAccountMoney(itcode, jdbcTemplate);
				if (account_change <= money_left) {//用户剩余金额足够打赏
					money_left -= account_change;
					DBAccess.setAccountMoney(itcode, money_left, jdbcTemplate);
					
				} else {
					
				}
			} catch (NumberFormatException e) {
				out.print("请正确输入打赏金额！");
				response.setHeader("refresh", "2;programs");
			}
		}
	}
}