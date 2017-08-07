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
 * �����ṩ��̬�������ڷ������ݿ�
 * @author yuhaozh88
 *
 */
public class DBAccess {
	
	/**
	 * @author yuhaozh88
	 * �˾�̬��������Ա���ţ������е����Ա����Ϣ�в���ƥ���Ա������
	 * @param itcode
	 * @param jdbcTemplate
	 * @return ����Ա���Ŷ�Ӧ��Ա�����������Ϊnull��ѯʧ��
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
	 * �˾�̬�����������û���Ϣ�������Ϣ
	 * @param itcode �û�����
	 * @param nickname �û��ǳ�
	 * @param dept_name �û���������
	 * @param icon �û�ͷ��
	 * @param is_online �û��Ƿ�����
	 * @param jdbcTemplate
	 * @return ���ص�����Ϊ����ļ�¼����������0Ϊ��������������ֵΪ-1Ϊ���ݿ��쳣
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
	 * �˾�̬�����������û�����������Ϣ
	 * @param itcode �û�����
	 * @param password �û�����
	 * @param jdbcTemplate
	 * @return ���ص�����Ϊ����ļ�¼����������0Ϊ��������������ֵΪ-1Ϊ���ݿ��쳣
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
	 * �˾�̬�����������û��ܱ�����������Ϣ
	 * @param itcode �û�����
	 * @param question �ܱ�����
	 * @param answer �ܱ������
	 * @param jdbcTemplate
	 * @return ���ص�����Ϊ����ļ�¼����������0Ϊ��������������ֵΪ-1Ϊ���ݿ��쳣
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
