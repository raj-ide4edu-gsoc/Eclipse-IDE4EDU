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

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * The activator class controls the plug-in life cycle
 */
// @SuppressWarnings("unused")
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "ca.rokc.ide4edu.installer"; //$NON-NLS-1$
	private static BundleContext context;
	// The shared instance
	private static Activator plugin;
	/**
	 * The constructor
	 */
	public Activator() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		Activator.context = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		Activator.context = context;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	public static void populateData(final List<InstallerFeature> features) {
		try {
			
			final URL rootUrl = new URL("http://www.eclipse.org/ide4edu/install/");
			URL catalogUrl = new URL(rootUrl, "catalog.xml");

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {
				private InstallerFeature tempFeature;
				private StringBuilder description;
				
				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {
					if (qName.equalsIgnoreCase("FEATURE")) {
						tempFeature = new InstallerFeature();
						tempFeature.setPackageName(attributes.getValue("id"));
						tempFeature.setTitleName(attributes.getValue("name"));
						tempFeature.setVersonNumber(attributes.getValue("version"));
						try {
							tempFeature.setImagePath(new URL(rootUrl, attributes.getValue("impath")));
						} catch (MalformedURLException e) {
							// TODO Log this properly. 
							e.printStackTrace();
						}
					} else if (qName.equalsIgnoreCase("DESCRIPTION")) {
						description = new StringBuilder();
						
					}

				}

				public void endElement(String uri, String localName,
						String qName) throws SAXException {
					if (qName.equalsIgnoreCase("FEATURE")) {
						
						features.add(tempFeature);
						
						tempFeature = null;
					}
					if (qName.equalsIgnoreCase("DESCRIPTION")) {
						tempFeature.setDescriptionContent(description.toString());
					}
				}

				public void characters(char ch[], int start, int length)
						throws SAXException {
					if (description == null)
						return;
					description.append(String.valueOf(ch, start, length));
				}

			};
			URLConnection connection = catalogUrl.openConnection();
			InputStream filetest = connection.getInputStream();
			saxParser.parse(filetest,handler);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static BundleContext getContext() {
		return context;

	}

}
