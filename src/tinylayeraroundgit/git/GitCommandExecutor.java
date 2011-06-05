package tinylayeraroundgit.git;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

import tinylayeraroundgit.utils.ConsoleHelper;
import tinylayeraroundgit.utils.ProcessThread;


public class GitCommandExecutor {
	
	private List<IResource> selectedResources;
	
	private GitCommand gitCommand;
	
	public static void main( String[] args ) throws Exception {
//		GitCommandExecutor gitCommandExecutor = new GitCommandExecutor( new GitCommand( "gui" ) );
//		
//		gitCommandExecutor.execute();
	}

	public GitCommandExecutor( GitCommand gitCommand, List<IResource> selectedResources ) {
		super();
		
		this.setGitCommand( gitCommand );
		this.setSelectedResources( selectedResources );
	}
	
	public List<GitCommandResult> execute() throws InterruptedException {
		ArrayList<GitCommandResult> result = new ArrayList<GitCommandResult>();
		
		for( IResource file : getSelectedResources() ) { 
			GitCommandResult gitCommandResult = executeOn( file );
			result.add( gitCommandResult );
		}
		
		return result;
	}

	private GitCommandResult executeOn( IResource file ) throws InterruptedException {
		String command = getGitCommand().getCommand();
		
		if( ! command.toLowerCase().trim().startsWith( "git" ) ) {
			command = "git " + command;
		}
		
		ConsoleHelper consoleHelper = ConsoleHelper.getInstance();
		consoleHelper.writeToConsole( command, SWT.COLOR_BLACK );
		
		ProcessThread processThread = new ProcessThread( command, file.getLocation().toFile() );
		
		processThread.start();
		
		processThread.waitToEnd();
		
		consoleHelper.writeToConsole( processThread.getStdout(), SWT.COLOR_GREEN );
		consoleHelper.writeToConsole( processThread.getStderr(), SWT.COLOR_RED );
		
		return new GitCommandResult( processThread.getStdout(), processThread.getStderr(), processThread.getExitCode() );
	}

	private void setGitCommand( GitCommand gitCommand ) {
		assert gitCommand != null;
		
		this.gitCommand = gitCommand;
	}

	public GitCommand getGitCommand() {
		return gitCommand;
	}

	public void setSelectedResources( List<IResource> selectedResources ) {
		this.selectedResources = selectedResources;
	}

	public List<IResource> getSelectedResources() {
		return selectedResources;
	}
			
}
