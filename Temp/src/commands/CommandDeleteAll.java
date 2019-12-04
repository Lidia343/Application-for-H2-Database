package commands;

import java.util.List;

import storages.Storage;
import user.User;

/**
 * Класс предназначен для реализации команды удаления всех пользователей.
 */
public class CommandDeleteAll implements Command
{
	private final Storage m_storage;
	private List<User> m_users;
	
	/**
	 * Конструктор класса CommandDelete.
	 * @param a_storage - хранилище данных пользователей
	 * @throws Exception 
	 */
	public CommandDeleteAll (Storage a_storage) 
	{
		m_storage = a_storage;
	}
	
	@Override
	public void execute() throws Exception 
	{
		m_users = m_storage.getUsersDataSet(false, true);
		m_storage.deleteAllUsers();
	}
	
	@Override public void undo() throws Exception 
	{
		for (User user : m_users) 
			m_storage.addUser(user);	
	}
	
	@Override
	public void redo() throws Exception 
	{
		m_storage.deleteAllUsers();
	}
}