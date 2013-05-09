package net.lotusdev.medusa.server;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

/**
 * GetInfo.java
 * @author Jabaar
 *
 */

public class GetInfo {
	public static String getUser() {
		return System.getProperty("user.name"); 
	}
	
	public static String getIp() {
		try {
			String out = new Scanner(new URL("http://www.lotusdev.net/dev/getip.php").openStream(), "UTF-8").useDelimiter("\\A").next();
			return out;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
