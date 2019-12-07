package storages;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;

import user.User;
import user.UsersListSorter;

/**
 * Класс предназначен для работы с файлом, в котором должна храниться информация о пользователях.
 */
public class FileStorage implements Storage, IExecutableExtension
{
	private String m_fileName;
	private File m_file;
	private File m_maxIdFile; //Файл для записи максимального значения первичного ключа 
	private int m_id;
	private FileWriter m_writer;
	private FileWriter m_idWriter;
	private BufferedReader m_reader;
	private BufferedReader m_idReader;
	private boolean m_allUsersDeleted;

	private List<User> m_usersDataList; //Список для всех пользователей, занесённых в файл
	private final List<User> m_deletedUsersDataList;
	private List<String> m_prevUsersDataList; //Список для пользователей, занесённых в файл и расположенных до выбранного для удаления/изменения пользователя
	private List<String> m_nextUsersDataList; //Список для пользователей, занесённых в файл и расположенных после выбранного для удаления/изменения пользователя
	
	private String m_afterNameTab; //Строка, содержащая необходимый отступ после имени пользователя для текущей строки в файле
	private String m_afterSurnameTab; //Строка, содержащая необходимый отступ после фамилии пользователя для текущей строки в файле
	
	/**
	 * Конструктор класса FileStorage.
	 * @param a_fileName - имя файла
	 */
	public FileStorage(String a_fileName) 
	{
		m_fileName = a_fileName;
		m_id = 0;
		m_usersDataList = new ArrayList<>();
		m_deletedUsersDataList = new ArrayList<>();
		m_prevUsersDataList = new ArrayList<>();
		m_nextUsersDataList = new ArrayList<>();
		m_allUsersDeleted = false;
	}
	
	/**
	 * Конструктор класса FileStorage.
	 */
	public FileStorage() 
	{
		m_id = 0;
		m_usersDataList = new ArrayList<>();
		m_deletedUsersDataList = new ArrayList<>();
		m_prevUsersDataList = new ArrayList<>();
		m_nextUsersDataList = new ArrayList<>();
		m_allUsersDeleted = false;
	}
	
	@Override
	public void setStorage() 
	{
		m_file = new File (m_fileName);
	}
	
	/**
	 * Метод обновляет файл, где хранится максимальный ПК.
	 * @param a_text - строка, которую необходимо записать в файл
	 * @throws IOException 
	 */
	private void updateIdFile (String a_text) throws IOException 
	{
		m_idWriter = new FileWriter (m_maxIdFile, false);
		m_idWriter.write(a_text);
		m_idWriter.close();
	}
	
	/**
	 * Метод записывает значение "-1" в файл, где хранится максимальный ПК.
	 * @throws Exception 
	 */
	private void updateIdFile() throws IOException 
	{
		updateIdFile("-1");
	}
	
	/**
	 * Метод записывает из файла file в список данные всех пользователей.
	 * @throws IOException 
	 */
	private void writeInList(File a_file) throws IOException 
	{
		m_writer.close();
		User user;
		m_reader = new BufferedReader (new FileReader (a_file));
		m_reader.readLine();
		m_reader.readLine();
		String line = m_reader.readLine(), buffer;
		int i;
		int j = 0;
		while (line != null) 
		{
			user = new User();
			buffer = "";
			i = 0;
			while (i < line.length()) 
			{
				if (((line.charAt(i) >= '0') && (line.charAt(i) <= '9')) || 
						((line.charAt(i) >= 'a') && (line.charAt(i) <= 'z')) || 
						((line.charAt(i) >= 'A') && (line.charAt(i) <= 'Z')) ||
						((line.charAt(i) >= 'а') && (line.charAt(i) <= 'я')) ||
						((line.charAt(i) >= 'А') && (line.charAt(i) <= 'Я'))) 
				{
					buffer += line.charAt(i); 
					if (buffer.equals("true") || buffer.equals("false")) 
						user.setIsActive(Boolean.parseBoolean(buffer));
				} else 
					{
						if (!buffer.equals("")) 
						{
							if (j == 0) user.setId(Integer.parseInt(buffer));
							if (j == 1) user.setName(buffer);
							if (j == 2) user.setSurname(buffer);
							if (j == 3) user.setAge(Integer.parseInt(buffer));
							j++;
						}
						buffer = "";  
					}
				i++;
			}
			j = 0;
			m_usersDataList.add(user);
			line = m_reader.readLine();
		}
		findMaxUserId();
	}
	
