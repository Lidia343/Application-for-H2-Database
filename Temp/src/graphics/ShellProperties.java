package graphics;

import java.io.InputStream;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

/**
 * Класс предназначен для работы со свойствами объекта класса Shell.
 */
public class ShellProperties {
	
	private final String title;
	private final Image image;
	private final Point size;
	private final Color backColor;
	
	/**
	 * Класс предназначен для создания объекта класса ShellProperties.
	 */
	public static class Builder { //Объект данного класса не будет хранить ссылку на объект класса ShellProperties
		
		//Обязательные для передачи в конструктор параметры:
		private final String title;
		private final String imageName;
		
		//Необязательные для передачи в конструктор параметры:
		private Image image;
		private Point size;
		private Color backColor;
		
		public Builder (String title, String imageName) {
			this.title = title;
			this.imageName = imageName;
			//Инициализация значениями по умолчанию:
			size = new Point (900, 550);
			backColor = new Color (Display.getDefault(), 83, 82, 82);
		}
		
		/**
		 * Метод устанавливает изображение.
		 */
		private void setImage () {
			try {
				InputStream stream = Graphics.class.getResourceAsStream("/icons/" + imageName);
				image = new Image (Display.getDefault(), stream);
				stream.close();
			} catch (Exception e) {
				image = null;
			}
		}
		
		/**
		 * Метод устанавливает размер.
		 */
		public Builder size (Point size) {
			if (size != null)
			this.size = size;
			return this;
		}
		
		/**
		 * Метод устанавливает цвет фона.
		 */
		public Builder backColor (Color backColor) {
			if (backColor != null)
			this.backColor = backColor;
			return this;
		}
		
		/**
		 * Метод возвращает объект класса ShellProperties.
		 */
		public ShellProperties build () {
			setImage ();
			return new ShellProperties (this);
		}
	}
	
	/**
	 * Конструктор класса ShellProperties.
	 * @param builder - объект класса Builder
	 */
	private ShellProperties (Builder builder) {
		title = builder.title;
		image = builder.image;
		size = builder.size;
		backColor = builder.backColor;
	}
	
	/**
	 * Метод возвращает заголовок.
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Метод возвращает изображение.
	 */
	public Image getImage() {
		return image;
	}
	
	/**
	 *  Метод возвращает размер.
	 */
	public Point getSize() {
		return size;
	}
	
	/**
	 *  Метод возвращает фоновый цвет.
	 */
	public Color getBackColor() {
		return backColor;
	}
}
