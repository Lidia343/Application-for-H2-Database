package commands;

import storages.Storage;
import user.User;

/**
 * Класс предназначен для реализации команды добавления пользователя.
 */
public class CommandAdd implements Command
{
	private Storage	m_storage;
	private User m_user;

	/**
	 * Конструктор класса CommandAdd.
	 * @param a_storage - хранилище данных пользователей
	 * @param a_user - объект класса User (пользователь, которого необходимо добавить в хранилище)
	 */
	public CommandAdd(Storage a_storage, User a_user)
	{
		m_storage = a_storage;
		m_user = a_user;
	}

	@Override
	public void execute() throws Exception
	{
		int currentID = m_storage.addUser(m_user);
		m_user.setId(currentID);
	}

	@Override
	public void undo() throws Exception
	{
		m_storage.deleteUser(m_user.getId());
	}

	@Override
	public void redo() throws Exception
	{
		m_storage.addUser(m_user);
	}
}
