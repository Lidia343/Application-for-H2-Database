package mainPackage;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс предназначен для реализации команды добавления указанного количества пользователей в хранилище.
 */
public class CommandGenerate implements Command{
	
	private Storage storage;
	private List <User> allUsers;
	private List <User> generatedUsers;
	private UsersDataGenerator generator;

	/**
	 * Конструктор класса CommandGenerate.
	 * @param storage - хранилище данных пользователей
	 * @param userNumbers - количество пользователей для добавления в хранилище
	 */
	public CommandGenerate (Storage storage, int userNumbers) {
		this.storage = storage;
		generator = new UsersDataGenerator (userNumbers);
		generatedUsers = new ArrayList<>();
	}
	
	@Override
	public void execute() throws Exception {
		generatedUsers = generator.generateUsers();
		User user = null;
		for (User u : generatedUsers) {
			storage.addUser(u);
			allUsers = storage.getUsersDataSet(false, false);
			for (User temp : allUsers)
				user = temp;
			u.setId(user.getId());
		}
	}
	
	@Override public void undo() throws Exception {
		for (User user : generatedUsers) 
		storage.deleteUser(user.getId());
	}
	
	@Override
	public void redo() throws Exception {
		for (User user : generatedUsers)
			storage.addUser(user, user.getId());	
	}
}