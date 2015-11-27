package com.fansz.members.api.utils;

public class UnifyResponse<T>{

	private String code;
	private String info;
	private long afterCount;
	private T content;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public long getAfterCount() {
		return afterCount;
	}

	public void setAfterCount(long afterCount) {
		this.afterCount = afterCount;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}
}
