package com.slhj.www.edu.shiro.filter;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MySessionListener implements SessionListener {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public void onStart(Session session) {
		// TODO Auto-generated method stub
		this.logger.info("onStart session created:id=" + session.getId()
				+ " begin at " + session.getStartTimestamp());
	}

	public void onStop(Session session) {
		// TODO Auto-generated method stub
		this.logger.info("onStop session created:id=" + session.getId()
				+ " begin at " + session.getStartTimestamp());
		this.logger.info("{}", session.getAttribute("currentUser"));
	}

	public void onExpiration(Session session) {
		// TODO Auto-generated method stub
		this.logger.info("onExpiration session created:id=" + session.getId()
				+ " begin at " + session.getStartTimestamp());
		this.logger.info("{}", session.getAttribute("currentUser"));
	}

}
