package tinylayeraroundgit.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.internal.resources.Folder;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import temp.TempDebug;
import tinylayeraroundgit.git.Command;
import tinylayeraroundgit.git.GitCommandExecutor;


public abstract class AbstractActionDelegate implements IActionDelegate {

	private StructuredSelection structuredSelection;
	private Shell shell;
	
	public AbstractActionDelegate() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		setShell( targetPart.getSite().getShell() );
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public abstract void run(IAction action);

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		setStructuredSelection( (StructuredSelection) selection );
	}
	
	// Utility methods for actual implemetations:
	
	protected void openInformationDialog( String title, String message ) {
		MessageDialog.openInformation( shell, title, message );
	}
	
	protected List<IProject> getSelectedProjects() {
		ArrayList<IProject> result = new ArrayList<IProject>();
		
		if( getStructuredSelection() == null ) {
			return result;
		}
		
		List<?> selectedElements = structuredSelection.toList();
		for( Object object : selectedElements ) {
			if( object instanceof IResource ) {
				IResource resource = (IResource) object;
				IProject project = resource.getProject();
				result.add( project );
			}
		}
		
		return result;
	}
	
	protected List<IResource> getSelectedResources() {
		ArrayList<IResource> result = new ArrayList<IResource>();
		
		if( getStructuredSelection() == null ) {
			return result;
		}
		
		List<?> selectedElements = structuredSelection.toList();
		for( Object object : selectedElements ) {
			if( object instanceof IResource ) {
				result.add( (IResource) object );
			}
		}
		
		return result;
	}
	
	// Getters and setters:
	
	public StructuredSelection getStructuredSelection() {
		return structuredSelection;
	}
	
	private void setStructuredSelection( StructuredSelection structuredSelection ) {
		this.structuredSelection = structuredSelection;
	}

	private void setShell( Shell shell ) {
		this.shell = shell;
	}
	
	protected Shell getShell() {
		return shell;
	}

}
