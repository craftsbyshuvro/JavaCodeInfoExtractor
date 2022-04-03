package myfirstplugin;

import java.awt.GraphicsConfiguration;
import java.awt.Polygon;
import java.util.Random;

import javax.swing.JFrame;

public class SampleJavaFile {
	public String Name;
	public int Age;
	
	public GraphicsConfiguration gc;
	
	JFrame framess;
	
	//sample method comment
	public void jFrameDetails() {	
	    
		JFrame frame = new JFrame(gc);
   
	    String myString = new String();
	    setName(myString);

	    frame.setBackground(null);
	    frame.setTitle("Welecome to JavaTutorial.net");
	    gc.getBounds();
	    gc.getDevice();
	    frame.addNotify();
	    frame.setVisible(true);
	}
	
	/**
	* Sample Comment nine
	*/
	public void jFrameDetailsNewMethod() {	
		JFrame frame = new JFrame(gc);
	    frame.setTitle("Welecome to Mars");
	    gc.getBounds();
		//SampleComment 2
	    frame.setVisible(true);
	}
	
	
	/**
	* Sample Comment one
	*/
	private void setName(String myString) {
			// TODO Auto-generated method stub
			
	}
}