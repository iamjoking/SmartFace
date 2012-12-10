/** This class describes a program.
 * @author iamjoking
 */

package common.util;

import org.jdom2.*;
import org.jdom2.input.*;
import org.jdom2.output.*;

import java.util.*;
import java.io.*;

public class Program {
	String name;
	String path;
	String description;
	
	Map<String,String> nonvalueOptions = new HashMap<String,String>();
	Map<String,String> valueOptions = new HashMap<String,String>();
	Map<String,String> optionalValueOptions = new HashMap<String,String>();
	
	public Program(String name, String path, String description) {
		this.name = name;
		this.path = path;
		this.description = description;
	}
	
	public Program(Element e) {
		if (e == null)
			return ;
		
		name = e.getChildText("name");
		path = e.getChildText("path");
		description = e.getChildText("description");
		
		List<Element> list = e.getChildren("nonvalueOption");
		for (int i = 0; i < list.size(); i++)
			addNonvalueOption(list.get(i).getChildText("name"), list.get(i).getChildText("description"));
			
		list = e.getChildren("valueOption");
		for (int i = 0; i < list.size(); i++)
			addValueOption(list.get(i).getChildText("name"), list.get(i).getChildText("description"));
		
		list = e.getChildren("optionalValueOption");
		for (int i = 0; i < list.size(); i++)
			addOptionalValueOption(list.get(i).getChildText("name"), list.get(i).getChildText("description"));
	}
	
	public String getName() {
		return name;
	}
	
	public String getPath() {
		return path;
	}

	public String getDescription() {
		return description;
	}
	
	public void addNonvalueOption(String optionName, String optionDescription) {
		nonvalueOptions.put(optionName,optionDescription);
	}

	public void addValueOption(String optionName, String optionDescription) {
		valueOptions.put(optionName,optionDescription);
	}
	
	public void addOptionalValueOption(String optionName, String optionDescription) {
		optionalValueOptions.put(optionName,optionDescription);
	}

	public int getNonvalueOptionsSize() {
		return nonvalueOptions.size();
	}
	
	public int getValueOptionsSize() {
		return valueOptions.size();
	}
	
	public int getOptionalValueOptionsSize() {
		return optionalValueOptions.size();
	}
	
	public String getNonvalueOptionName(int index) {
		if (index < 0 || index >= getNonvalueOptionsSize())
			return null;
		
		Set<String> set = nonvalueOptions.keySet();
		Iterator<String> iterator = set.iterator();
		while (index-- > 0) iterator.next();
		
		return (String)iterator.next();	
	}
	
	public String getValueOptionName(int index) {
		if (index < 0 || index >= getValueOptionsSize())
			return null;
		
		Set<String> set = valueOptions.keySet();
		Iterator<String> iterator = set.iterator();
		while (index-- > 0) iterator.next();
		
		return (String)iterator.next();	
	}

	public String getOptionalValueOptionName(int index) {
		if (index < 0 || index >= getOptionalValueOptionsSize())
			return null;
		
		Set<String> set = optionalValueOptions.keySet();
		Iterator<String> iterator = set.iterator();
		while (index-- > 0) iterator.next();
		
		return (String)iterator.next();	
	}

	public String getDescription(String optionName) {
		String ret = nonvalueOptions.get(optionName);
		if (ret != null)
			return ret;
		
		ret = valueOptions.get(optionName);
		if (ret != null)
			return ret;
			
		ret = optionalValueOptions.get(optionName);
		if (ret != null)
			return ret;
		else
			return null;
	}
	
}