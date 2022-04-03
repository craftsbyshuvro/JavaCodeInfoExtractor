package myfirstplugin.actions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import myfirstplugin.DirectoryExplorer;
import myfirstplugin.PPATypeVisitor;
import myfirstplugin.ProjectAnalyser;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import ca.mcgill.cs.swevo.ppa.PPAOptions;
import ca.mcgill.cs.swevo.ppa.ui.PPAUtil;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.jface.dialogs.MessageDialog;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class SampleAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	/**
	 * The constructor.
	 */
	public SampleAction() {
	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		
//	    File javaFile = new File("D:/Study/Final Project/Applications/MyFirstPlugin/Test/SampleJavaFile.java");
	    
//	    File javaFile = new File("C:/Users/USER/OneDrive/Desktop/ShedLock/providers/arangodb/shedlock-provider-arangodb/src/main/java/net/javacrumbs/shedlock/provider/arangodb/ArangoLockProvider.java");
	    
//	    File javaFile = new File("D:/Study/Final Project/Dataset/Projects/kstyle/app/src/main/java/com/keegan/kstyle/AnimationActivity.java");

	    
	    // CompilationUnit contains the AST of the partial program
	    // PPAOptions is a wrapper and contains various configuration options:
	    // most options are still not implemented, so the default options are
	    // usually fine.
	    
	    
	    
	    
//	    
//	    CompilationUnit cu = PPAUtil.getCU(javaFile, new PPAOptions());
//	    PPATypeVisitor visitor = new PPATypeVisitor(System.out, cu);
//	    cu.accept(visitor); 
		
		 
		DirectoryExplorer _directoryExplorer = new DirectoryExplorer();
		try {
			_directoryExplorer.ExploreDirectory();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}