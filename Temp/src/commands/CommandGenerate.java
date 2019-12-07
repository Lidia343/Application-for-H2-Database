package commands;
import java.util.ArrayList;
import java.util.List;

import storages.IStorage;
import user.User;
import user.UsersDataGenerator;

/**
 * Класс предназначен для реализации команды добавления указанного количества пользователей в хранилище.
 */
public class CommandGenerate implements Command
{
	private IStorage m_storage;
	private List <User> m_generatedUsers;
	private UsersDataGenerator m_generator;

	/**
	 * Конструктор класса CommandGenerate.
	 * @param a_storage - хранилище данных пользователей
	 * @param a_userNumbers - количество пользователей для добавления в хранилище
	 */
	public CommandGenerate (IStorage a_storage, int a_userNumbers) 
	{
		m_storage = a_storage;
		m_generator = new UsersDataGenerator (a_userNumbers);
		m_generatedUsers = new ArrayList<>();
	}
	
	@Override
	public void execute() throws Exception 
	{
		m_generatedUsers = m_generator.generateUsers();
		int currentID;
		for (User user : m_generatedUsers) 
		{
			currentID = m_storage.addUser(user);
			user.setId(currentID);
		}
	}
	
	@Override public void undo() throws Exception 
	{
		for (User user : m_generatedUsers) 
			m_storage.deleteUser(user.getId());
	}
	
	@Override
	public void redo() throws Exception 
	{
		for (User user : m_generatedUsers)
			m_storage.addUser(user);	
	}
}