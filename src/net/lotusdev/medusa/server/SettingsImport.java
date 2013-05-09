package net.lotusdev.medusa.server;

/**
 * SettingsImport.java
 * @author Jabaar
 *
 */

public class SettingsImport {
	/**
	 * This is the .java class that will be overwritten then turned into a .class with the set user information
	 * and packed into the stub for access. (building blocks to the stub).
	 * 
	 * Here's how it goes:
	 * 1. The client will modify this file and convert from .java file to .class.
	 * 2. Encrypt the new .class to keep priviate information safe (email password).
	 * 3. Pack into the stub when being built and overwrite this template with new info.
	 * 4. Create copy of stub, encrypt, and seal off packages.
	 */
	
	public static String getEmailUser() {
		/**
		 * If you are wanting to test this, you put your email here.
		 */
		return "user@host.com";
	}
	
	public static String getEmailName() {
		/**
		 * Gets username from email (first part of email in front of "@").
		 */
		String[] emailSplit = getEmailUser().split("@");
		return emailSplit[0];
	}
	
	public static String getEmailPass() {
		/**
		 * If you are wanting to test this, you put your email password here.
		 */
		return "password";
	}
	
	public static String getEmailHost() {
		return "smtp.gmail.com";
	}
	
	public static String getEmailPort() {
		return "465";
	}
	
	public static int getTimeout() {
		return Integer.parseInt("1");
	}
}
