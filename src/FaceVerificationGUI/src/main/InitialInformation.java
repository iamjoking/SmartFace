/** Collect initial information
 * @author iamjoking
 */

package main;

import common.util.Project;
import java.io.*;
import java.util.*;

public class InitialInformation {
	final static int MAXIMUM_RECENT_FILES = 8;
	final static String INITIAL_FILE_PATH = "fvaa.ini";
	int errorCode = 0;
	String errorString = "";
	File presentWorkDirectory = new File(".");
	ArrayList<File> recentFiles = new ArrayList<File>();
	File initInfoFile;
	
	public InitialInformation() {
		this(INITIAL_FILE_PATH);
	}
	
	public InitialInformation(String path) {
		initInfoFile = new File(path);
		Scanner scanner;
		String lineString, itemString, valueString;
		File temp;
		int split = 0;
		try {
			scanner = new Scanner(initInfoFile);
			while (scanner.hasNext()) {
				lineString = scanner.nextLine();
				split = lineString.indexOf("=");
				itemString = lineString.substring(0,split);
				valueString = lineString.substring(split + 1);
				if (itemString.equals("pwd")) {
					temp = new File(valueString);
					if (temp.exists() && temp.isDirectory())
						presentWorkDirectory = temp;
				}
				else if (itemString.equals("recentfiles")) {
					for (String fileName : valueString.split(";")) {
						temp = new File(fileName);
						if (Project.isProjectFile(temp))
							recentFiles.add(temp);
					}
				}
			}
		} catch (FileNotFoundException e1) {
			errorCode = 1;
			errorString = "Initial file(.ini) \"" + INITIAL_FILE_PATH + "\" does not exist!";
		}
	}
	
	public int check() {
		return errorCode;
	}
	
	public String getErrorString() {
		return errorString;
	}
	
	public File getPresentWorkDirectory() {
		return presentWorkDirectory;
	}
	
	public ArrayList<File> getRecentFiles() {
		return recentFiles;
	}
	
	public void addRecentFiles(ArrayList<File> arrayList) {
		for (int i = 0; i < arrayList.size(); i++) {
			addRecentFile(arrayList.get(i));
		}
	}
	
	public void addRecentFile(File file) {
		if (recentFiles.contains(file)) {
			recentFiles.remove(file);
			recentFiles.add(file);
		}
		else {
			if (recentFiles.size() >= MAXIMUM_RECENT_FILES)
				recentFiles.remove(0);
			recentFiles.add(file);
		}
	}
	
	public void save() {
		if (initInfoFile.exists() && initInfoFile.isFile()) {
			/*initInfoFile.delete();
			initInfoFile.createNewFile();
			*/
			
			FileWriter fw;
			try {
				fw = new FileWriter(initInfoFile);
				fw.append("pwd=" + presentWorkDirectory + System.getProperty("line.separator"));
				fw.append("recentfiles=");
				for (int i = recentFiles.size() - 1; i >= 0; i--) {
					fw.append(recentFiles.get(i) + ";");
				}
				fw.flush();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static File getCwd() {
		return new InitialInformation().getPresentWorkDirectory();
	}
}