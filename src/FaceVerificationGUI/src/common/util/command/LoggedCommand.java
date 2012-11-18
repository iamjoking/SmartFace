/**
 * 
 */
package common.util.command;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import common.util.logging.LogFactory;
import common.util.logging.Logger;

/**
 * @author Momo
 *
 */
public class LoggedCommand extends ExecCommand {
	private String ownner;
	private Logger logger;
	
	public LoggedCommand () {
		this("********");
	}
	
	public LoggedCommand (String Ownner) {
		logger = new Logger(ownner);
	}

	@Override
	protected void resultHandler() {
		logger.info(getCommand() + getResult());
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LogFactory logFactory = new LogFactory();
		LoggedCommand loggedCommand = new LoggedCommand("Test");
		try {
			//loggedCommand.exec("cmd /c C++\\FaceDetection\\FaceDetection -i D:\\Study\\code\\Java\\Java\\Project\\FVAA\\db\\FG-NET\\003A35.JPG -d D:\\Study\\code\\Java\\Java\\Project\\FVAA\\db\\output -x jpg");
			//loggedCommand.exec(new String[] {"FaceDetection\\FaceDetection.exe","-l","1.JPG","-d",".\\","-x","jpg"});
			String input = "C++\\FaceDetection\\FaceDetection.exe -i 1.JPG -d . -x jpg -e -g";
			int firstSplit = input.indexOf(".exe") + 4; //account for length of ".exe"
			String command = input.substring(0,firstSplit);
			String args1 = input.substring(firstSplit).trim(); //trim off extraneous whitespace
			String[] argarray = args1.split(" ");
			String[] cmdargs = new String[argarray.length + 1];
			cmdargs[0] = command;
			for (int i = 0; i < argarray.length; i++) {
			    cmdargs[i+1] = argarray[i];
			    System.out.println(cmdargs[i+1]);
			}
			loggedCommand.exec("C++\\FaceDetection\\FaceDetection -i ..\\..\\1.JPG -d ..\\..\\ -x jpg -e -g",null,"C++\\FaceDetection\\");
			System.out.println("command is : " + loggedCommand.getCommand());
			System.out.println("result is : " + loggedCommand.getResult());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
