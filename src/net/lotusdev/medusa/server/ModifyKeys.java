package net.lotusdev.medusa.server;

/**
 * ModifyKeys.java
 * @author Jabaar
 *
 */

public class ModifyKeys {
	public static String getModifiedKey(String key) {
		/**
		 * Harcoded because I am not sure of an easier way to do this...
		 * Simple method to modify keycodes that aren't letters or numbers into actual keystrokes.
		 */
		if(key.equals("Enter") || key.equals("Backspace") || key.equals("Right") || key.equals("Left") || key.equals("Up") || key.equals("Down") || key.equals("Up") || key.equals("Up") ||
				key.equals("Insert") || key.equals("Home") || key.equals("Page Up") || key.equals("Delete") || key.equals("End") || key.equals("Page Down") || key.equals("Tab") || key.equals("Caps Lock") ||
				key.equals("Print Screen") || key.equals("Scroll Lock") || key.equals("Pause") || key.equals("Num Lock") || key.equals("Context Menu") || key.equals("Escape") || key.equals("Windows") ||
				key.equals("Alt")) {
			return newString(key);
		}else if(key.equals("Undefined")) {
			return "";
		}else if(key.equals("Semicolon")) {
			return ";";
		}else if(key.equals("Comma")) {
			return ",";
		}else if(key.equals("Period")) {
			return ".";
		}else if(key.equals("Ctrl")) {
			return ">Ctrl+";
		}else if(key.equals("Equals")) {
			return "=";
		}else if(key.equals("Open Bracket")) {
			return "[";
		}else if(key.equals("Close Bracket")) {
			return "]";
		}else if(key.equals("Shift")) {
			return ">Shift+";
		}else if(key.startsWith("NumPad")) {
			String arg[] = key.split(" ");
			return " -" + arg[0] + " " + arg[1] + "- ";
		}else if(key.equals("Back Slash")) {
			return "\\";
		}else if(key.equals("Dead Acute")) {
			return "/";
		}else if(key.equals("Space")) {
			return " ";
		}else if(key.equals("Minus")) {
			return "-";
		}else if(key.equals("Back Quote")) {
			return "`";
		}else if(key.startsWith("F")) {
			/**
			 * My way of modifying F1-F12 keys with a simple 2 step block.
			 */
			for(char alphabet = '1'; alphabet <= '9'; alphabet++) {
				if(key.length() > 2) {
					for(char alphabet1 = '0'; alphabet1 <= '2'; alphabet1++) {
						key = key.replace("F" + alphabet + alphabet1, newString("F" + alphabet + alphabet1));
					}
				}else {
					key = key.replace("F" + alphabet, newString("F" + alphabet));
				}
			}
			return key;
		}else if(key.equals("Quote")) {
			return "'";
		}else {
			return key;
		}
	}
	
	public static String newString(String key) {
		return " -" + key + "- ";
	}
	
	public static String getPrime(char letter) {
		if(letter == '0') {
			return ")";
		}else if(letter == '1') {
			return "!";
		}else if(letter == '2') {
			return "@";
		}else if(letter == '3') {
			return "#";
		}else if(letter == '4') {
			return "$";
		}else if(letter == '5') {
			return "%";
		}else if(letter == '6') {
			return "^";
		}else if(letter == '7') {
			return "&";
		}else if(letter == '8') {
			return "*";
		}else if(letter == '9') {
			return "(";
		}else {
			return "";
		}
	}
}
