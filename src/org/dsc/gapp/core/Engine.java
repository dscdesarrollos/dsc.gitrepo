package org.dsc.gapp.core;

import java.util.Properties;

public class Engine {

	public void setUp() {
		// setup proxy
		Properties systemProperties = System.getProperties();
		systemProperties.setProperty(C.SYS_HTTPPROXY_HOST, "ibproxy02.intranet.ibermatica");
		systemProperties.setProperty(C.SYS_HTTPPROXY_PORT, "8080");
		systemProperties.setProperty(C.SYS_HTTPPROXY_USR, "IBERMATICA\bimalose");
		systemProperties.setProperty(C.SYS_HTTPPROXY_PWD, "2015.01.Sml");	}
}
