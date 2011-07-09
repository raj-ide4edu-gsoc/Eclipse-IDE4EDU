package ca.rokc.ide4edu.installer;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.core.IProvisioningAgentProvider;
import org.eclipse.equinox.p2.core.ProvisionException;
import org.eclipse.equinox.p2.metadata.IInstallableUnit;
import org.eclipse.equinox.p2.operations.InstallOperation;
import org.eclipse.equinox.p2.operations.ProvisioningSession;
import org.eclipse.equinox.p2.query.QueryUtil;
import org.eclipse.equinox.p2.repository.artifact.IArtifactRepositoryManager;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepository;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepositoryManager;
import org.osgi.framework.ServiceReference;

import ca.rokc.ide4edu.installer.Activator;

public class windowLauncherHandler implements IHandler {

	private IProvisioningAgent agent;
	IProgressMonitor monitor;

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// TODO Auto-generated method stub
		Display display;
		display = PlatformUI.getWorkbench().getDisplay();
		Shell shell = new Shell(display);
		shell.setText("Click counter");
		shell.setBounds(100, 100, 200, 100);
		shell.setLayout(null);
		final Label label = new Label(shell, SWT.PUSH);
		label.setBounds(120, 20, 30, 30);

		Job job = null;

		// get the agent
		@SuppressWarnings("rawtypes")
		ServiceReference sr = Activator.getContext().getServiceReference(
				IProvisioningAgentProvider.SERVICE_NAME);
		IProvisioningAgentProvider agentProvider = null;
		if (sr == null)

			return agentProvider;

		agentProvider = (IProvisioningAgentProvider) Activator.getContext()
				.getService(sr);
		try {
			agent = agentProvider.createAgent(null);
		} catch (ProvisionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (agent == null) {
			System.out.println("Agent has never been created");
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
		} catch (ProvisionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OperationCanceledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		shell.setText("The execution of the installation has beguin");

		Collection<IInstallableUnit> toInstall = metadataRepo.query(
				QueryUtil.createIUQuery("org.eclipse.cdt.feature.group"),
				new NullProgressMonitor()).toUnmodifiableSet();
		shell.setText("The Size of the installation units is "
				+ toInstall.size());
		InstallOperation installOperation = new InstallOperation(
				new ProvisioningSession(agent), toInstall);
		shell.setText("It ran till here");
		for (IInstallableUnit test : toInstall) {
			System.out.println(test.toString());
		}
		if (installOperation.resolveModal(new NullProgressMonitor()).isOK()) {
			shell.setText("Job is being added");
			job = installOperation
					.getProvisioningJob(new NullProgressMonitor());
			job.addJobChangeListener(new JobChangeAdapter() {
				public void done(IJobChangeEvent event) {
					 agent.stop();
				}
			});

		}
		if (job != null) {
			shell.setText("Job is being scheduled");
			job.schedule();
		}

		shell.setText("Job is not being scheduled");
		shell.open();

		return null;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isHandled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}

}
