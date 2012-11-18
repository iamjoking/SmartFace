package common.util.logging;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.util.Date;

/** 日志管理器 使用多文件管理
 * @author Momo
 */
public class LogFactory {
	private static String logPath = "LogFile" + System.getProperty("file.separator");
	private static int logFileAmount = 2;
	private static int logsPerFile = 10;
	
	private static LogFile[] logFiles = null;
	private static int index;

	public LogFactory () {
		this(logPath,logFileAmount,logsPerFile);
	}
	public LogFactory (int logAmount) {
		this(logPath,logFileAmount,logAmount);
	}
	public LogFactory (String logPath, int logAmount) {
		this(logPath,logFileAmount,logAmount);
	}
	
	public LogFactory (String logPath, int logFileAmount, int logsPerFile) {
		initialVariables(logPath,logFileAmount,logsPerFile);
		File dir = new File(LogFactory.logPath);
		initialLogFiles(dir);
	}
	
	private static void initialVariables (String logPath, int logFileAmount, int logsPerFile)  {
		File dir = new File(logPath);
		if (dir.exists())
			LogFactory.logPath = logPath;
		LogFactory.logFileAmount = logFileAmount;
		LogFactory.logsPerFile = logsPerFile;
	}
	
	private static void initialLogFiles(File dir) {
		String[] logFileList = dir.list(new FilenameFilter(){	// 筛选出符合要求的文件名
		       public boolean accept(File dir, String name){
		    	   return LogFile.isLogFileName(name);
		         }
		       });
		
		logFiles = new LogFile[logFileAmount];
		
		if (logFileList == null || logFileList.length == 0) {
			for (int i = 0; i < logFileAmount; i++)
				logFiles[i] = new LogFile(dir,logsPerFile);
			index = logFileAmount - 1;
			return ;
		}
		
		if (logFileList.length < logFileAmount) {
			for (int i = 0; i < logFileList.length; i++)
				logFiles[i] = new LogFile(dir,logFileList[i],logsPerFile);
			for (int i = logFileList.length; i < logFileAmount; i++)
				logFiles[i] = new LogFile(dir,logsPerFile);
			index = logFileAmount - 1;			
		}
		else {
			for (int i = 0; i < logFileAmount; i++)
				logFiles[i] = new LogFile(dir,
					logFileList[logFileList.length -logFileAmount + i],logsPerFile);
			index = logFileAmount - 1;
		}
		
		return ;
	}

	public static void info (String content) {
		if (logFiles[index].isFull()) {
			index = (index + 1) % logFileAmount;
			logFiles[index].empty();
		}
		logFiles[index].append(content);
	}
	
	public static StringBuffer logContent () {
		StringBuffer sBuffer = new StringBuffer();
		int start = (index + 1) % logFileAmount;
		
		for (int i = 0; i < logFileAmount; i++)
			sBuffer.append(logFiles[(i + start) % logFileAmount].content());

		return sBuffer;
	}
	
	/** The test method.
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LogFactory logFactory = new LogFactory();
		for (int i = 0; i < 45; i++)
			LogFactory.info("No." + i);
		LogFactory.info("Siu-Tung Wang 1--It is a test!\nThank you.");
		LogFactory.info("Siu-Tung Wang 2--It is a test!\nThank you.");
		LogFactory.info("Siu-Tung Wang 3--It is a test!\nThank you.");
		LogFactory.info("Siu-Tung Wang 哈哈哈");
		System.out.println("end>>>>>>>>>>>>>");
	}

}
