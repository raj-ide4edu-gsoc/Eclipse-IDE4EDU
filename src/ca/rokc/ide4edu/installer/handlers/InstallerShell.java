package ca.rokc.ide4edu.installer.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
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
		final ImageRegistry trial_Registry = new ImageRegistry(Activator.getDefault()
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
		Image image;
		for (final InstallerFeature feature : addons_list) {
			Button selectionButton = new Button(shell, SWT.PUSH);
			selectionButton.setLayoutData(gridData);
			image = trial_Registry.get(feature.getTitleName());
			if(image == null)
			{
				System.out.println("The images are not being handled properly");
			}
			selectionButton.setImage(trial_Registry.get(feature.getPackageName()));
			selectionButton.setText("Install " + feature.getTitleName());
			selectionButton.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {

					IHandlerService handlerService = (IHandlerService) window.getService(IHandlerService.class);
					try {
						handlerService.executeCommand("ca.rokc.ide4edu.installer.windowLauncher", null);


					} catch (Exception ex) {
						ex.printStackTrace();
						throw new RuntimeException("ca.rokc.ide4edu.installer.windowLauncher not found");
					}

				}
			});
			selectionButton.addDisposeListener(new DisposeListener() {

				@Override
				public void widgetDisposed(DisposeEvent e) {
					// TODO Auto-generated method stub
					trial_Registry.get(feature.getTitleName()).dispose();
					
				}
				
			});
			selectionButton.pack();

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