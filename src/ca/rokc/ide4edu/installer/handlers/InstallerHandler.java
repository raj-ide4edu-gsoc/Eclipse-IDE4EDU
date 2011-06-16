package ca.rokc.ide4edu.installer.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.jface.dialogs.MessageDialog;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
@SuppressWarnings("unused")
public class InstallerHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public InstallerHandler() {
	}

	/**
	 * the command has been executed, so extract  the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		new InstallerShell(HandlerUtil.getActiveShellChecked(event)).open();
		return null;
	}
}
