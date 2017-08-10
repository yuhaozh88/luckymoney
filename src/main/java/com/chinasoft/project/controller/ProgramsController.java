package com.chinasoft.project.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
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
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8;pageEncoding=UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String itcode = (String)session.getAttribute("itcode");
		List<programs> list = DBAccess.getAllPrograms(jdbcTemplate);
		String trade_type = "打赏";
		SimpleDateFormat fmtDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String send = "";
		try {
			for (programs program : list ) {
				int pid = program.getPid();
				String name = program.getProgram_name();
				String input_name = String.valueOf(pid);
				if (request.getParameter(input_name) != null && request.getParameter(input_name) != "") {//用户在输入框中输入了金额
					//将用户打赏的金额转换为分为单位
					int account_change = 100 * Integer.valueOf(request.getParameter(input_name));
					//从数据库中取出当前用户的余额，以分为单位
					int money = DBAccess.getAccountMoney(itcode, jdbcTemplate);
					if (account_change <= money) {//用户剩余金额足够打赏
						money -= account_change;
						String log_time = fmtDate.format(new Date());
						String dept_name = DBAccess.getDeptName(pid, jdbcTemplate);
						DBAccess.setWalletMoney(itcode, money, jdbcTemplate);
						DBAccess.addTradeLog(itcode, -account_change, money, log_time, dept_name, trade_type, jdbcTemplate);
						DBAccess.setDepartmentsMoney(dept_name, account_change, jdbcTemplate);
						DBAccess.setProgramsMoney(pid, account_change, jdbcTemplate);
						send += "成功为" + name + "打赏" + (account_change / 100) + "元" + " <br>";
					} else {
						send += "没有足够余额为" + name + "打赏" + (account_change / 100) + "元" + " <br>";
					}	
				}
			}
			out.print(send);
			response.setHeader("refresh", "2;programs");
		}catch (NumberFormatException e) {
			out.print(send);
			out.print("请正确输入打赏金额");
			response.setHeader("refresh", "2;programs");
		}
	}
}