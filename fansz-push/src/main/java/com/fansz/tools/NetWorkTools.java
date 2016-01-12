package com.fansz.tools;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetWorkTools {
	private final static String DEFAULT_IP_ADDR = "127.0.0.1";

	public static String getLocalIpAddress() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return DEFAULT_IP_ADDR;
		}
	}
}
