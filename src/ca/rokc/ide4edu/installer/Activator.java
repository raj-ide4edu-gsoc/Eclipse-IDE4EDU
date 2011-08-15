package ca.rokc.ide4edu.installer;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
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
	private static Shell welcome;

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
			welcome = new Shell();
			welcome.open();
			welcome.setBounds(100, 100, 400, 300);
			URL url = null;
			/*
			Properties properties = System.getProperties();
			properties.put("http.proxyHost", "172.16.25.25");
			properties.put("http.proxyPort", "8080");
			*/
			URLConnection connection = catalogUrl.openConnection();
			InputStream filetest = connection.getInputStream();
			saxParser.parse(filetest,handler);
			welcome.close();
			/*URL data = FileLocator.find(Platform.getBundle(PLUGIN_ID),new Path ("/icons/packConfig.xml"), null);
			InputStream file = data.openStream();
			saxParser.parse(file, handler);
		*/
		} catch (Exception e) {
			welcome.close();
			Shell error = new Shell();
			error.setBounds(200, 200, 600, 400);
			Label label = new Label(error, SWT.PUSH);
			label.setBounds(220, 120, 400, 300);
			label.setText(e.getMessage());
			e.printStackTrace();
			error.open();
		}

	}

	public static BundleContext getContext() {
		return context;

	}

}
