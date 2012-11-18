/** Define several file operations.
 * @author iamjoking
 */

package common.util;

import java.io.File;
 
public abstract class FileOperation {
	public boolean delete(File file) {
		if (!file.exists())
			return false;
		
		if (file.isDirectory()) {
			File files[] = file.listFiles();
			for (File f : files)
				delete(f);
		}
		file.delete();
		
		return true;
	}
	
	abstract public boolean open(File file);
}