/** The project class
 * @author iamjoking
 */

package common.util;

import java.util.*;
import java.io.*;

public class Project {
	File projectFile;
	final static String PROJECT_FILE_EXTENSION = ".proj";
	
	public Project(File file) {
		projectFile = file;
		if (!isProjectFile()) return ;
		
		
	}

	public boolean isProjectFile() {
		return Project.isProjectFile(projectFile);
	}
	
	
	public static boolean isProjectFile(File file) {
		return file.exists() && file.isFile() && file.getPath().endsWith(PROJECT_FILE_EXTENSION);
	}
	
	/** Test
	 */
	public static void main (String[] args) {
	
	}

}
