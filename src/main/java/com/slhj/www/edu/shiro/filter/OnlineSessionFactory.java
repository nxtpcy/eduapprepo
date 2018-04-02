package com.slhj.www.edu.shiro.filter;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.slhj.www.edu.shiro.entity.OnlineSession;

public class OnlineSessionFactory implements SessionFactory {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public Session createSession(SessionContext initData) {
		// TODO Auto-generated method stub
		OnlineSession session = new OnlineSession();
		this.logger.info("OnlineSessionFactory.createSession={}",
				new Object[] { session.getStatus() });
		return session;
	}

}
