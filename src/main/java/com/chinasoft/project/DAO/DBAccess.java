package com.chinasoft.project.DAO;

import java.util.Hashtable;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.validation.BeanPropertyBindingResult;

import com.chinasoft.project.model.messages;
import com.chinasoft.project.model.programs;
import com.chinasoft.project.model.users_info;
import com.chinasoft.project.model.users_pwd;
import com.chinasoft.project.model.users_question;
import com.chinasoft.project.model.users_vali;
import com.chinasoft.project.model.wallet;


public class DBAccess {
	
	/**
	 * 用itcode获取用户真实姓名，注册页面用于用户身份检测
	 * @author yuhaozh88
	 * @param itcode
	 * @param jdbcTemplate
	 * @return 返回null查询失败
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
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 向用户信息表插入用户的注册信息
	 * @author yuhaozh88
	 * @param itcode
	 * @param nickname
	 * @param dept_name
	 * @param icon
	 * @param is_online
	 * @param jdbcTemplate
	 * @return 返回为-1插入失败
	 */
	public static int insertUsersInfo(String itcode, String nickname, String dept_name, byte[] icon, boolean is_online,JdbcTemplate jdbcTemplate) {
		try {
			return jdbcTemplate.update("insert into users_info values(?,?,?,?,?)",new Object[] {itcode,nickname,dept_name,icon,is_online});
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * 向用户密码表中插入用户的密码
	 * @author yuhaozh88
	 * @param itcode
	 * @param password
	 * @param jdbcTemplate
	 * @return 返回为-1插入失败
	 */
	public static int insertUsersPwd(String itcode, String password, JdbcTemplate jdbcTemplate) {
		try {
			return jdbcTemplate.update("insert into users_pwd values(?,?)",new Object[] {itcode,password});
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * 向用户密保问题表插入用户的密保问题和答案
	 * @author yuhaozh88
	 * @param itcode
	 * @param question
	 * @param answer
	 * @param jdbcTemplate
	 * @return 返回为-1插入失败
	 */
	public static int insertUsersQuestion(String itcode, String question, String answer, JdbcTemplate jdbcTemplate) {
		try {
			return jdbcTemplate.update("insert into users_question values(?,?,?)", new Object[] {itcode,question,answer});
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * 向用户钱包表中插入用户的钱包信息
	 * @author yuhaozh88
	 * @param itcode
	 * @param money
	 * @param jdbcTemplate
	 * @return
	 */
	public static int insertWallet(String itcode, int money, JdbcTemplate jdbcTemplate) {
		try {
			return jdbcTemplate.update("insert into wallet values(?,?,?)", new Object[] {null,itcode,0});
		} catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public static int updateUsersIcon(String itcode, byte[] icon, JdbcTemplate jdbcTemplate) {
		try {
			return jdbcTemplate.update("update users_info set icon = ? where itcode = ?",new Object[] {icon,itcode});
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * 根据用户的itcode获取用户的头像
	 * @author yuhaozh88
	 * @param itcode
	 * @param jdbcTemplate
	 * @return 返回为null查询失败
	 */
	public static byte[] getUsersIcon(String itcode, JdbcTemplate jdbcTemplate) {
		RowMapper<users_info> users_info_mapper = new BeanPropertyRowMapper<users_info>(users_info.class);
		users_info user = jdbcTemplate.queryForObject("select * from users_info where itcode=?", users_info_mapper, itcode);
		try {
			return user.getIcon();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}	
	
	/**
	 * @date Aug 9th
	 * 返回目前所有的节目信息
	 * @author yuhaozh88
	 * @param jdbcTemplate
	 * @return 返回为null查询失败
	 */
	public static List<programs> getAllPrograms(JdbcTemplate jdbcTemplate){
		RowMapper<programs> programs_mapper = new BeanPropertyRowMapper<programs>(programs.class);
		try {
			List<programs> list = jdbcTemplate.query("select * from programs", programs_mapper);
			return list;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * @date Aug 9th
	 * 查询指定itcode员工账户余额
	 * @author yuhaozh88
	 * @param itcode
	 * @param jdbcTemplate
	 * @return 返回-1查询失败
	 */
	public static int getAccountMoney(String itcode, JdbcTemplate jdbcTemplate) {
		RowMapper<wallet> wallet_mapper = new BeanPropertyRowMapper<wallet>(wallet.class);
		try {
			wallet w = jdbcTemplate.queryForObject("select * from wallet where itcode=?", wallet_mapper, itcode);
			return w.getMoney();
		} catch (Exception e){
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * @date Aug 9th
	 * 更改指定itcode用户的账户余额
	 * @author yuhaozh88
	 * @param itcode
	 * @param money_left
	 * @param jdbcTemplate
	 * @return 返回-1查询失败
	 */
	public static int setWalletMoney(String itcode, int money, JdbcTemplate jdbcTemplate) {
		try {
			return jdbcTemplate.update("update wallet set money=? where itcode=?", new Object[] {money,itcode});
		} catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * @date Aug 9th
	 * 向交易记录表增加交易记录
	 * @author yuhaozh88
	 * @param itcode
	 * @param account_change
	 * @param money
	 * @param log_time
	 * @param dept_name
	 * @param trade_type
	 * @param jdbcTemplate
	 * @return 返回-1查询失败
	 */
	public static int addTradeLog(String itcode, int account_change, int money, String log_time, String dept_name, String trade_type, JdbcTemplate jdbcTemplate) {
		try {
			return jdbcTemplate.update("insert into trade_log values(?,?,?,?,?,?,?)",new Object[] {null,itcode,account_change,money,log_time,dept_name,trade_type});
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * @date Aug 9th
	 * 查询节目所属的部门
	 * @author yuhaozh88
	 * @param pid
	 * @param jdbcTemplate
	 * @return 返回null查询失败
	 */
	public static String getDeptName(int pid, JdbcTemplate jdbcTemplate) {
		RowMapper<programs> programs_mapper = new BeanPropertyRowMapper<programs>(programs.class);
		try {
			programs program = jdbcTemplate.queryForObject("select * from programs where pid=?", programs_mapper,pid);
			return program.getDept_name();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * @date Aug 9th
	 * 更新部门获得打赏金额
	 * @author yuhaozh88
	 * @param dept_name
	 * @param account_change
	 * @param jdbcTemplate
	 * @return 返回-1更新失败
	 */
	public static int setDepartmentsMoney(String dept_name, int account_change, JdbcTemplate jdbcTemplate) {
		try {
			return jdbcTemplate.update("update departments set money_get=money_get+? where dept_name=?",new Object[] {account_change,dept_name});
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * @date Aug 9th
	 * 更新节目获得打赏金额
	 * @author yuhaozh88
	 * @param pid
	 * @param account_change
	 * @param jdbcTemplate
	 * @return 返回-1更新失败
	 */
	public static int setProgramsMoney(int pid, int account_change, JdbcTemplate jdbcTemplate) {
		try {
			return jdbcTemplate.update("update programs set money_get=money_get+? where pid=?",new Object[] {account_change,pid});
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * @date Aug 10th
	 * 更改用户的在线状态
	 * @author yuhaozh88
	 * @param itcode
	 * @param is_online
	 * @param jdbcTemplate
	 * @return 返回-1更改状态失败
	 */
	public static int setUsersOnlineStatus(String itcode, boolean is_online, JdbcTemplate jdbcTemplate) {
		try {
			return jdbcTemplate.update("update users_info set is_online=? where itcode=?" ,new Object[] {is_online,itcode});
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	
	/**
	 * @date Aug 10th
	 * 通过密码表查看当前itcode用户是否已经注册
	 * @author yuhaozh88
	 * @param itcode
	 * @param jdbcTemplate
	 * @return true表示用户已经注册过，false表示用户还没有注册过
	 */
	public static boolean checkIsRegistered(String itcode, JdbcTemplate jdbcTemplate) {
		RowMapper<users_pwd> users_pwd_mapper = new BeanPropertyRowMapper<users_pwd>(users_pwd.class);
		try {
			List<users_pwd> pwd = jdbcTemplate.query("select * from users_pwd where itcode = ?",users_pwd_mapper,itcode);
			if (pwd == null || pwd.size() < 1) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}
	
	/**
	 * @date Aug 10th
	 * 向聊天信息表中添加聊天信息
	 * @author yuhaozh88
	 * @param itcode
	 * @param nickname
	 * @param message
	 * @param message_time
	 * @param jdbcTemplate
	 * @return 返回-1插入失败
	 */
	public static int addMessages(String itcode, String nickname, String message, String message_time, JdbcTemplate jdbcTemplate) {
		try {
			return jdbcTemplate.update("insert into messages values (?,?,?,?)",new Object[] {itcode,nickname,message,message_time});
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * @date Aug 10th
	 * 查询最近的100条消息
	 * @author yuhaozh88
	 * @param jdbcTemplate
	 * @return 返回null查询失败
	 */
	public static List<messages> getMessages(JdbcTemplate jdbcTemplate){
		RowMapper<messages> messages_mapper = new BeanPropertyRowMapper<messages>(messages.class);
		try {
			List<messages> list = jdbcTemplate.query("select * from messages order by message_time desc limit 100", messages_mapper);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}