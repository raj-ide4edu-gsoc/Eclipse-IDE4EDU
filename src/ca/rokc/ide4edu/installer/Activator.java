package ca.rokc.ide4edu.installer;

import java.net.URL;

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

/**
 * The activator class controls the plug-in life cycle
 */
@SuppressWarnings("unused")
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "ca.rokc.ide4edu.installer"; //$NON-NLS-1$
	
	public static final String CDT_IMAGE = "CDT";
	public static final String PHP_IMAGE = "PHP";
	public static final String PYTHON_IMAGE = "PYTHON";
	public static final String HASKELL_IMAGE = "HASKELL";
	public static final String JAVA_IMAGE = "JAVA";
	public static final String DEFAULT_IMAGE = "DEFAULT";
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
		
        Bundle bundle = Platform.getBundle(PLUGIN_ID);
        int index = pack_Data.package_name.size();
	        for(int i=0;i<index;i++)
	        {
		        ImageDescriptor myImage = ImageDescriptor.createFromURL(
		              FileLocator.find(bundle,
		                               new Path("icons/"+pack_Data.image_path.get(i)),
		                                        null));
		        registry.put(pack_Data.package_name.get(i), myImage);
	        }
    }

	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	}

