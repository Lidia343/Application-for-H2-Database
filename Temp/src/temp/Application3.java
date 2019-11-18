package temp;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

public class Application3 implements IApplication {

	@Override
	public Object start(IApplicationContext context) throws Exception {
		/*Display display = new Display();
		Shell shell = new Shell (display);
		shell.setMinimumSize(200, 200);
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}*/
		Graphics graphics = new Graphics();
		graphics.createWindow();
		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
