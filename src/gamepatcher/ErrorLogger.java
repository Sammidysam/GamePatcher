package gamepatcher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

public class ErrorLogger {
	static void logError(Exception e){
//		logs errors
		PrintWriter error = null;
		try {
			error = new PrintWriter(File.createTempFile("error", ".txt", new File(System.getProperty("user.dir"))));
			writeCurrentDate(error, false);
			e.printStackTrace(error);
//			prints error to new file.  the file will look like this: error + lots of numbers + .txt
			System.out.println("Error " + e.toString() + " occured.  Error log will be stored in " + new File(System.getProperty("user.dir")).getAbsolutePath());
			System.out.println("The game patcher will stop trying to patch due to this error.");
		} catch (FileNotFoundException e1) {
			logError(e);
		} catch (IOException e1) {
			logError(e);
		} finally {
			if(error != null)
				error.close();
		}
	}
	private static void writeCurrentDate(PrintWriter pw, boolean shouldClose){
		if(pw == null)
			return;
		Calendar currentDate = Calendar.getInstance();
		pw.write("The format for this datestamp is Month/Day/Year Hour:Minute:Second AM_PM" + System.getProperty("line.separator"));
		pw.write("On " + (currentDate.get(Calendar.MONTH) + 1) + '/' + currentDate.get(Calendar.DAY_OF_MONTH) + '/' + currentDate.get(Calendar.YEAR) + ' ' + convertHour(currentDate.get(Calendar.HOUR)) + ':' + addZeroIfNecessary(currentDate.get(Calendar.MINUTE)) + ':' + addZeroIfNecessary(currentDate.get(Calendar.SECOND)) + ' ' + convertAMPM(currentDate.get(Calendar.AM_PM)) + System.getProperty("line.separator"));
		if(shouldClose)
			pw.close();
	}
	private static int convertHour(int hour){
		return hour == 0 ? 0 : hour;
	}
	private static String convertAMPM(int AMPM){
		return AMPM == 1 ? "PM" : "AM";
	}
	private static String addZeroIfNecessary(int time){
		return time > 9 ? String.valueOf(time) : "0" + String.valueOf(time);
	}
}
