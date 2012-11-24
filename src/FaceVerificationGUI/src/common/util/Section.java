/** The section of a project.
 * @author iamjoking
 */

package common.util;

import common.util.logging.*;
import common.util.command.*;
import java.util.*;
import java.io.*;

public class Section {
	boolean done = false;
	boolean built = false;
	String title;
	String handler = null;
	Map<String,String> options = new HashMap<String,String>();
	Map<String,String> ioOptions = new HashMap<String,String>();
	
	public Section (String title) {
		this.title = title;
	}
	
	public boolean isDone() {
		return done && handler != null;
	}
	
	public boolean isBuilt() {
		return built;
	}
	
	public boolean setHandler (String string) {
		File file = new File(string);
		if (!file.exists() || !file.canExecute())
			return false;
		handler = string;
		return true;
	}
	
	public void setOption(String option, String value) {
		options.put(option,value);
	}
	
	public void setIoOption (String option, String value) {
		ioOptions.put(option,value);
	}
	
	public void setDone(boolean value) {
		done = value;
	}
	
	public String getCommandString() {
		String commandString  = handler;
		String option;
		Set<String> optionsSet = options.keySet();
		Iterator<String> osIterator = optionsSet.iterator();
		Set<String> ioOptionsSet = ioOptions.keySet();
		Iterator<String> iosIterator = ioOptionsSet.iterator();
		while (osIterator.hasNext()) {
			option = (String)osIterator.next();
			commandString += " -" + option + " " + options.get(option);
		}
		
		while (iosIterator.hasNext()) {
			option = (String)iosIterator.next();
			commandString += " -" + option + " " + ioOptions.get(option);
		}
		
		return commandString;
	}
	
	public boolean exec() {
		if (!isDone()) return false;
		LoggedCommand loggedCommand = new LoggedCommand("Test");
		try {
			loggedCommand.exec(getCommandString());
			System.out.println("result is " + loggedCommand.getResult());
		} catch (IOException e) {
			System.out.println("error!");
			e.printStackTrace();
		}
		built = true;
		return true;
	}
	
	/** Test
	 */
	public static void main(String[] args) {
		LogFactory logFactory = new LogFactory();
		Section section = new Section("Face Detection");
		
		section.setHandler("D:\\Study\\code\\Java\\Java\\Project\\FVAA\\bin\\C++\\bin\\FaceDetection\\FaceDetection.exe");
		section.setOption("x","jpg");
		section.setOption("g","");
		section.setOption("e","");
		section.setIoOption("n","123");
		section.setIoOption("i","D:\\Study\\code\\Java\\Java\\Project\\FVAA\\db\\FG-NET\\003A35.JPG");
		section.setIoOption("d","D:\\Study\\code\\Java\\Java\\Project\\FVAA\\db\\output");
		System.out.println(section.getCommandString());
		section.setDone(true);
		section.exec();
	}
}