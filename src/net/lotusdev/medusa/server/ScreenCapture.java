package net.lotusdev.medusa.server;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * ScreenCapture.java
 * @author Jabaar
 *
 */

public class ScreenCapture {	
	public static void captureScreen() {
		try {
			/**
			 * Takes an image and saves it to the AppData folder as a temp file.
			 */
			BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			ImageIO.write(image, "png", new File(System.getenv("APPDATA") + "/scr_tmp.png"));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void delete() {
		try {
			/**
			 * Deletes the file.
			 */
			File scr_tmp = new File(System.getenv("APPDATA") + "/scr_tmp.png");
			scr_tmp.delete();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
