package main;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import graphics.Graphics;

public class Application implements IApplication 
{
	@Override
	public Object start(IApplicationContext context) throws Exception 
	{
		Graphics m_graphics = new Graphics();
		m_graphics.createWindow();
		return IApplication.EXIT_OK;
	}
	
	@Override
	public void stop() 
	{
	}
}
