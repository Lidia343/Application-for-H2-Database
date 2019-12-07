package user;

import java.util.List;

import storages.IStorage;

/**
 * Класс предназначен для реализации модели данных, которую необходимо передать в метод setInput() класса TableViewer.
 */
public class UsersModelProvider 
{
	private IStorage m_storage;
	
	/**
	 * Конструктор класса ModelProvider.
	 * @param storage - хранилище данных
	 */
	public UsersModelProvider(IStorage a_storage) 
	{
		m_storage = a_storage;
	}
	
	/**
	 * Метод возвращает данные всех пользователей из хранилища.
	 * @throws Exception
	 */
	public List<User> getUsersData() throws Exception 
	{
		return m_storage.getUsersDataSet(false, false);
	}
}
