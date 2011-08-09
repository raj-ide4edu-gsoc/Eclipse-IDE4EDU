package ca.rokc.ide4edu.installer;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

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

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static void initializeImageRegistry(ImageRegistry registry,
			List<InstallerFeature> pack_Data) {
		/*
		 * The initialize ImageRegistry code is handled here. If the data and
		 * the images are being obtained from an URL this code is unreachable
		 * and need to be disposed
		 */
		Bundle bundle = Platform.getBundle(PLUGIN_ID);

		for (InstallerFeature feature : pack_Data) {
			ImageDescriptor myImage = ImageDescriptor
					.createFromURL(FileLocator.find(bundle, new Path("icons/"
							+ feature.getImagePath()), null));
			registry.put(feature.getPackageName(), myImage);
		}
	}

	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	public static void populateData(final List<InstallerFeature> features) {
		// TODO Manage the XML handling and populate the list

		try {

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
						tempFeature.setImagePath(attributes.getValue("impath"));
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
			URL url = null;
			url = new URL("http://www.eclipse.org/ide4edu/install/catalog.xml");
			URLConnection connection = url.openConnection();
			InputStream filetest = connection.getInputStream();
			saxParser.parse(filetest,handler);
			/*URL data = FileLocator.find(Platform.getBundle(PLUGIN_ID),new Path ("/icons/packConfig.xml"), null);
			InputStream file = data.openStream();
			saxParser.parse(file, handler);
		*/
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static BundleContext getContext() {
		return context;

	}

}
