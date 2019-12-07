package graphics;

import java.io.InputStream;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

/**
 * Класс предназначен для работы со свойствами объекта класса Shell.
 */
public class ShellProperties 
{
	private final String m_title;
	private final Image m_image;
	private final Point m_size;
	private final Color m_backColor;
	
	/**
	 * Класс предназначен для создания объекта класса ShellProperties.
	 */
	public static class Builder 
	{ //Объект данного класса не будет хранить ссылку на объект класса ShellProperties
		//Обязательные для передачи в конструктор параметры:
		private final String m_title;
		private final String m_imageName;
		
		//Необязательные для передачи в конструктор параметры:
		private Image m_image;
		private Point m_size;
		private Color m_backColor;
		
		public Builder (String a_title, String a_imageName) 
		{
			m_title = a_title;
			m_imageName = a_imageName;
			//Инициализация значениями по умолчанию:
			m_size = new Point (900, 550);
			m_backColor = new Color (Display.getDefault(), 83, 82, 82);
		}
		
		/**
		 * Метод устанавливает изображение.
		 */
		private void setImage () 
		{
			try 
			{
				InputStream stream = Graphics.class.getResourceAsStream("/icons/" + m_imageName);
				m_image = new Image (Display.getDefault(), stream);
				stream.close();
			} catch (Exception a_e) 
			{
				m_image = null;
			}
		}
		
		/**
		 * Метод устанавливает размер.
		 */
		public Builder size (Point a_size) 
		{
			if (a_size != null)
				m_size = a_size;
			return this;
		}
		
		/**
		 * Метод устанавливает цвет фона.
		 */
		public Builder backColor (Color a_backColor) 
		{
			if (a_backColor != null)
				m_backColor = a_backColor;
			return this;
		}
		
		/**
		 * Метод возвращает объект класса ShellProperties.
		 */
		public ShellProperties build () 
		{
			setImage();
			return new ShellProperties (this);
		}
	}
	
	/**
	 * Конструктор класса ShellProperties.
	 * @param a_builder - объект класса Builder
	 */
	private ShellProperties (Builder a_builder) 
	{
		m_title = a_builder.m_title;
		m_image = a_builder.m_image;
		m_size = a_builder.m_size;
		m_backColor = a_builder.m_backColor;
	}
	
	/**
	 * Метод возвращает заголовок.
	 */
	public String getTitle() 
	{
		return m_title;
	}
	
	/**
	 * Метод возвращает изображение.
	 */
	public Image getImage() 
	{
		return m_image;
	}
	
	/**
	 *  Метод возвращает размер.
	 */
	public Point getSize() 
	{
		return m_size;
	}
	
	/**
	 *  Метод возвращает фоновый цвет.
	 */
	public Color getBackColor() 
	{
		return m_backColor;
	}
}
