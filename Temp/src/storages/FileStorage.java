package storages;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import user.User;
import user.UsersListSorter;

/**
 * Класс предназначен для работы с файлом, в котором должна храниться информация о пользователях.
 */
public class FileStorage implements Storage 
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
		User m_user;
		m_reader = new BufferedReader (new FileReader (a_file));
		m_reader.readLine();
		m_reader.readLine();
		String m_line = m_reader.readLine(), m_buffer;
		int m_i;
		int m_j = 0;
		while (m_line != null) 
		{
			m_user = new User();
			m_buffer = "";
			m_i = 0;
			while (m_i < m_line.length()) 
			{
				if (((m_line.charAt(m_i) >= '0') && (m_line.charAt(m_i) <= '9')) || 
						((m_line.charAt(m_i) >= 'a') && (m_line.charAt(m_i) <= 'z')) || 
						((m_line.charAt(m_i) >= 'A') && (m_line.charAt(m_i) <= 'Z')) ||
						((m_line.charAt(m_i) >= 'а') && (m_line.charAt(m_i) <= 'я')) ||
						((m_line.charAt(m_i) >= 'А') && (m_line.charAt(m_i) <= 'Я'))) 
				{
					m_buffer += m_line.charAt(m_i); 
					if (m_buffer.equals("true") || m_buffer.equals("false")) 
						m_user.setIsActive(Boolean.parseBoolean(m_buffer));
				} else 
					{
						if (!m_buffer.equals("")) 
						{
							if (m_j == 0) m_user.setId(Integer.parseInt(m_buffer));
							if (m_j == 1) m_user.setName(m_buffer);
							if (m_j == 2) m_user.setSurname(m_buffer);
							if (m_j == 3) m_user.setAge(Integer.parseInt(m_buffer));
							m_j++;
						}
						m_buffer = "";  
					}
				m_i++;
			}
			m_j = 0;
			m_usersDataList.add(m_user);
			m_line = m_reader.readLine();
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
		String m_temp = m_reader.readLine();
		m_temp = m_reader.readLine();
		m_temp = m_reader.readLine();
		
		if ((m_temp == null) || (m_temp.length()*m_temp.getBytes().length == m_temp.getBytes().length)) 
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
		int m_userNameSize = a_userName.length();
		int m_userSurnameSize = a_userSurname.length();
		int m_tabCountAfterUserName = 8 - (int)(m_userNameSize/8),
		    m_tabCountAfterUserSurname = 8 - (int)(m_userSurnameSize/8);
		for (int m_i = 0; m_i < m_tabCountAfterUserName; m_i++) 
			m_afterNameTab += "\t";
		
		for (int m_i = 0; m_i < m_tabCountAfterUserSurname; m_i++) 
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
		String m_userLine = (m_id + 1) + "\t" + a_user.getName() + m_afterNameTab + a_user.getSurname() + m_afterSurnameTab + a_user.getAge() + "\t\t" + a_user.isActive() + "\r\n";
		m_writer.append(m_userLine); 
		m_writer.close();
			
		a_user.setId(m_id + 1);
		m_usersDataList.add(a_user);
			
		if (a_isUpdated) updateIdFile (Integer.toString(m_id + 1));
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
			String m_idLine;
			m_idReader = new BufferedReader (new FileReader (m_maxIdFile));
			m_idLine = m_idReader.readLine();
			m_id = Integer.parseInt(m_idLine);
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
			int[] m_indexes = new int[m_usersDataList.size()];
			for (int m_i = 0; m_i < m_usersDataList.size(); m_i++) 
			{
				m_indexes[m_i] = m_usersDataList.get(m_i).getId();
			}

			int m_max = m_indexes[0], m_maxi = 0;
			for (int m_i = 0; m_i < m_indexes.length; m_i++) 
			{
				if (m_indexes[m_i] > m_max) 
				{
					m_max = m_indexes[m_i];
					m_maxi = m_i;
				}
			}
			
			updateIdFile(Integer.toString(m_indexes[m_maxi]));
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
		String m_line = m_reader.readLine(), m_idBuffer = "";
		m_prevUsersDataList.add(m_line);
		int m_i;
			
		while (m_line != null) 
		{
			m_idBuffer = "";
			m_i = 0;
			while (m_i < m_line.length()) 
			{
				if ((m_line.charAt(m_i) >= '0') && (m_line.charAt(m_i) <= '9')) 
				{
					m_idBuffer += m_line.charAt(m_i);
				} else break;
				m_i++;
			}
			if (m_idBuffer.equals("" + a_userId)) break;
			m_line = m_reader.readLine();
			m_prevUsersDataList.add(m_line);
		}
		m_prevUsersDataList.remove(m_prevUsersDataList.size() - 1);
		m_line = m_reader.readLine();
		while (m_line != null) 
		{
			m_nextUsersDataList.add(m_line);
			m_line = m_reader.readLine();
		}
			
		m_writer = new FileWriter (m_file, false); 
			
		for (m_i = 0; m_i < m_prevUsersDataList.size(); m_i++)
			m_writer.write(m_prevUsersDataList.get(m_i) + "\r\n");
	}
	
	/**
	 * Метод добавляет в файл содержавшиеся в нём до обновления/удаления пользователя данные, которые были расположены после данного пользователя.
	 * @throws IOException 
	 */
	private void updateTextAfterUser () throws IOException 
	{
		for (int m_i = 0; m_i < m_nextUsersDataList.size(); m_i++)
			m_writer.write(m_nextUsersDataList.get(m_i) + "\r\n");
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
		
		int m_dataIndex = 0;
		for (User m_temp : m_usersDataList)
			if (m_temp.getId() == a_user.getId()) m_dataIndex = m_usersDataList.indexOf(m_temp);
		m_usersDataList.set(m_dataIndex, a_user);
	}
	
	@Override
	public void deleteUser(int a_id) throws IOException 
	{
		updateTextBeforeUser (a_id);
		updateTextAfterUser ();
		
		int m_dataIndex = 0;
		for (User m_temp : m_usersDataList)
			if (m_temp.getId() == m_id) m_dataIndex = m_usersDataList.indexOf(m_temp);
		m_usersDataList.remove(m_dataIndex); 
		findMaxUserId();
	}
	
	@Override
	public void deleteAllUsers() throws IOException 
	{
		m_writer = new FileWriter (m_file, false); 
		m_writer.write("Код:" + "\t" + "Имя:    " +  "\t\t\t\t\t\t\t" + "Фамилия:" + "\t\t\t\t\t\t\t" + "Возраст:" + "\t" + "Активен:" + "\r\n\r\n");
		m_reader = new BufferedReader (new FileReader (m_file));
		String m_line = m_reader.readLine();
		while (m_line != null) 
			m_writer.write("\r\n");
		m_writer.close();
		m_allUsersDeleted = true;
		m_deletedUsersDataList.clear();
		for (int m_i = 0; m_i < m_usersDataList.size(); m_i++) 
		{
			m_deletedUsersDataList.add(m_usersDataList.get(m_i));
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
}
