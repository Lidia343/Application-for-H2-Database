/**
 * Класс предназначен для реализации команды обновления пользователя.
 */
public class CommandUpdate implements Command {
	
	private Storage storage;
	private User prevUser;
	private User nextUser;
	private Object value;
	private int valueInformation;
	
	/**
	 * Конструктор класса CommandUpdate.
	 * @param storage - хранилище данных пользователей
	 * @param user - объект класса User
	 */
	public CommandUpdate (Storage storage, User user, Object value, int valueInformation) {
		this.storage = storage;
		this.prevUser = user;
		this.value = value;
		this.valueInformation = valueInformation;
	}
	
	@Override
	public void execute() throws Exception {
		nextUser = new User();
		nextUser.setId(prevUser.getId());
		nextUser.setName(prevUser.getName());
		nextUser.setSurname(prevUser.getSurname());
		nextUser.setAge(prevUser.getAge());
		nextUser.setIsActive(prevUser.isActive());
		if (valueInformation == UserData.FIRSTNAME) nextUser.setName(String.valueOf(value));
		if (valueInformation == UserData.LASTNAME) nextUser.setSurname(String.valueOf(value));
		if (valueInformation == UserData.AGE) nextUser.setAge(Integer.parseInt(String.valueOf(value)));
		if (valueInformation == UserData.ISACTIVE) nextUser.setIsActive(Boolean.parseBoolean(String.valueOf(value)));
		storage.updateUser(nextUser);
	}
	
	@Override public void undo() throws Exception {
		storage.updateUser(prevUser);
	}
	
	@Override
	public void redo() throws Exception {
		storage.updateUser(nextUser);
	}
}
