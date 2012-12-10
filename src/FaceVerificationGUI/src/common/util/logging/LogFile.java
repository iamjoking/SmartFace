/**
 * 
 */
package common.util.logging;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.Date;

/** The Log File
 * @author Momo
 *
 */
public class LogFile {
	private File file;
	private int maxSize;
	private int count;
	
	public LogFile (File dir, String name, int size) {
		System.out.println("file's name is " + name);
		file = new File(dir,name);
		maxSize = size;
		count = readCount();
	}
	
	public LogFile (File dir, int size) {
		file = new File(dir,String.valueOf(new Date().getTime()) + ".log");
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		maxSize = size;
		count = 0;
		writeCount(count);
	}
	
	private int readCount() {
		if (file == null)
			return 0;
		
		BufferedReader br;
		int ret = 0;
		
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String formatString = br.readLine();
			formatString = formatString.substring(0, formatString.indexOf(" "));
			ret = Integer.parseInt(formatString);
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ret;
	}
	
	private void writeCount (int count){
		try {
			RandomAccessFile raf = new RandomAccessFile(file,"rw");
			String countString = String.valueOf(count);
			String blank = generateSpace(10 - countString.length());
			byte[] bytes = countString.concat(blank).concat(System.getProperty("line.separator")).getBytes();
			raf.write(bytes);
			raf.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void appandContent (String content) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file,true);
			fos.write(content.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void updateName () {
		File newFile = new File(file.getParent() 
				+ System.getProperty("file.separator") 
				+ String.valueOf(new Date().getTime()).concat(".log"));
		file.renameTo(newFile);
		file.delete();
		file = newFile;
	}
	
	@SuppressWarnings("deprecation")
	public boolean append (String content) {
		if (isFull())
			return false;
		writeCount(++count);
		appandContent("[" + new Date().toLocaleString() + "]" + content + System.getProperty("line.separator"));
		updateName();
		return true;
	}
	
	public StringBuffer content () {
		StringBuffer sBuffer = new StringBuffer();
		if (file == null)
			return sBuffer;
		
        String line;
        BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
	        line = reader.readLine(); 
	        line = reader.readLine();
	        while (line != null) {
	        	sBuffer.append(line);
	        	sBuffer.append(System.getProperty("line.separator"));
	            line = reader.readLine();
	        }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sBuffer;
	}
	
	public boolean isFull () {
		return count >= maxSize;
	}
	
	public boolean isEmpty () {
		return count == 0;
	}
	
	public void empty () {
		file.delete();
		count = 0;
		writeCount(count);
	}
	
	public static String generateSpace(int count) {
        if(count < 0) {
            throw new IllegalArgumentException("count must be great equal than 0.");
        }
        char[] chs = new char[count];
        for(int i = 0; i < count; i++) {
            chs[i] = ' ';
        }
        return new String(chs);
    }
	
	public static Date getDateFromFileName(String string) {
		String dateString = string.substring(0, string.lastIndexOf("."));
		try {
			return new Date(Long.parseLong(dateString));
		} catch (Exception e) {
			return null;
 	   	}
	}
	
	public static boolean isLogFileName (String name) {
 	   if (!name.endsWith(".log") || getDateFromFileName(name) == null)
		   return false;
	   return true;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File dir = new File("LogFile\\");
		LogFile logFile = new LogFile(dir,10);
		for (int i = 0; i < 25; i++)
			if(!logFile.append("No." + i)) {
				logFile.empty();
				i--;
			}
	}
}
