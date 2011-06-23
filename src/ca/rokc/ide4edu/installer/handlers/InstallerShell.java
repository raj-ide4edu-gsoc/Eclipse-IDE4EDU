package ca.rokc.ide4edu.installer.handlers;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import ca.rokc.ide4edu.installer.Activator;
import ca.rokc.ide4edu.installer.InstallerFeatures;
import org.eclipse.jface.resource.ImageRegistry;

public class InstallerShell {
	
	private Shell shell;
	public InstallerShell(Shell parent) {
		// TODO Manage the lifecycle of the image.
		InstallerFeatures pack_data = new InstallerFeatures();
		ImageRegistry trial_Registry = new ImageRegistry(Activator.getDefault().getWorkbench().getDisplay());
			pack_data.setPackageName("cdt");
			pack_data.setPackageTitle("C/C++");
			pack_data.setPackageImage("mycdt.png");
			pack_data.setPackageName("php");
			pack_data.setPackageTitle("PHP");
			pack_data.setPackageImage("php.png");
			pack_data.setPackageName("java");
			pack_data.setPackageTitle("Java");
			pack_data.setPackageImage("Java_2.png");
			pack_data.setPackageName("haskell");
			pack_data.setPackageTitle("Haskell");
			pack_data.setPackageImage("Haskell_2.png");
			pack_data.setPackageName("python");
			pack_data.setPackageTitle("Python");
			pack_data.setPackageImage("python_2.gif");
			Activator.initializeImageRegistry(trial_Registry, pack_data);
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
		for(int i=0;i<pack_data.package_name.size();i++){	
			Button cdt = new Button(shell, SWT.PUSH);
			cdt.setLayoutData(gridData);
			cdt.setImage(trial_Registry.get(pack_data.package_name.get(i)));
			cdt.setText("Install "+pack_data.title_name.get(i));
			
	}
		shell.pack();
		/*
		 * The registry created in this shell is disposed after packing. Is this method correct?
		 */
		trial_Registry.dispose();
	}

	public void open() {
		shell.open();
			}
}