	@Override
	public void createStorageObject() throws IOException 
	{
		m_maxIdFile = new File ("maxId.txt");
		m_writer = new FileWriter (m_file, true); 
		m_writer.close();
		m_reader = new BufferedReader (new FileReader (m_file));
		String temp = m_reader.readLine();
		temp = m_reader.readLine();
		temp = m_reader.readLine();
		
		if ((temp == null) || (temp.length()*temp.getBytes().length == temp.getBytes().length)) 
		{
			m_writer = new FileWriter (m_file, false); 
			m_writer.write("Код:" + "\t" + "Имя:    " +  "\t\t\t\t\t\t\t" + "Фамилия:" + "\t\t\t\t\t\t\t" + "Возраст:" + "\t" + "Активен:" + "\r\n\r\n");
			m_writer.close();
				
			updateIdFile();
		} else 
			writeInList(m_file);
		if (!m_maxIdFile.exists()) 
			updateIdFile();

		m_writer = new FileWriter (m_file, true);
	}
	
	@Override
	public void updateStorageObject() throws IOException 
	{ 
		updateIdFile ();
	}
	
	/**
	 * Метод рассчитывает необходимый отступ после имени и фамилии пользователя.
	 * @param a_userName - имя
	 * @param a_userSurname - фамилия
	 */
	private void calculateTabForUserData(String a_userName, String a_userSurname) 
	{
		m_afterNameTab = "";
		m_afterSurnameTab = "";
		int userNameSize = a_userName.length();
		int userSurnameSize = a_userSurname.length();
		int tabCountAfterUserName = 8 - (int)(userNameSize/8),
		    tabCountAfterUserSurname = 8 - (int)(userSurnameSize/8);
		for (int i = 0; i < tabCountAfterUserName; i++) 
			m_afterNameTab += "\t";
		
		for (int i = 0; i < tabCountAfterUserSurname; i++) 
			m_afterSurnameTab += "\t";
	}
	
	/**
	 * Метод записывает пользователя в файл.
	 * @param a_user - объект класса User
	 * @param a_id - (код пользователя - 1)
	 * @param a_isUpdated - обновлять файл с максимальным ПК/не обновлять
	 * @throws IOException
	 */
	private void writeUserInFile(User a_user, int a_id, boolean a_isUpdated) throws IOException 
	{
		calculateTabForUserData(a_user.getName(), a_user.getSurname());
		m_writer = new FileWriter (m_file, true); 
		String userLine = (a_id + 1) + "\t" + a_user.getName() + m_afterNameTab + a_user.getSurname() + m_afterSurnameTab + a_user.getAge() + "\t\t" + a_user.isActive() + "\r\n";
		m_writer.append(userLine); 
		m_writer.close();
			
		a_user.setId(a_id + 1);
		m_usersDataList.add(a_user);
			
		if (a_isUpdated) updateIdFile (Integer.toString(a_id + 1));
	}
	
	@Override
	public int addUser (User a_user) throws IOException
	{
		if (a_user.getId() == -1) 
		{
			if (m_allUsersDeleted) 
			{
				m_usersDataList.clear();
				m_allUsersDeleted = false;
			}
			String idLine;
			m_idReader = new BufferedReader (new FileReader (m_maxIdFile));
			idLine = m_idReader.readLine();
			m_id = Integer.parseInt(idLine);
			writeUserInFile (a_user, m_id, true);
			return (m_id + 1);
		} 
		else 
		{
			writeUserInFile (a_user, a_user.getId() - 1, false);
			findMaxUserId();
			return a_user.getId();
		}
	}
	
	/**
	 * Метод находит максимальный ПК пользователей списка и записывает его в cоответствующий файл. Если список пуст, записывает "-1".
	 * @throws IOException
	 */
	private void findMaxUserId() throws IOException 
	{
		if (m_usersDataList.size() != 0) 
		{
			int[] indexes = new int[m_usersDataList.size()];
			for (int i = 0; i < m_usersDataList.size(); i++) 
			{
				indexes[i] = m_usersDataList.get(i).getId();
			}

			int max = indexes[0], maxi = 0;
			for (int i = 0; i < indexes.length; i++) 
			{
				if (indexes[i] > max) 
				{
					max = indexes[i];
					maxi = i;
				}
			}
			
			updateIdFile(Integer.toString(indexes[maxi]));
		} else updateIdFile ("-1");
	}
	
