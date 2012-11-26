/** The project class
 * @author iamjoking
 */

package common.util;

import org.jdom2.*;
import org.jdom2.input.*;
import org.jdom2.output.*;

import java.util.*;
import java.io.*;

public class Project {
	File projFile;
	Document projDoc = new Document();
	public final static String PROJECT_FILE_EXTENSION = ".proj";
	ArrayList<Section> sections = new ArrayList<Section>();
	
	public Project(File file) {
		projFile = file;
		if (!isProjectFile()) return ;
		SAXBuilder builder = new SAXBuilder();
		try {
			projDoc = (Document) builder.build(projFile);
			Element rootNode = projDoc.getRootElement();
			List list = rootNode.getChildren("section");
			for (int i = 0; i < list.size(); i++)
				sections.add(new Section((Element)list.get(i)));
		} catch (IOException io) {
			io.printStackTrace();
		} catch (JDOMException jdomex) {
			jdomex.printStackTrace();
		}
	}

	public int size() {
		return sections.size();
	}
	
	public File getProjectFile() {
		return projFile;
	}
	
	private void writeDoc() {
		Element rootNode = new Element("project");
		projDoc = new Document(rootNode);
		
		for (int i = 0; i < sections.size(); i++)
			rootNode.addContent(sections.get(i).getElement());
	}
	
	public void save() {
		if (projFile == null || projDoc == null)
			return ;
		writeDoc();
		try {
			XMLOutputter xmlOutputter = new XMLOutputter();
			FileWriter fw = new FileWriter(projFile);
			Format f = Format.getPrettyFormat();
			xmlOutputter.setFormat(f);
			xmlOutputter.output(projDoc,fw);
			fw.close();
		} catch (IOException io) {
			io.printStackTrace();
		}
	}
	
	public boolean isProjectFile() {
		return Project.isProjectFile(projFile);
	}
	
	public Section getSection(int index) {
		if (index < 0 || index >= sections.size())
			return null;
		
		return sections.get(index);
	}
	
	/** Set the index-th section in sections, and erase the sections behind.
	 */
	public boolean setSection(int index, Section section) {
		try {
			sections.set(index, section);
		} catch (IndexOutOfBoundsException e) {
			if (index == sections.size())
				sections.add(section);
			else
				return false;
		}
		
		for (int i = sections.size() - 1; i > index; i--)
			sections.remove(i);
		
		return true;
	}
	
	public static boolean isProjectFile(File file) {
		return file.exists() && file.isFile() && file.getPath().endsWith(PROJECT_FILE_EXTENSION);
	}
	
	/** Test
	 */
	public static void main (String[] args) {

	}

}
