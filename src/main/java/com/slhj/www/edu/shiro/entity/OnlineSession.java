package com.slhj.www.edu.shiro.entity;

import org.apache.shiro.session.mgt.SimpleSession;

public class OnlineSession extends SimpleSession {

	private static final long serialVersionUID = -8732182387384142489L;

	public static enum OnlineStatus {
		ONLINE("在线"), KICKOUT("强制退出");
		private OnlineStatus(String info) {
			this.info = info;
		}

		private final String info;

		public String getInfo() {
			return this.info;
		}
	}

	private OnlineStatus status = OnlineStatus.ONLINE;

	public OnlineStatus getStatus() {
		return this.status;
	}

	public void setStatus(OnlineStatus status) {
		this.status = status;
	}
}
