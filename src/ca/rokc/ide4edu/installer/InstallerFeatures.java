/**
 * 
 */
package ca.rokc.ide4edu.installer;
import java.io.*;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Rajendra
 *
 */
@SuppressWarnings("unused")
public class InstallerFeatures {

		
		public String package_name;
		public String title_name ; 
		public String verson_number;
		public String image_path;
		public String description_content;
	/**
	 *  Constructor for the class
	 */
	public InstallerFeatures() {
		// TODO Auto-generated constructor stub
	}
	
	public void SetName(String pname ){
		this.package_name = pname;
	}
	public void SetTitle(String ptitle){
		this.title_name = ptitle;
	}
	public void SetVersion (String version){
		this.verson_number = version;
	}
	public void SetImage (String impath){
		this.image_path = impath;
	}
	public void SetDescription(String desc){
		this.description_content = desc;
	}


}