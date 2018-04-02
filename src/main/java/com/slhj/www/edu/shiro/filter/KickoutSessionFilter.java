package com.slhj.www.edu.shiro.filter;

import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 访问控制过滤器，继承AccessControlFilter
 * 
 * @author wanghang
 * 
 */
public class KickoutSessionFilter extends AccessControlFilter {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private boolean kickoutAfter = false;
	private int maxSession = 1; // 最大的session数量
	private SessionManager sessionManager; // 安全管理器
	private Cache<String, Deque<Serializable>> cache; //用来保存用户缓存


	public void setKickoutAfter(boolean kickoutAfter) {
		this.kickoutAfter = kickoutAfter;
	}

	public void setMaxSession(int maxSession) {
		this.maxSession = maxSession;
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cache = cacheManager.getCache("shiro-kickout-session");
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest arg0,
			ServletResponse arg1, Object arg2) throws Exception {
		// TODO Auto-generated method stub
		Subject subject = getSubject(arg0, arg1);
		if (subject.isAuthenticated() || !subject.isRemembered())
			return true;
		Session session = subject.getSession();
		Serializable sessionId = session.getId();
		Boolean marker = (Boolean) session.getAttribute("kickoutSession");
		this.logger.info("marker={}", marker);
		if (marker != null && marker.booleanValue()) { //
			return false;
		}

		/** 将新用户加入缓存**/
		String username = (String) subject.getPrincipal();
		Deque<Serializable> deque = (Deque) this.cache.get(username);
		if (deque == null) {
			deque = new LinkedList<Serializable>();
			this.cache.put(username, deque);
		}

		if ((!deque.contains(sessionId)) && (marker == null)) {
			deque.push(sessionId);
		}

		while (deque.size() > this.maxSession) { //如果session大于系统最大能够保存的session数量
			Serializable kickoutSessionId = null;
			if (this.kickoutAfter) {
				kickoutSessionId = (Serializable) deque.removeFirst();
			} else {
				kickoutSessionId = (Serializable) deque.removeLast();
			}
			try {
				Session kickoutSession = this.sessionManager
						.getSession(new DefaultSessionKey(kickoutSessionId));
				if (kickoutSession != null) {
					kickoutSession.setAttribute("kickoutSession",
							Boolean.valueOf(true));
				}
			} catch (Exception e) {
				this.logger.error("", e);
			}
		}
		return true;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest arg0, ServletResponse arg1)
			throws Exception {
		// TODO Auto-generated method stub
		Subject subject = getSubject(arg0, arg1);
		   subject.logout();
		WebUtils.getSavedRequest(arg0);
		return false;
	}

}
