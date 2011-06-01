package tinylayeraroundgit.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import tinylayeraroundgit.git.GitCommand;


public class GitProjectUtils {
	
	private GitProjectUtils() {}
	
	public static void refrech( IProject project ) {
		try {
			project.refreshLocal( IProject.DEPTH_INFINITE, null );
		} catch ( CoreException e ) {
			e.printStackTrace();
		}

	}
	
	public static List<String> getBranches( IProject project ) {
		
		assert project != null;
		
		ArrayList<String> result = new ArrayList<String>();
		
		GitCommand gitCommand = new GitCommand( "branch", false );
		
		try {
			gitCommand.executeOn( project );
		} catch ( InterruptedException e ) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static List<String> getBranches( List<IProject> projects ) {
		
		assert projects != null;
		
		ArrayList<String> result = new ArrayList<String>();
		
		for( IProject project : projects ) {
			List<String> branches = getBranches( project );
			
			for( String branch : branches ) {
				if( ! result.contains( branch ) ) {
					result.add( branch );
				}
			}
		}
		
		return result;
	}

}
