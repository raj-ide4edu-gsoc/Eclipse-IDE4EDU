/**
 * 
 */
package ca.rokc.ide4edu.installer;

/**
 * @author Rajendra
 * 
 */
// @SuppressWarnings("unused")
public class InstallerFeature {

	public String packageName;
	public String titleName;
	public String versonNumber;
	public String imagePath;
	public String descriptionContent;

	public void setName(String pname) {
		this.packageName = pname;
	}

	public void setTitle(String ptitle) {
		this.titleName = ptitle;
	}

	public void setVersion(String version) {
		this.versonNumber = version;
	}

	public void setImage(String impath) {
		this.imagePath = impath;
	}

	public void setDescription(String desc) {
		this.descriptionContent = desc;
	}

}