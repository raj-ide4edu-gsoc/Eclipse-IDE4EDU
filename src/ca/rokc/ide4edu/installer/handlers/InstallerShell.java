package ca.rokc.ide4edu.installer.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.IWorkbenchWindow;
import ca.rokc.ide4edu.installer.Activator;
import ca.rokc.ide4edu.installer.InstallerFeature;

public class InstallerShell {

	private Shell shell;

	public InstallerShell(Shell parent) {
		ImageRegistry trial_Registry = new ImageRegistry(Activator.getDefault()
				.getWorkbench().getDisplay());
		List<InstallerFeature> addons_list = new ArrayList<InstallerFeature>();
	
			Activator.populateData(addons_list);
		Activator.initializeImageRegistry(trial_Registry, addons_list);
		shell = new Shell(parent);
		shell.setLayout(new GridLayout(1, true));
		final IWorkbenchWindow window = Activator.getDefault().getWorkbench().getActiveWorkbenchWindow();
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
			cdt.setImage(trial_Registry.get(feature.getPackageName()));
			cdt.setText("Install " + feature.getTitleName());
			cdt.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
				
					IHandlerService handlerService = (IHandlerService) window.getService(IHandlerService.class);
					try {
						handlerService.executeCommand("ca.rokc.ide4edu.installer.windowLauncher", null);
					} catch (Exception ex) {
						throw new RuntimeException("ca.rokc.ide4edu.installer.windowLauncher not found");
					}

				}
			});
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