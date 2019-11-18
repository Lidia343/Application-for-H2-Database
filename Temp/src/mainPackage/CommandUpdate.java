package mainPackage;
/**
 * Класс предназначен для реализации команды обновления пользователя.
 */
public class CommandUpdate implements Command {
	
	private Storage storage;
	private User user;
	private User updatedUser;
	private Object value;
	private int valueInformation;
	
	/**
	 * Конструктор класса CommandUpdate.
	 * @param storage - хранилище данных пользователей
	 * @param user - объект класса User (пользователь, данные которого необходимо обновить)
	 * @param value - значение, которое необходимо присвоить изменяемому свойству объекта user
	 * @param valueInformation - информация о том, какое свойство пользователя необходимо изменить 
	 * (связана с классом UsersData, предоставляющим статические неизменяемые поля, каждое из которых 
	 * соответствует одному из разрешённых к изменению полей объекта user)
	 */
	public CommandUpdate (Storage storage, User user, Object value, int valueInformation) {
		this.storage = storage;
		this.user = user;
		this.value = value;
		this.valueInformation = valueInformation;
	}
	
	@Override
	public void execute() throws Exception {
		updatedUser = new User();
		updatedUser.setId(user.getId());
		updatedUser.setName(user.getName());
		updatedUser.setSurname(user.getSurname());
		updatedUser.setAge(user.getAge());
		updatedUser.setIsActive(user.isActive());
		if (valueInformation == UserData.FIRSTNAME) updatedUser.setName(String.valueOf(value));
		if (valueInformation == UserData.LASTNAME) updatedUser.setSurname(String.valueOf(value));
		if (valueInformation == UserData.AGE) updatedUser.setAge(Integer.parseInt(String.valueOf(value)));
		if (valueInformation == UserData.ISACTIVE) updatedUser.setIsActive(Boolean.parseBoolean(String.valueOf(value)));
		storage.updateUser(updatedUser);
	}
	
	@Override public void undo() throws Exception {
		storage.updateUser(user);
	}
	
	@Override
	public void redo() throws Exception {
		storage.updateUser(updatedUser);
	}
}
