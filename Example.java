import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gamepatcher.Downloader;

@SuppressWarnings("serial")
public class Main extends JPanel {
	private Downloader downloader;
	private JFrame frame;
	public Main(){
		super(new BorderLayout());
	}
	public static void main(String[] args){
        final Main panel = new Main();
		panel.downloader = new Downloader("PixelZombies.zip", "date.txt", "http://sammidysam.github.com/PixelZombies/PixelZombies.zip", "http://sammidysam.github.com/PixelZombies/date.txt", 1000);
		if(panel.downloader.isUpdateNecessary()){
			panel.frame = new JFrame("PixelZombies Game Launcher");
	        panel.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        panel.frame.setContentPane(panel);
	        panel.frame.setResizable(false);
	        panel.frame.setLocationRelativeTo(null);
	        JTextField text = new JTextField("Would you like to download an update?");
	        text.setEditable(false);
	        panel.frame.add(text, BorderLayout.PAGE_START);
	        JButton yes = new JButton("Yes");
	        yes.setPreferredSize(new Dimension(110, 20));
	        yes.addActionListener(new ActionListener(){
	        	public void actionPerformed(ActionEvent e){
	        		panel.downloader.downloadFiles();
	        		panel.buttonClicked();
	        	}
	        });
	        panel.frame.add(yes, BorderLayout.LINE_START);
	        JButton no = new JButton("No");
	        no.setPreferredSize(new Dimension(110, 20));
	        no.addActionListener(new ActionListener(){
	        	public void actionPerformed(ActionEvent e){
	        		panel.buttonClicked();
	        	}
	        });
	        panel.frame.add(no, BorderLayout.LINE_END);
	        panel.frame.pack();
	        panel.frame.setVisible(true);
		}
		else {
			if(panel.downloader.getDownloaded()){
				try {
					extractFolder(System.getProperty("user.dir") + File.separatorChar + "PixelZombies.zip");
					System.out.println("Extraction of zip file complete");
				} catch (ZipException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(new File(System.getProperty("user.dir") + File.separatorChar + "PixelZombies.jar").exists()){
				System.out.println("Launching jar...");
				ProcessBuilder pb = new ProcessBuilder("java", "-jar", System.getProperty("user.dir") + File.separatorChar + "PixelZombies.jar");
				pb.directory(new File(System.getProperty("user.dir")));
				try {
					pb.inheritIO();
					pb.start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else
				System.out.println("Could not find jar file, will not launch");
		}
	}
	private static void extractFolder(String zipFile) throws ZipException, IOException {
	    System.out.println("Extracting zip...");
	    int BUFFER = 2048;
	    File file = new File(zipFile);
	    @SuppressWarnings("resource")
		ZipFile zip = new ZipFile(file);
	    String newPath = zipFile.substring(0, zipFile.lastIndexOf(File.separatorChar));
	    new File(newPath).mkdir();
	    Enumeration<? extends ZipEntry> zipFileEntries = zip.entries();
	    while(zipFileEntries.hasMoreElements()){
	        ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
	        String currentEntry = entry.getName();
	        File destFile = new File(newPath, currentEntry);
	        File destinationParent = destFile.getParentFile();
	        destinationParent.mkdirs();
	        if(!entry.isDirectory()){
	            BufferedInputStream is = new BufferedInputStream(zip.getInputStream(entry));
	            int currentByte;
	            byte data[] = new byte[BUFFER];
	            FileOutputStream fos = new FileOutputStream(destFile);
	            BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);
	            while((currentByte = is.read(data, 0, BUFFER)) != -1){
	                dest.write(data, 0, currentByte);
	            }
	            dest.flush();
	            dest.close();
	            is.close();
	        }
	        if(currentEntry.endsWith(".zip")){
	            extractFolder(destFile.getAbsolutePath());
	        }
	    }
	}
	public void paintComponent(Graphics g){
		
	}
	private void buttonClicked(){
		frame.dispose();
		if(downloader.getDownloaded()){
			try {
				extractFolder(System.getProperty("user.dir") + File.separatorChar + "PixelZombies.zip");
				System.out.println("Extraction of zip file complete");
			} catch (ZipException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if(new File(System.getProperty("user.dir") + File.separatorChar + "PixelZombies.jar").exists()){
			System.out.println("Launching jar...");
			ProcessBuilder pb = new ProcessBuilder("java", "-jar", System.getProperty("user.dir") + File.separatorChar + "PixelZombies.jar");
			pb.directory(new File(System.getProperty("user.dir")));
			try {
				pb.inheritIO();
				pb.start();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		else
			System.out.println("Could not find jar file, will not launch");
	}
}
