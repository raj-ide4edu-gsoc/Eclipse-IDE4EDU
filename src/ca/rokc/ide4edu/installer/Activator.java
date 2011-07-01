package ca.rokc.ide4edu.installer;

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
//@SuppressWarnings("unused")
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "ca.rokc.ide4edu.installer"; //$NON-NLS-1$
	public static BundleContext context;
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
			InstallerFeature pack_Data) {
		/*
		 * The initialize ImageRegistry code is handled here. If the data and
		 * the images are being obtained from an URL this code is unreachable
		 * and need to be disposed
		 */
		Bundle bundle = Platform.getBundle(PLUGIN_ID);

		for (int i = 0; i < 1/* Write the list iterator here */; i++) {
			ImageDescriptor myImage = ImageDescriptor.createFromURL(FileLocator
					.find(bundle, new Path("icons/" + pack_Data.imagePath),
							null));
			registry.put(pack_Data.packageName, myImage);
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
						tempFeature.setName(attributes.getValue("id"));
						tempFeature.setTitle(attributes.getValue("name"));
						tempFeature.setVersion(attributes.getValue("version"));
						tempFeature.setImage(attributes.getValue("impath"));
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
						tempFeature.setDescription(description.toString());
					}
				}

				public void characters(char ch[], int start, int length)
						throws SAXException {
					if (description == null)
						return;
					description.append(String.valueOf(ch, start, length));
				}

			};

			saxParser.parse("c:\\file.xml", handler);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static BundleContext getContext(){
		return context;
		
	}

}
