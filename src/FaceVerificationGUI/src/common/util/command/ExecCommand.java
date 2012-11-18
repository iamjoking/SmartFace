/**
 * 
 */
package common.util.command;

/**
 * @author Momo
 *
 */

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import common.util.logging.Logger;

public class ExecCommand {
	private StringBuffer resultBuffer;
	private String command;
	
	public void exec(String command) throws IOException {
		exec(command , null , null);
	}

	public void exec(String command,String workpath) throws IOException {
		exec(command , null , workpath);
	}
	
	public void exec(String command,String[] envp,String workpath) throws IOException {
		InputStream is = null;
		BufferedInputStream in = null;
		BufferedReader br = null;
		try {
			this.command = command;
			File dir=null;
			if(null != workpath)
				dir = new File(workpath);

			is = Runtime.getRuntime().exec(command,envp,dir).getInputStream();
			in = new BufferedInputStream(is);
			br = new BufferedReader(new InputStreamReader(in));
			resultBuffer = new StringBuffer();
			String ss = "";
			while ((ss = br.readLine()) != null) {
				resultBuffer.append(ss).append(System.getProperty("line.separator"));
			}
			resultHandler();
		} finally {
			if (null != br)
				br.close();
			if (null != in)
				in.close();
			if (null != is)
				is.close();
		}
	}
	
	public void exec(String[] commands) throws IOException {
		exec(commands , null , null);
	}

	public void exec(String[] commands,String workpath) throws IOException {
		exec(commands , null , workpath);
	}
	
	public void exec(String[] commands,String[] envp , String workpath) throws IOException {
		InputStream is = null;
		BufferedInputStream in = null;
		BufferedReader br = null;
		try {
			this.command = getCommandString(commands);
			File dir=null;
			if(null != workpath)
				dir=new File(workpath);
			
			is = Runtime.getRuntime().exec(commands,envp,dir).getInputStream();
			in = new BufferedInputStream(is);
			br = new BufferedReader(new InputStreamReader(in));
			
			resultBuffer = new StringBuffer();
			String ss = "";
			while ((ss = br.readLine()) != null) {
				resultBuffer.append(ss).append(System.getProperty("line.separator"));
			}
			resultHandler();
		} finally {
			if (null != br)
				br.close();
			if (null != in)
				in.close();
			if (null != is)
				is.close();
		}
	}

	private String getCommandString(String[] commands){
		StringBuffer sb=new StringBuffer();
		for(String command:commands){
			sb.append(command);
			sb.append(" ");
		}
		return sb.toString();
	}

	public String getCommand() {
		return command;
	}
	
	public String getResult() {
		if (resultBuffer == null)
			return null;
		return resultBuffer.toString();
	}
	
	protected void resultHandler() {
		// Default : Do nothing
	}
}

