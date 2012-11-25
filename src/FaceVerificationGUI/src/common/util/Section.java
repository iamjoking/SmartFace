/** The section of a project.
 * @author iamjoking
 */

package common.util;

import common.util.logging.*;
import common.util.command.*;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.util.*;
import java.io.*;

public class Section {
	int status = 0;		// MSB [built,covered] LSB
	String title = "";
	String handler = "";
	Map<String,String> options = new HashMap<String,String>();
	Map<String,String> ioOptions = new HashMap<String,String>();
	
	public Section (String title) {
		this.title = title;
	}
	
	public Section (Element e) {
		status = Integer.parseInt(e.getChildText("status"));
		title = e.getChildText("title");
		handler = e.getChildText("handler");
		List optionsList = e.getChild("options").getChildren("option");
		for (int i = 0; i < optionsList.size(); i++) {
			Element node = (Element) optionsList.get(i);
			setOption(node.getChildText("name"),node.getChildText("value"));
		}
		
		List iooptionsList = e.getChild("iooptions").getChildren("iooption");
		for (int i = 0; i < iooptionsList.size(); i++) {
			Element node = (Element) iooptionsList.get(i);
			setIoOption(node.getChildText("name"),node.getChildText("value"));
		}		
	}
	
	public Element getElement() {
		Element rootNode = new Element("section");
		
		Element titleNode = new Element("title");
		titleNode.addContent(title);
		Element statusNode = new Element("status");
		statusNode.addContent(String.valueOf(status));
		Element handlerNode = new Element("handler");
		handlerNode.addContent(handler);
		
		Element optionsNode = new Element("options");
		Set<String> optionsSet = options.keySet();
		Iterator<String> osIterator = optionsSet.iterator();
		while(osIterator.hasNext()) {
			String name = (String)osIterator.next();
			Element nameNode = new Element("name");
			nameNode.addContent(name);
			Element valueNode = new Element("value");
			valueNode.addContent(options.get(name));
			optionsNode.addContent(nameNode);
			optionsNode.addContent(valueNode);
		}
		
		Element iooptionsNode = new Element("iooptions");
		Set<String> ioOptionsSet = ioOptions.keySet();
		Iterator<String> iosIterator = ioOptionsSet.iterator();
		while(iosIterator.hasNext()) {
			String name = (String)iosIterator.next();
			Element nameNode = new Element("name");
			nameNode.addContent(name);
			Element valueNode = new Element("value");
			nameNode.addContent(ioOptions.get(name));			
			iooptionsNode.addContent(nameNode);
			iooptionsNode.addContent(valueNode);
		}
		
		rootNode.addContent(titleNode);
		rootNode.addContent(statusNode);
		rootNode.addContent(handlerNode);
		rootNode.addContent(optionsNode);
		rootNode.addContent(iooptionsNode);		
		
		return rootNode;
	}
	
	public boolean isCovered() {
		return status % 2 == 1;
	}
	
	public boolean isBuilt() {
		return (status >> 1) % 2 == 1;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getHandler() {
		return handler;
	}
	
	public int getOptionSize() {
		return options.size();
	}
	
	public String getOptionName(int index) {
		if (index >= options.size() || index < 0)
			return null;
		Set<String> optionsSet = options.keySet();
		Iterator<String> osIterator = optionsSet.iterator();
		while (index-- > 0) osIterator.next();
		
		return (String)osIterator.next();
	}
	
	public String getOptionValue(int index) {
		String name = getOptionName(index);
		if (name == null) return null;
		return options.get(name);
	}
	
	public int getIoOptionSize() {
		return ioOptions.size();
	}
	
	public String getIoOptionName(int index) {
		if (index >= options.size() || index < 0)
			return null;
		Set<String> ioOptionsSet = ioOptions.keySet();
		Iterator<String> iosIterator = ioOptionsSet.iterator();
		while (index-- > 0) iosIterator.next();
		
		return (String)iosIterator.next();	
	}
	
	public String getIoOptionValue(int index) {
		String name = getIoOptionName(index);
		if (name == null) return null;
		return ioOptions.get(name);	
	}
	
	public boolean setHandler (String string) {
		File file = new File(string);
		if (!file.exists() || !file.canExecute())
			return false;
		handler = string;
		return true;
	}
	
	public void setOption(String option, String value) {
		value = (value == null ? "" : value);
		options.put(option,value);
	}
	
	public void setIoOption (String option, String value) {
		value = (value == null ? "" : value);
		ioOptions.put(option,value);
	}
	
	public void setCovered(boolean value) {
		int temp = 1;
		if (value)
			status = status | temp;
		else
			status = status & (~temp);
	}
	
	private void setBuilt(boolean value) {
		int temp = 1 << 1;
		if (value)
			status = status | temp;
		else
			status = status & (~temp);
	}
	
	public String getCommandString() {
		String commandString  = handler;
		String value;
		int size = getOptionSize();
		for (int i = 0; i < size; i++) {
			commandString += " -" + getOptionName(i);
			value = getOptionValue(i);
			if (value != "")
				commandString += " " + value;
		}
		
		size = getIoOptionSize();
		for (int i = 0; i < size; i++) {
			commandString += " -" + getIoOptionName(i);
			value = getIoOptionValue(i);
			if (value != "")
				commandString += " " + value;
		}

		return commandString;
	}
	
	public boolean exec() {
		if (!isCovered()) return false;
		LoggedCommand loggedCommand = new LoggedCommand("");
		try {
			loggedCommand.exec(getCommandString(),new File(handler).getParent());
			//System.out.println("result is " + loggedCommand.getResult());
		} catch (IOException e) {
			System.out.println("error!");
			e.printStackTrace();
		}
		setBuilt(true);
		return true;
	}
	
	/** Test
	 */
	public static void main(String[] args) throws Exception {
		LogFactory logFactory = new LogFactory();
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File("fvaa.proj");
		Document document = (Document) builder.build(xmlFile);
		Element rootNode = document.getRootElement();
		List list = rootNode.getChildren("section");
		Element node = (Element) list.get(0);
		Section section = new Section(node);
		section.setCovered(true);
		section.exec();
		System.out.println("the cmd is : \n" + section.getCommandString());
	}
}