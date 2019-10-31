import java.io.InputStream;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ShellPropertiesFactory {
	
	private Display display;
	
	public ShellPropertiesFactory (Display display) {
		this.display = display;
	}
	
	public void setShell(Shell shell, Point point, String text, String imageName, Color backColor) {
		try {
			InputStream stream = Graphics.class.getResourceAsStream(imageName);
			Image image = new Image (display, stream);
			shell.setImage(image);
			stream.close();
		} catch (Exception e) {
			shell.setImage(null);
		}
		if (point != null) shell.setMinimumSize(point); 
		shell.setText (text);          			
		shell.setBackground (backColor);   
	}
}
