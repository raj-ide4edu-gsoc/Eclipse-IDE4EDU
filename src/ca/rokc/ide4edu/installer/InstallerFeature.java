/*******************************************************************************
 * Copyright (c) 2011 Rajendra Kolli
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Rajendra Kolli - initial API and implementation
 *******************************************************************************/

package ca.rokc.ide4edu.installer;

import java.net.URL;


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