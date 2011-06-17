/**
 * 
 */
package ca.rokc.ide4edu.installer;
import java.io.*;
import java.util.Vector;

/**
 * @author Rajendra
 *
 */
@SuppressWarnings("unused")
public class InstallerFeatures {

		
		public Vector<String>package_name = new Vector<String>();
		public Vector<String>title_name = new Vector<String>(); 
		public Vector<String>verson_number = new Vector<String>();
		public Vector<String>image_path = new Vector<String>();
		public Vector<String>description_content = new Vector<String>();
	/**
	 *  Constructor for the class
	 */
	public InstallerFeatures() {
		// TODO Auto-generated constructor stub
	}
	
	public void setPackageName (String pname){
		this.package_name.add(pname);
	}
	public void setPackageTitle (String tname){
		this.title_name.add(tname);
	}
	public void setPackageVersion (String vnum){
		this.verson_number.add(vnum);
	}
	public void setPackageImage (String impath){
		this.image_path.add(impath);
	}
	public void setPackageDescription (String content){
		this.description_content.add(content);
	}

}
