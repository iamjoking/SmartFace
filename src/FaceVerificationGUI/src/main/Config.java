/** The Configuration System.
 * @author iamjoking
 */

package main;

import common.util.*;

import org.jdom2.*;
import org.jdom2.input.*;
import org.jdom2.output.*;

import java.io.*;
import java.util.*;

public class Config {
	final static int MAXIMUM_RECENT_FILES = 8;
	final static String CONFIG_FILE_NAME = ".ini";
	final static String PROGRAM_FILE_NAME = ".pro";
	
	private static int errorCode = 0;
	private static String errorString = "";
	private static File presentWorkDirectory;
	private static File workstationDirectory;
	private static ArrayList<File> recentFiles;

	private static Properties config;
	private static Properties language;
	private static Properties program;
	private static Document programDocument;
	
	private static Config instance = null;
	
	private Config() {
		if (!new File(CONFIG_FILE_NAME).exists()) {
			setErrorStatus(1,
				"Initial file \"" + CONFIG_FILE_NAME + "\" does not exist!");
			return ;
		}
		if (!readConfigProperty(CONFIG_FILE_NAME)) {
			setErrorStatus(2,
				"Error occurs when loading the config file \"" + CONFIG_FILE_NAME + "\"!");
			return ;
		}
		if (!readLanguageProperty(config.getProperty("lang"))) {
			setErrorStatus(3,
				"Error occurs when loading the language file \"" + config.getProperty("lang") + "\"!");
			return ;
		}
		if (!readProgramDocument(PROGRAM_FILE_NAME)) {
			setErrorStatus(4,
				"Error occurs when loading the program file \"" + PROGRAM_FILE_NAME + "\"!");
			return ;
		}
		
		presentWorkDirectory = new File(config.getProperty("pwd"));
		workstationDirectory = new File(config.getProperty("wsd"));
		recentFiles = new ArrayList<File>();
		String rfNames = config.getProperty("rfs");
		rfNames.split(";");
		for (String fileName : rfNames.split(";")) {
			File temp = new File(fileName);
			if (Project.isProjectFile(temp))
				recentFiles.add(temp);
		}
		
	}
	
	private static void setErrorStatus(int errCode, String errString) {
		errorCode = errCode;
		errorString = errString;
	}
	
	private static boolean readConfigProperty(String propertyName) {
		config = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(propertyName));
			config.load(in);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private static boolean readLanguageProperty(String propertyName) {
		language = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(propertyName));
			language.load(in);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private static boolean readProgramDocument(String documentName) {
		SAXBuilder builder = new SAXBuilder();
		try {
			programDocument = (Document) builder.build(new File(documentName));
		} catch (IOException io) {
			io.printStackTrace();
			return false;
		} catch (JDOMException jdomex) {
			jdomex.printStackTrace();
			return false;
		}
		return true;
	}

	private static Config getInstance() {
		if (instance == null)
			instance = new Config();
		return instance;
	}

	public static int check() {
		getInstance();
		return errorCode;
	}
	
	public static String getErrorString() {
		getInstance();
		return errorString;
	}
	
	public static File getPresentWorkDirectory() {
		getInstance();
		return presentWorkDirectory;
	}

	public static File getWorkstationDirectory() {
		getInstance();
		return workstationDirectory;
	}
	
	public static ArrayList<File> getRecentFiles() {
		getInstance();
		return recentFiles;
	}
	
	public static void addRecentFiles(File[] files) {
		for (int i = 0; i < files.length; i++)
			addRecentFile(files[i]);
	}
	
	public static void addRecentFiles(ArrayList<File> arrayList) {
		for (int i = 0; i < arrayList.size(); i++) {
			addRecentFile(arrayList.get(i));
		}
	}
	
	public static void addRecentFile(File file) {
		getInstance();
		if (!file.exists())
			return ;
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
	
	public static void setLanguage(String langName) {
		getInstance();
		config.setProperty("lang", langName);
	}
	
	public static void changePresentWorkDirectory (File file) {
		getInstance();
System.out.println("Change present work dir to " + file.getPath());
		if (file == null || !file.exists() || !file.isDirectory())
			return ;
		presentWorkDirectory = file;
System.out.println("Change present work dir to " + file.getPath() + ". Done!");
	}

	public static void changeWorkstationDirectory (File file) {
		getInstance();
System.out.println("Change workstation dir to " + file.getPath());
		if (file == null || !file.exists())
			return ;
		workstationDirectory = file;
System.out.println("Change workstation dir to " + file.getPath() + ". Done!");
	}
	
	public static List<Element> getProgramElements(String sectionName) {
		getInstance();
		Element rootNode = programDocument.getRootElement();
		Element sectionElement = rootNode.getChild(sectionName);
		List<Element> list = sectionElement.getChildren("program");
		return list;
	}
	
	public static void save() {
		getInstance();
		config.setProperty("pwd", presentWorkDirectory.getPath());

		String rfNames = new String();
		for (int i = recentFiles.size() - 1; i >= 0; i--) {
			rfNames += recentFiles.get(i) + ";";
		}
		config.setProperty("rfs",rfNames);

		try {
			OutputStream fos = new FileOutputStream(CONFIG_FILE_NAME);
			config.store(fos, "");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main (String[] args) {
		if (Config.check() != 0) {
			javax.swing.JOptionPane.showMessageDialog(null,Config.getErrorString());
			return ;
		}
		Config.addRecentFile(new File("D:\\a.txt"));
		Config.addRecentFile(new File("D:\\b.txt"));
		Config.addRecentFile(new File("D:\\c.txt"));
		Config.addRecentFile(new File("D:\\d.txt"));
		Config.changePresentWorkDirectory(new File("E:\\"));
		Config.save();
	}
}