package gamepatcher;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.TimeZone;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class Updater extends Patcher {
	private String filePath;
	private String uploadPath;
	private String fileName;
	private String dateName;
	private boolean canMake = true;
	public Updater(String fileName, String dateName, String uploadPath){
		setUserDir();
		filePath = System.getProperty("user.dir") + File.separatorChar + fileName;
		this.uploadPath = uploadPath;
		if(uploadPath.charAt(uploadPath.length() - 1) != File.separatorChar){
//			if the upload path does not end in the separator character ('\\' on Windows), then the updater will not update anything
			System.out.println("uploadPath must end in " + File.separatorChar);
			canMake = false;
		}
		this.fileName = fileName;
		this.dateName = dateName;
	}
	public void update(){
		if(canMake){
//			the file will first be copied to the update directory
			System.out.println("Deleting obsolete files...");
			File uploadFile = new File(uploadPath + fileName);
			File uploadDate = new File(uploadPath + dateName);
			if(uploadFile.exists())
				if(!uploadFile.delete())
					return;
			if(uploadDate.exists())
				if(!uploadDate.delete())
					return;
			System.out.println("Updating files...");
			Path originalFile = Paths.get(filePath);
			Path newFile = Paths.get(uploadPath + fileName);
			try {
				Files.copy(originalFile, newFile, REPLACE_EXISTING);
			} catch (IOException e) {
				ErrorLogger.logError(e);
				return;
			}
			System.out.println("File updated.  Timestamping...");
//			then the time of update will be timestamped so that it can be distributed to all jars checking for update
			File file = new File(uploadPath + dateName);
			try {
				if(!file.exists())
					if(!file.createNewFile())
						return;
				if(file.exists()){
					if(!file.delete())
						return;
					else {
						if(!file.createNewFile())
							return;
					}
				}
			} catch (IOException e1) {
				e1.printStackTrace();
				return;
			}
			if(file.exists()){
				Calendar calendar = Calendar.getInstance();
				String[] values = new String[6];
				TimeZone utc = TimeZone.getTimeZone("UTC");
				calendar.setTimeZone(utc);
				values[0] = String.valueOf(calendar.get(Calendar.YEAR));
				values[1] = String.valueOf(calendar.get(Calendar.MONTH) + 1);
				values[2] = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
				values[3] = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
				values[4] = String.valueOf(calendar.get(Calendar.MINUTE));
				values[5] = String.valueOf(calendar.get(Calendar.SECOND));
				BufferedWriter bw = null;
				try {
					FileWriter dateWriter = new FileWriter(file);
					bw = new BufferedWriter(dateWriter);
					for(int i = 0; i < values.length; i++){
						bw.write(values[i]);
						bw.newLine();
					}
				} catch (IOException e) {
					ErrorLogger.logError(e);
					return;
				} finally {
					if(bw != null)
						try {
							bw.close();
						} catch (IOException e) {
							ErrorLogger.logError(e);
							return;
						}
				}
				System.out.println("Timestamp complete");
			}
			System.out.println("Update complete");
		}
	}
}
