package ca.rokc.ide4edu.installer.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;

import ca.rokc.ide4edu.installer.Activator;
import ca.rokc.ide4edu.installer.InstallerFeature;

public class InstallerShell {

	private Shell shell;

	public InstallerShell(Shell parent) {
		ImageRegistry trial_Registry = new ImageRegistry(Activator.getDefault()
				.getWorkbench().getDisplay());
		List<InstallerFeature> addons_list = new ArrayList<InstallerFeature>();

		Activator.initializeImageRegistry(trial_Registry, addons_list);
		Activator.populateData(addons_list);

		shell = new Shell(parent);
		shell.setLayout(new GridLayout(1, true));
		// shell.setLayout(null);
		shell.setText("Install New Language");
		shell.setBounds(100, 100, 600, 400);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.widthHint = 200;
		gridData.heightHint = 100;
		gridData.grabExcessHorizontalSpace = false;
		gridData.grabExcessVerticalSpace = false;
		for (InstallerFeature feature : addons_list) {
			Button cdt = new Button(shell, SWT.PUSH);
			cdt.setLayoutData(gridData);
			cdt.setImage(trial_Registry.get(feature.packageName));
			cdt.setText("Install " + feature.titleName);
			cdt.pack();

		}
		shell.pack();
		/*
		 * The registry created in this shell is disposed after packing. Is this
		 * method correct?
		 */
		trial_Registry.dispose();
	}

	public void open() {
		shell.open();
	}
}