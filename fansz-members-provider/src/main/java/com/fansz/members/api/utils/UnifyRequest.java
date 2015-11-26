package com.fansz.members.api.utils;

public class UnifyRequest<T> {
	
	private String session;
	private String deciceId;
	private T context;
	private String timeStamp;
	
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}
	public String getDeciceId() {
		return deciceId;
	}
	public void setDeciceId(String deciceId) {
		this.deciceId = deciceId;
	}
	public T getContext() {
		return context;
	}
	public void setContext(T context) {
		this.context = context;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

}
