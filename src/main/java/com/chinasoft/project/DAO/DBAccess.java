package com.chinasoft.project.DAO;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.validation.BeanPropertyBindingResult;

import com.chinasoft.project.model.users_info;
import com.chinasoft.project.model.users_pwd;
import com.chinasoft.project.model.users_question;
import com.chinasoft.project.model.users_vali;
import com.chinasoft.project.model.wallet;


public class DBAccess {
	
	public static String getUsersRealname(String itcode, JdbcTemplate jdbcTemplate) {
		RowMapper<users_vali> users_vali_mapper = new BeanPropertyRowMapper<users_vali>(users_vali.class);
		try {
			users_vali user = jdbcTemplate.queryForObject("select * from users_vali where itcode=?", users_vali_mapper,itcode);
			if (user != null) {
				return user.getRealname();
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	
	public static int insertUsersInfo(String itcode, String nickname, String dept_name, byte[] icon, boolean is_online,JdbcTemplate jdbcTemplate) {
		try {
			return jdbcTemplate.update("insert into users_info values(?,?,?,?,?)",new Object[] {itcode,nickname,dept_name,icon,is_online});
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	
	public static int insertUsersPwd(String itcode, String password, JdbcTemplate jdbcTemplate) {
		try {
			return jdbcTemplate.update("insert into users_pwd values(?,?)",new Object[] {itcode,password});
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	
	public static int insertUsersQuestion(String itcode, String question, String answer, JdbcTemplate jdbcTemplate) {
		try {
			return jdbcTemplate.update("insert into users_question values(?,?,?)", new Object[] {itcode,question,answer});
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	
	public static int insertWallet(String itcode, int money, JdbcTemplate jdbcTemplate) {
		try {
			return jdbcTemplate.update("insert into wallet values(?,?,?)", new Object[] {null,itcode,0});
		} catch(Exception e) {
			return -1;
		}
	}
	
	public static int updateUsersIcon(String itcode, byte[] icon, JdbcTemplate jdbcTemplate) {
		try {
			return jdbcTemplate.update("update users_info set icon = ? where itcode = ?",new Object[] {icon,itcode});
		} catch (Exception e) {
			return -1;
		}
	}
	public static byte[] getUsersIcon(String itcode, JdbcTemplate jdbcTemplate) {
		RowMapper<users_info> users_info_mapper = new BeanPropertyRowMapper<users_info>(users_info.class);
		users_info user = jdbcTemplate.queryForObject("select * from users_info where itcode=?", users_info_mapper, itcode);
		try {
			return user.getIcon();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}	
}