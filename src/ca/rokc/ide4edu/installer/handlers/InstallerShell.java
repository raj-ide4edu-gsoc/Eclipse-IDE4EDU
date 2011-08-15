package ca.rokc.ide4edu.installer.handlers;

import java.awt.Label;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.core.IProvisioningAgentProvider;
import org.eclipse.equinox.p2.core.ProvisionException;
import org.eclipse.equinox.p2.metadata.IInstallableUnit;
import org.eclipse.equinox.p2.query.QueryUtil;
import org.eclipse.equinox.p2.repository.artifact.IArtifactRepositoryManager;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepository;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepositoryManager;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.ServiceReference;

import ca.rokc.ide4edu.installer.Activator;
import ca.rokc.ide4edu.installer.InstallerFeature;

public class InstallerShell {

	private Shell shell;
	private IProvisioningAgent agent;
	IProgressMonitor monitor;

	final ImageRegistry trial_Registry = new ImageRegistry(Activator.getDefault()
			.getWorkbench().getDisplay());

	public InstallerShell(Shell parent) {
		
		List<InstallerFeature> addons_list = new ArrayList<InstallerFeature>();

			Activator.populateData(addons_list);
		shell = new Shell(parent);
		shell.setLayout(new GridLayout(3, true));		
		shell.setText("Install New Language");
		shell.setBounds(100, 100, 600, 400);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.widthHint = 200;
		gridData.heightHint = 100;
		gridData.grabExcessHorizontalSpace = false;
		gridData.grabExcessVerticalSpace = false;
		for (final InstallerFeature feature : addons_list) {
			Button selectionButton = new Button(shell, SWT.PUSH);
			selectionButton.setLayoutData(gridData);
			
			final Image image = getImageForFeature(shell.getDisplay(), feature);
			selectionButton.setImage(image);
			selectionButton.addDisposeListener(new DisposeListener() {				
				@Override
				public void widgetDisposed(DisposeEvent e) {
					image.dispose();
				}
			});
			
			selectionButton.setText("Install " + feature.getTitleName());
			selectionButton.addSelectionListener(new SelectionAdapter() {
				
				

				@Override
				public void widgetSelected(SelectionEvent e) {
					

					try {
						/*
						 * This test Shell is intended to display the description of the packages
						 * and prompt the user to install or close the window.
						 * If handled properly, this can be the way a package can be neatly described to the user
						 * 
						 * */
						Display display;
						display = PlatformUI.getWorkbench().getDisplay();
						final Shell test = new Shell(display);
						
						test.setText("About the package - "+feature.getTitleName());
						test.setBounds(100, 100, 600, 400);
						test.setLayout(null);
						/*
						 * Labels are defined to show the image and the description
						 * */
						Label label = new Label();
						label.setBounds(200, 200, 380, 180);
						label.setText(feature.getDescriptionContent());
						final Button installButton = new Button(test, SWT.PUSH);
						installButton.setBounds(380, 330, 100, 60);
						installButton.setText("Install");
						final Button closeButton = new Button(test, SWT.PUSH);
						closeButton.setBounds(490, 330, 100, 60);
						closeButton.setText("Close");
						shell.setVisible(false);
						test.open();
						/*
						 * Implementation of methods for the buttons and the installation procedure.
						 * Close Button calls the shell and widget disposal functions.
						 * Install button calls the installation procedure to be executed
						 * */
						closeButton.addSelectionListener(new SelectionAdapter(){
							public void widgetSelected(SelectionEvent e){
								test.dispose();
							}
						});
						/*
						 * Installation Job is being handled here. It is supposed to work but as of now
						 * It only queries data from a given metadata repository.
						 * The issue is being that the installation job is being created but never triggered
						 * */
						installButton.addSelectionListener(new SelectionAdapter(){
							public void widgetSelected(SelectionEvent e){
								// get the agent
								ServiceReference sr = Activator.getContext().getServiceReference(
										IProvisioningAgentProvider.SERVICE_NAME);
								IProvisioningAgentProvider agentProvider = null;
								
								agentProvider = (IProvisioningAgentProvider) Activator.getContext()
										.getService(sr);
								try {
									agent = agentProvider.createAgent(null);
								} catch (ProvisionException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								// get the repository managers and define our repositories
								IMetadataRepositoryManager manager = (IMetadataRepositoryManager) agent
										.getService(IMetadataRepositoryManager.SERVICE_NAME);
								IArtifactRepositoryManager artifactManager = (IArtifactRepositoryManager) agent
										.getService(IArtifactRepositoryManager.SERVICE_NAME);
								try {
									manager.addRepository(new URI(
											"http://download.eclipse.org/releases/indigo"));
								} catch (URISyntaxException e1) { // TODO Auto-generated catch block
									e1.printStackTrace();
								}
								try {
									artifactManager.addRepository(new URI(
											"http://download.eclipse.org/releases/indigo"));
								} catch (URISyntaxException e1) { // TODO Auto-generated catch block
									e1.printStackTrace();
								}
								// Load and query the metadata
								IMetadataRepository metadataRepo = null;
								try {
									System.out.println("The execution of the installation has beguin");
									metadataRepo = manager.loadRepository(new URI(
											"http://download.eclipse.org/releases/indigo"),
											new NullProgressMonitor());
								} catch (ProvisionException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (OperationCanceledException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (URISyntaxException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								/*
								 * Create Variable Queries based on the package name
								 * To ensure the correctness of the query, the package name used
								 * should be as accurate as the one used in actual terms
								 * */
								Collection<IInstallableUnit> toInstall = metadataRepo.query(
										QueryUtil.createIUQuery("org.eclipse."+feature.getPackageName()+".feature.group"),
										new NullProgressMonitor()).toUnmodifiableSet();
								
								//TODO : The installation procedure must be written from here to complete the plugin

							}
						});
						
						test.addDisposeListener(new DisposeListener(){

							@Override
							public void widgetDisposed(DisposeEvent e) {
								installButton.dispose();
								closeButton.dispose();
								shell.update();
								shell.setVisible(true);
								
							}
							
							
						});

					} catch (Exception ex) {
						ex.printStackTrace();
					}

				}
			});
			selectionButton.pack();

		}
		shell.pack();
		/*
		 * The registry created in this shell is disposed after packing. Is this
		 * method correct?
		 */
		
	}
	private Image getImageForFeature(Device device, InstallerFeature feature) {
		InputStream in = null;
		try {
			in = feature.getImagePath().openStream();
			ImageData[] data = new ImageLoader().load(in);
			return new Image(device, data[0]);
		} catch (IOException e) {
			// TODO return a default image.
			e.printStackTrace();
			return null;
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					// TODO Log this unlikely exception
					e.printStackTrace();
				}
		}
	}
	
	public void open() {
		trial_Registry.dispose();
		shell.open();
	}
}