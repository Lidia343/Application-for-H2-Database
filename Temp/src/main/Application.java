package main;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import graphics.Graphics;

public class Application implements IApplication 
{
	@Override
	public Object start(IApplicationContext context) throws Exception 
	{
		Graphics graphics = new Graphics();
		graphics.createWindow();
		return IApplication.EXIT_OK;
	}
	
	@Override
	public void stop() 
	{
	}
}