	/**
	 * Метод обновляет содержащиеся в файле данные, записанные до указанного пользователя.
	 * @param a_userId - код пользователя
	 * @throws IOException 
	 */
	private void updateTextBeforeUser(int a_userId) throws IOException 
	{
		m_writer.close();
		m_reader = new BufferedReader (new FileReader (m_file));
		m_prevUsersDataList.add(m_reader.readLine());
		m_prevUsersDataList.add(m_reader.readLine());
		String line = m_reader.readLine(), idBuffer = "";
		m_prevUsersDataList.add(line);
		int i;
			
		while (line != null) 
		{
			idBuffer = "";
			i = 0;
			while (i < line.length()) 
			{
				if ((line.charAt(i) >= '0') && (line.charAt(i) <= '9')) 
				{
					idBuffer += line.charAt(i);
				} else break;
				i++;
			}
			if (idBuffer.equals("" + a_userId)) break;
			line = m_reader.readLine();
			m_prevUsersDataList.add(line);
		}
		m_prevUsersDataList.remove(m_prevUsersDataList.size() - 1);
		line = m_reader.readLine();
		while (line != null) 
		{
			m_nextUsersDataList.add(line);
			line = m_reader.readLine();
		}
			
		m_writer = new FileWriter (m_file, false); 
			
		for (i = 0; i < m_prevUsersDataList.size(); i++)
			m_writer.write(m_prevUsersDataList.get(i) + "\r\n");
	}
	
	/**
	 * Метод добавляет в файл содержавшиеся в нём до обновления/удаления пользователя данные, которые были расположены после данного пользователя.
	 * @throws IOException 
	 */
	private void updateTextAfterUser () throws IOException 
	{
		for (int i = 0; i < m_nextUsersDataList.size(); i++)
			m_writer.write(m_nextUsersDataList.get(i) + "\r\n");
		m_writer.close();
		m_prevUsersDataList.clear();
		m_nextUsersDataList.clear();
	}
	
	@Override
	public void updateUser (User a_user) throws IOException 
	{
		calculateTabForUserData(a_user.getName(), a_user.getSurname());
		updateTextBeforeUser (a_user.getId());
		m_writer.write(a_user.getId() + "\t" + a_user.getName() + m_afterNameTab + a_user.getSurname() + m_afterSurnameTab + a_user.getAge() + "\t\t" + a_user.isActive() + "\r\n");
		updateTextAfterUser ();
		
		int dataIndex = 0;
		for (User temp : m_usersDataList)
			if (temp.getId() == a_user.getId()) dataIndex = m_usersDataList.indexOf(temp);
		m_usersDataList.set(dataIndex, a_user);
	}
	
	@Override
	public void deleteUser(int a_id) throws IOException 
	{
		updateTextBeforeUser (a_id);
		updateTextAfterUser ();
		
		int dataIndex = 0;
		for (User temp : m_usersDataList)
			if (temp.getId() == a_id) dataIndex = m_usersDataList.indexOf(temp);
		m_usersDataList.remove(dataIndex); 
		findMaxUserId();
	}
	
	@Override
	public void deleteAllUsers() throws IOException 
	{
		m_writer = new FileWriter (m_file, false); 
		m_writer.write("Код:" + "\t" + "Имя:    " +  "\t\t\t\t\t\t\t" + "Фамилия:" + "\t\t\t\t\t\t\t" + "Возраст:" + "\t" + "Активен:" + "\r\n\r\n");
		m_reader = new BufferedReader (new FileReader (m_file));
		String line = m_reader.readLine();
		while (line != null) 
			m_writer.write("\r\n");
		m_writer.close();
		m_allUsersDeleted = true;
		m_deletedUsersDataList.clear();
		for (int i = 0; i < m_usersDataList.size(); i++) 
		{
			m_deletedUsersDataList.add(m_usersDataList.get(i));
		}
		m_usersDataList.clear();
	}
	
	@Override
	public List <User> getUsersDataSet(boolean a_sorting, boolean a_usersAreDeleted) 
	{
		if (a_usersAreDeleted) 
		{
			if (a_sorting) Collections.sort(m_deletedUsersDataList, new UsersListSorter());
			return m_deletedUsersDataList;
		}
		if (a_sorting) Collections.sort(m_usersDataList, new UsersListSorter());
		return m_usersDataList;
	}
	
	@Override
	public void closeStorage() throws IOException 
	{
		if (m_reader != null) m_reader.close();
		if (m_idReader != null) m_idReader.close();
		if (m_writer != null) m_writer.close();
	}

	@Override
	public void setInitializationData(IConfigurationElement a_config, String a_propertyName, Object a_data) throws CoreException
	{
		m_fileName = (String)a_data;
	}
}
