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
	@Override
    protected void initializeImageRegistry(ImageRegistry registry) {
        super.initializeImageRegistry(registry);
        Bundle bundle = Platform.getBundle(PLUGIN_ID);
        ImageDescriptor myImage = ImageDescriptor.createFromURL(
              FileLocator.find(bundle,
                               new Path("icons/mycdt.png"),
                                        null));
        registry.put(CDT_IMAGE, myImage);
        ImageDescriptor myImage1 = ImageDescriptor.createFromURL(
                FileLocator.find(bundle,
                                 new Path("icons/Java_2.png"),
                                          null));
          registry.put(JAVA_IMAGE, myImage1);
          ImageDescriptor myImage2 = ImageDescriptor.createFromURL(
                  FileLocator.find(bundle,
                                   new Path("icons/php.png"),
                                            null));
            registry.put(PHP_IMAGE, myImage2);
            ImageDescriptor myImage3 = ImageDescriptor.createFromURL(
                    FileLocator.find(bundle,
                                     new Path("icons/Haskell_2.png"),
                                              null));
              registry.put(HASKELL_IMAGE, myImage3);
              ImageDescriptor myImage4 = ImageDescriptor.createFromURL(
                      FileLocator.find(bundle,
                                       new Path("icons/python_2.gif"),
                                                null));
                registry.put(PYTHON_IMAGE, myImage4);
                ImageDescriptor myImage5 = ImageDescriptor.createFromURL(
                        FileLocator.find(bundle,
                                         new Path("icons/sample.gif"),
                                                  null));
                  registry.put(DEFAULT_IMAGE, myImage5);
                  
    }

	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	public static String getInstallName(int flag){
			switch (flag) {
			  case 0: 
			    return("Install C/C++") ;
			  case 1: 
				  return("Install PHP") ;
			  case 2: 
				  return("Install Java") ;
			  case 3: 
				  return("Install Python") ;
			  case 4: 
				  return("Install Haskell") ;
			  default: 
				  return("UNKNOWN") ;
			}
			
		}
	public static Image getInstallImage(ImageRegistry imageRegistry, int flag){
		switch (flag) {
		  case 0: 
		    return(imageRegistry.get(CDT_IMAGE)) ;
		  case 1: 
			  return(imageRegistry.get(PHP_IMAGE)) ;
		  case 2: 
			  return(imageRegistry.get(JAVA_IMAGE)) ;
		  case 3: 
			  return(imageRegistry.get(PYTHON_IMAGE)) ;
		  case 4: 
			  return(imageRegistry.get(HASKELL_IMAGE)) ;
		  default: 
			  return(imageRegistry.get(DEFAULT_IMAGE)) ;
		}
		
	}
	}

