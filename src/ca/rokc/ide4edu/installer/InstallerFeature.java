/**
 * 
 */
package ca.rokc.ide4edu.installer;

import java.net.URL;

/**
 * @author Rajendra
 * 
 */
// @SuppressWarnings("unused")
public class InstallerFeature {

	private String packageName;
	private String titleName;
	private String versonNumber;
	private URL imagePath;
	private String descriptionContent;
	
	public InstallerFeature (){
		
	};

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setVersonNumber(String versonNumber) {
		this.versonNumber = versonNumber;
	}

	public String getVersonNumber() {
		return versonNumber;
	}

	public void setDescriptionContent(String descriptionContent) {
		this.descriptionContent = descriptionContent;
	}

	public String getDescriptionContent() {
		return descriptionContent;
	}

	public void setImagePath(URL url) {
		this.imagePath = url;
	}

	public URL getImagePath() {
		return imagePath;
	}

}