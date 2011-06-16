package ca.rokc.ide4edu.installer.handlers;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import ca.rokc.ide4edu.installer.Activator;
import org.eclipse.jface.resource.ImageRegistry;

public class InstallerShell {
	
	private Shell shell;
	public InstallerShell(Shell parent) {
		// TODO Manage the lifecycle of the image.
		//Image cdtImage = Activator.getImageDescriptor("icons/mycdt.png").createImage();
		ImageRegistry imageRegistry = Activator.getDefault().getImageRegistry();
		//Image cdtImage = imageRegistry.get(Activator.CDT_IMAGE);
		
		shell = new Shell(parent);
		shell.setLayout(new GridLayout(3, true));
		//shell.setLayout(null);
		shell.setText("Install New Language");
		shell.setBounds(100,100,600, 400);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.widthHint = 200;
		gridData.heightHint = 100;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		for(int i=0;i<6;i++){	
			Button cdt = new Button(shell, SWT.PUSH);
			cdt.setLayoutData(gridData);
			cdt.setImage(Activator.getInstallImage(imageRegistry, i));
			cdt.setText(Activator.getInstallName(i));
			
	}
		/*// TODO Need a PHP image
		Button php = new Button(shell, SWT.PUSH);
		php.setImage(cdtImage);
		php.setBounds(70,100, 100, 100);
		php.setText("Install PHP");		

		// TODO Need a Scheme (or whatever) image
		Button scheme = new Button(shell, SWT.PUSH);
		scheme.setImage(cdtImage);
		scheme.setBounds(130,100,100,100);
		scheme.setText("Install Scheme");
		*/
		//cdtImage.dispose();
		shell.pack();
		
	}

	public void open() {
		shell.open();
			}
}