package gamepatcher;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Patcher {
	protected String pingURL;
	protected void setUserDir(){
//		sets property "user.dir" to the one specified, or leaves it as default if nothing is specified or an invalid location is specified
		File file = new File(System.getProperty("user.dir") + File.separatorChar + "settings.txt");
		if(file.exists()){
			Scanner scanner = null;
			try {
				scanner = new Scanner(file);
				if(scanner.hasNext()){
					String location = scanner.nextLine();
					if(new File(location).exists())
						System.setProperty("user.dir", location);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				scanner.close();
			}
		}
		else {
			try {
				makeSettings(file);
				setUserDir();
			} catch (IOException e) {
				ErrorLogger.logError(e);
			}
		}
	}
	protected void setPingURL(){
//		sets variable pingURL to the ping URL specified in settings
		File file = new File(System.getProperty("user.dir") + File.separatorChar + "settings.txt");
		if(file.exists()){
			Scanner scanner = null;
			try {
				scanner = new Scanner(file);
				if(scanner.hasNext())
					scanner.nextLine();
				if(scanner.hasNext()){
					String url = scanner.nextLine();
					if(new URL(url).getHost() != null)
						pingURL = url;
				}
			} catch (FileNotFoundException e) {
				ErrorLogger.logError(e);
			} catch (MalformedURLException e) {
				ErrorLogger.logError(e);
			} finally {
				scanner.close();
			}
		}
		else {
			try {
				makeSettings(file);
				setPingURL();
			} catch (IOException e) {
				ErrorLogger.logError(e);
			}
		}
	}
	private void makeSettings(File settings) throws IOException {
//		makes the settings file and inputs default values (line 1-"", line 2-"https://github.com/")
		if(settings.createNewFile()){
			FileWriter settingsWriter = new FileWriter(settings);
			BufferedWriter bw = new BufferedWriter(settingsWriter);
			bw.newLine();
			bw.write("https://github.com/");
			bw.close();
		}
		else
			return;
	}
}
