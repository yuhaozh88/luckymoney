package com.chinasoft.project.listener;

import java.util.Hashtable;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.chinasoft.project.DAO.DBAccess;
import com.chinasoft.project.model.users_short_info;

/**
 * Application Lifecycle Listener implementation class SessionListener
 *
 */
public class SessionListener implements HttpSessionAttributeListener {

    /**
     * Default constructor. 
     */
    public SessionListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionAttributeListener#attributeAdded(HttpSessionBindingEvent)
     */
    
    
    
    /**
     * @date Aug 10th
     * 用户登录创建session向用户在线列表中添加在线用户信息， 
     */
    public void attributeAdded(HttpSessionBindingEvent arg0)  { 
         // TODO Auto-generated method stub
    	ApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
		JdbcTemplate jdbcTemplate = ctx.getBean("jdbcTemplate", JdbcTemplate.class);
    	HttpSession session = arg0.getSession();
    	ServletContext application = session.getServletContext();
    	String itcode = (String)session.getAttribute("itcode");
    	String nickname = (String)session.getAttribute("nickname");
    	boolean is_online = true;
    	if (itcode != null && nickname != null) {//添加itcode和nickname属性
    		DBAccess.setUsersOnlineStatus(itcode, is_online, jdbcTemplate);
    		//添加全局变量usersOnline，用于保存所有在线用户的itcode和nickname
    		if (application.getAttribute("usersOnline") == null) {//还未初始化HashTable的情况
    			Hashtable<String, users_short_info> usersOnline = new Hashtable<String, users_short_info>();
    			users_short_info user = new users_short_info(itcode,nickname);
    			String sessionId = session.getId();
    			usersOnline.put(sessionId, user);
    			application.setAttribute("usersOnline", usersOnline);
    		} else {//已经初始化HashTable
    			Hashtable<String, users_short_info> usersOnline = (Hashtable<String, users_short_info>)application.getAttribute("usersOnline");
    			users_short_info user = new users_short_info(itcode, nickname);
    			String sessionId = session.getId();
    			usersOnline.put(sessionId, user);
    			application.setAttribute("usersOnline", usersOnline);
    		}
    	}
    }

	/**
	 * @date Aug 10th
	 * 用户注销或session被销毁的时清除在线用户列表中的信息，更改数据库中用户的在线消息
	 * @author yuhaozh88
	 */
    public void attributeRemoved(HttpSessionBindingEvent arg0)  { 
         // TODO Auto-generated method stub
    	ApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
		JdbcTemplate jdbcTemplate = ctx.getBean("jdbcTemplate", JdbcTemplate.class);
    	HttpSession session = arg0.getSession();
    	ServletContext application = session.getServletContext();
    	String sessionID = session.getId();
    	Hashtable<String, users_short_info> usersOnline = (Hashtable<String, users_short_info>)session.getAttribute("usersOnline");
    	//通过sessionID获取离线的简短信息
    	users_short_info user = usersOnline.get(sessionID);
    	if (user != null) {//获取到了离线用户的简短信息
    		String itcode = user.getItcode();
    		boolean is_online = false;
    		usersOnline.remove(sessionID);
    		DBAccess.setUsersOnlineStatus(itcode, is_online, jdbcTemplate);
    	}
    	application.setAttribute("usersOnline", usersOnline);
    }

	/**
     * @see HttpSessionAttributeListener#attributeReplaced(HttpSessionBindingEvent)
     */
    public void attributeReplaced(HttpSessionBindingEvent arg0)  { 
         // TODO Auto-generated method stub
    }
	
}
