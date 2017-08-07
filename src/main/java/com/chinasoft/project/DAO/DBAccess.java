package com.chinasoft.project.DAO;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.validation.BeanPropertyBindingResult;

import com.chinasoft.project.model.users_info;
import com.chinasoft.project.model.users_pwd;
import com.chinasoft.project.model.users_question;
import com.chinasoft.project.model.users_vali;

/**
 * 此类提供静态方法用于访问数据库
 * @author yuhaozh88
 *
 */
public class DBAccess {
	
	/**
	 * @author yuhaozh88
	 * 此静态方法利用员工号，在所有导入的员工信息中查找匹配的员工对象
	 * @param itcode
	 * @param jdbcTemplate
	 * @return 返回员工号对应的员工姓名，如果为null查询失败
	 */
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
	
	/**
	 * @author yuhaozh88
	 * 此静态方法用于向用户信息表插入信息
	 * @param itcode 用户工号
	 * @param nickname 用户昵称
	 * @param dept_name 用户所属部门
	 * @param icon 用户头像
	 * @param is_online 用户是否在线
	 * @param jdbcTemplate
	 * @return 返回的整数为插入的记录条数，大于0为插入正常，返回值为-1为数据库异常
	 */
	public static int insertUsersInfo(String itcode, String nickname, String dept_name, byte[] icon, boolean is_online,JdbcTemplate jdbcTemplate) {
		RowMapper<users_info> users_info_mapper = new BeanPropertyRowMapper<users_info>(users_info.class);
		try {
			return jdbcTemplate.update("insert into users_info values(?,?,?,?,?)",users_info_mapper,new Object[] {itcode,nickname,dept_name,icon,is_online});
		} catch (Exception e) {
			return -1;
		}
	}
	
	/**
	 * @author yuhaozh88
	 * 此静态方法用于向用户密码表插入信息
	 * @param itcode 用户工号
	 * @param password 用户密码
	 * @param jdbcTemplate
	 * @return 返回的整数为插入的记录条数，大于0为插入正常，返回值为-1为数据库异常
	 */
	public static int insertUsersPwd(String itcode, String password, JdbcTemplate jdbcTemplate) {
		RowMapper<users_pwd> users_pwd_mapper = new BeanPropertyRowMapper<users_pwd>(users_pwd.class);
		try {
			return jdbcTemplate.update("insert into users_pwd values(?,?)",users_pwd_mapper,new Object[] {itcode,password});
		} catch (Exception e) {
			return -1;
		}
	}
	
	/**
	 * @author yuhaozh88
	 * 此静态方法用于向用户密保问题表插入信息
	 * @param itcode 用户工号
	 * @param question 密保问题
	 * @param answer 密保问题答案
	 * @param jdbcTemplate
	 * @return 返回的整数为插入的记录条数，大于0为插入正常，返回值为-1为数据库异常
	 */
	public static int insertUsersQuestion(String itcode, String question, String answer, JdbcTemplate jdbcTemplate) {
		RowMapper<users_question> users_question_mapper = new BeanPropertyRowMapper<users_question>(users_question.class);
		try {
			return jdbcTemplate.update("insert into users_question values(?,?,?)",users_question_mapper,new Object[] {itcode,question,answer});
		} catch (Exception e) {
			return -1;
		}
	}
}
