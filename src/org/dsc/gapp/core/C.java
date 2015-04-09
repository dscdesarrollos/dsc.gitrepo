package org.dsc.gapp.core;

public interface C {
	public static final String SYS_HTTPPROXY_HOST = "http.proxyHost";
	public static final String SYS_HTTPPROXY_PORT = "http.proxyPort";
	public static final String SYS_HTTPPROXY_USR = "http.proxyUser";
	public static final String SYS_HTTPPROXY_PWD = "http.proxyPassword";
	
	public static final long SEC_INMLSECS = 1000;
	public static final long MIN_INSECS = 60 * SEC_INMLSECS;
	public static final long HOUR_INSECS = 60 * MIN_INSECS;
	public static final long DAY_INSECS = 24 * HOUR_INSECS;
	public static final long WEEK_INSECS = 7 * DAY_INSECS;
	
	
	public static final String ERR_SVC_RSP = "NO DISPONIBLE";
	
	public static final String GBP = "GBP";
	public static final String EUR = "EUR";
	public static final String YEN = "EUR";

}
