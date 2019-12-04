package commands;

import storages.Storage;
import user.User;
import user.UserData;

/**
 * Класс предназначен для реализации команды обновления пользователя.
 */
public class CommandUpdate implements Command 
{
	private Storage m_storage;
	private User m_user;
	private User m_updatedUser;
	private Object m_value;
	private int m_valueInformation;
	
	/**
	 * Конструктор класса CommandUpdate.
	 * @param a_storage - хранилище данных пользователей
	 * @param a_user - объект класса User (пользователь, данные которого необходимо обновить)
	 * @param a_value - значение, которое необходимо присвоить изменяемому свойству объекта a_user
	 * @param a_valueInformation - информация о том, какое свойство пользователя необходимо изменить 
	 * (связана с классом UsersData, предоставляющим статические неизменяемые поля, каждое из которых 
	 * соответствует одному из разрешённых к изменению полей объекта a_user)
	 */
	public CommandUpdate (Storage a_storage, User a_user, Object a_value, int a_valueInformation) 
	{
		m_storage = a_storage;
		m_user = a_user;
		m_value = a_value;
		m_valueInformation = a_valueInformation;
	}
	
	@Override
	public void execute() throws Exception 
	{
		m_updatedUser = new User();
		m_updatedUser.setId(m_user.getId());
		m_updatedUser.setName(m_user.getName());
		m_updatedUser.setSurname(m_user.getSurname());
		m_updatedUser.setAge(m_user.getAge());
		m_updatedUser.setIsActive(m_user.isActive());
		if (m_valueInformation == UserData.FIRSTNAME) m_updatedUser.setName(String.valueOf(m_value));
		if (m_valueInformation == UserData.LASTNAME) m_updatedUser.setSurname(String.valueOf(m_value));
		if (m_valueInformation == UserData.AGE) m_updatedUser.setAge(Integer.parseInt(String.valueOf(m_value)));
		if (m_valueInformation == UserData.ISACTIVE) m_updatedUser.setIsActive(Boolean.parseBoolean(String.valueOf(m_value)));
		m_storage.updateUser(m_updatedUser);
	}
	
	@Override public void undo() throws Exception 
	{
		m_storage.updateUser(m_user);
	}
	
	@Override
	public void redo() throws Exception 
	{
		m_storage.updateUser(m_updatedUser);
	}
}
