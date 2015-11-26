package com.fansz.members.api.utils;

@Data
@AllArgsConstructor
public class UnifyResponse<T>{

	private String code;
	private String info;
	private long afterCount;
	private T content;

}
