package gamepatcher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class ErrorLogger {
	static void logError(Exception e){
//		logs errors
		PrintWriter error = null;
		try {
			e.printStackTrace(error = new PrintWriter(File.createTempFile("error", ".txt", new File(System.getProperty("user.dir")))));
//			prints error to new file.  the file will look like this: error + lots of numbers + .txt
		} catch (FileNotFoundException e1) {
			logError(e);
		} catch (IOException e1) {
			logError(e);
		} finally {
			if(error != null)
				error.close();
		}
	}
}
