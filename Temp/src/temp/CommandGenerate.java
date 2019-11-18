package temp;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс предназначен для реализации команды добавления указанного количества пользователей в хранилище.
 */
public class CommandGenerate implements Command{
	
	private Storage storage;
	private List <User> allUsers;
	private List <User> generatedUsers;
	private List<String> generatedUsersLines;
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
		setGeneratedUsers();
		User user = null;
		for (int i = 0; i < generatedUsers.size(); i++) {
			storage.addUser(generatedUsers.get(i));
			allUsers = storage.getUsersDataSet(false, false);
			for (User temp : allUsers)
				user = temp;
			generatedUsers.get(i).setId(user.getId());
		}
	}
	
	/**
	 * Метод устанавливает список для сгенерированных пользователей значениями, полученными 
	 * из метода генерации пользователей generateUsersData() класса UsersDataGenerator.
	 */
	private void setGeneratedUsers() {
		generatedUsersLines = generator.generateUsersData();
		User user;
		for (int i = 0; i < generatedUsersLines.size() - 3; i+=4) {
			user = new User();
			user.setName(generatedUsersLines.get(i));
			user.setSurname(generatedUsersLines.get(i + 1));
			user.setAge(Integer.parseInt(generatedUsersLines.get(i + 2)));
			user.setIsActive(Boolean.parseBoolean(generatedUsersLines.get(i + 3)));
			generatedUsers.add(user);	
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