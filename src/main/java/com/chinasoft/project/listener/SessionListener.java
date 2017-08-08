package com.chinasoft.project.listener;

import java.util.Hashtable;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

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
    public void attributeAdded(HttpSessionBindingEvent arg0)  { 
         // TODO Auto-generated method stub
    	HttpSession session = arg0.getSession();
    	ServletContext application = session.getServletContext();
    	String itcode = (String)session.getAttribute("itcode");
    	String nickname = (String)session.getAttribute("nickname");
    	if (itcode != null && nickname != null) {//添加itcode和nickname属性
    		//添加全局变量usersOnline，用于保存所有在线用户的itcode和nickname
    		if (application.getAttribute("usersOnline") == null) {//还未初始化HashTable的情况
    			Hashtable<String, users_short_info> usersOnline = new Hashtable<String, users_short_info>();
    			users_short_info user = new users_short_info(itcode,nickname);
    			String sessionId = session.getId();
    			usersOnline.put(sessionId, user);
    			try {
    			application.setAttribute("usersOnline", usersOnline);
    			} catch (Exception e) {
    				// TODO: handle exception
    				e.printStackTrace();
    			}
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
     * @see HttpSessionAttributeListener#attributeRemoved(HttpSessionBindingEvent)
     */
    public void attributeRemoved(HttpSessionBindingEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see HttpSessionAttributeListener#attributeReplaced(HttpSessionBindingEvent)
     */
    public void attributeReplaced(HttpSessionBindingEvent arg0)  { 
         // TODO Auto-generated method stub
    }
	
}
