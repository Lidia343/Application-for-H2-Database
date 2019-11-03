
/**
 * Главный класс приложения. Необходим для создания точки входа в программу. 
 * Создаёт объект класса Graphics и вызывает его метод createWindow(), создающий окно для взаимодействия с пользователем.
 */
public class Application {
	
	public static void main(String[] args) {
		Graphics graphics = new Graphics();
		graphics.createWindow();
	}
}
//"jdbc:h2:~/test"