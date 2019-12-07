package commands;

import storages.IStorage;
import user.User;

/**
 * Класс предназначен для реализации команды удаления пользователя.
 */
public class CommandDelete implements Command 
{
	private IStorage m_storage;
	private User m_user;
	
	/**
	 * Конструктор класса CommandDelete.
	 * @param a_storage - хранилище данных пользователей
	 * @param a_user - объект класса User (пользователь, которого необходимо удалить из хранилища)
	 */
	public CommandDelete (IStorage a_storage, User a_user) 
	{
		m_storage = a_storage;
		m_user = a_user;
	}
	
	@Override
	public void execute() throws Exception 
	{
		m_storage.deleteUser(m_user.getId());
	}
	
	@Override public void undo() throws Exception 
	{
		m_storage.addUser(m_user);
	}
	
	@Override
	public void redo() throws Exception 
	{
		m_storage.deleteUser(m_user.getId());
	}
}