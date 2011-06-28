package ca.rokc.ide4edu.installer;

import java.net.URL;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.eclipse.swt.graphics.Image;
import ca.rokc.ide4edu.installer.InstallerFeatures;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory; 
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * The activator class controls the plug-in life cycle
 */
@SuppressWarnings("unused")
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "ca.rokc.ide4edu.installer"; //$NON-NLS-1$
	
	// The shared instance
	private static Activator plugin;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
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
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static void initializeImageRegistry(ImageRegistry registry, InstallerFeatures pack_Data) {
		/*
		 * The initialize ImageRegistry code is handled here.
		 * If the data and the images are being obtained from an URL
		 * this code is unreachable and need to be disposed
		 */
        Bundle bundle = Platform.getBundle(PLUGIN_ID);

	        for(int i=0;i<1/*Write the list iterator here*/;i++)
	        {
		        ImageDescriptor myImage = ImageDescriptor.createFromURL(
		              FileLocator.find(bundle,
		                               new Path("icons/"+pack_Data.image_path),
		                                        null));
		        registry.put(pack_Data.package_name, myImage);
	        }
    }

	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	public static void populateData(final List<InstallerFeatures> features){
		//TODO Manage the XML handling and populate the list

		try {
			
		     SAXParserFactory factory = SAXParserFactory.newInstance();
		     SAXParser saxParser = factory.newSAXParser();
		     final InstallerFeatures tempFeature = new InstallerFeatures();
		     String tempVal="";
		     DefaultHandler handler = new DefaultHandler() {
		    	 
		     public void startElement(String uri, String localName,
		        String qName, Attributes attributes)
		        throws SAXException {
		        if (qName.equalsIgnoreCase("FEATURE")) {
		         tempFeature.SetName(attributes.getValue("id"));
		         tempFeature.SetTitle(attributes.getValue("name"));
		         tempFeature.SetVersion(attributes.getValue("version"));
		         tempFeature.SetImage(attributes.getValue("impath"));
		        }
		 
		     }
		 
		     public void endElement(String uri, String localName,
		          String qName)
		          throws SAXException {
		    	 if (qName.equalsIgnoreCase("FEATURE")){
		    		 features.add(tempFeature);
		    	 }
		    	 if (qName.equalsIgnoreCase("DESCRIPTION")){
		    		 tempFeature.SetDescription(tempVal);
		    	 }
		     }
		 
		     public void characters(char ch[], int start, int length)
		         throws SAXException {
		    	tempVal = String.valueOf(ch, start, length);
		        }
		 
		      };
		 
		      saxParser.parse("c:\\file.xml", handler);
		 
			} 
			catch(Exception e) {
		      e.printStackTrace();
		    }
		
	}
}

