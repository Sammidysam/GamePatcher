package gamepatcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class Updater {
	private String filePath;
	private String datePath;
	private String uploadPath;
	private String fileName;
	private String dateName;
	private boolean canMake = true;
	public Updater(String fileName, String dateName, String uploadPath){
		filePath = System.getProperty("user.dir") + File.separatorChar + fileName;
		datePath = System.getProperty("user.dir") + File.separatorChar + dateName;
		this.uploadPath = uploadPath;
		if(uploadPath.charAt(uploadPath.length() - 1) != File.separatorChar){
			System.out.println("uploadPath must end in " + File.separatorChar);
			canMake = false;
		}
		this.fileName = fileName;
		this.dateName = dateName;
	}
	public void update(){
		if(canMake){
			System.out.println("Deleting obsolete files...");
			File uploadFile = new File(uploadPath + fileName);
			File uploadDate = new File(uploadPath + dateName);
			uploadFile.delete();
			uploadDate.delete();
			System.out.println("Updating files...");
			Path originalFile = Paths.get(filePath);
			Path newFile = Paths.get(uploadPath + fileName);
			Path originalDate = Paths.get(datePath);
			Path newDate = Paths.get(uploadPath + dateName);
			try {
				Files.copy(originalFile, newFile, REPLACE_EXISTING);
				Files.copy(originalDate, newDate, REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Update complete");
		}
	}
}
