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
public class FileStorage implements Storage {
	
	private String fileName;
	private File file;
	private File maxIdFile; //Файл для записи максимального значения первичного ключа 
	private int id;
	private FileWriter writer;
	private FileWriter idWriter;
	private BufferedReader reader;
	private BufferedReader idReader;
	private boolean allUsersDeleted;

	private List<User> usersDataList; //Список для всех пользователей, занесённых в файл
	private final List<User> deletedUsersDataList;
	private List<String> prevUsersDataList; //Список для пользователей, занесённых в файл и расположенных до выбранного для удаления/изменения пользователя
	private List<String> nextUsersDataList; //Список для пользователей, занесённых в файл и расположенных после выбранного для удаления/изменения пользователя
	
	private String afterNameTab; //Строка, содержащая необходимый отступ после имени пользователя для текущей строки в файле
	private String afterSurnameTab; //Строка, содержащая необходимый отступ после фамилии пользователя для текущей строки в файле
	
	/**
	 * Конструктор класса FileStorage.
	 * @param fileName - имя файла
	 */
	public FileStorage(String fileName) {
		this.fileName = fileName;
		id = 0;
		usersDataList = new ArrayList<>();
		deletedUsersDataList = new ArrayList<>();
		prevUsersDataList = new ArrayList<>();
		nextUsersDataList = new ArrayList<>();
		allUsersDeleted = false;
	}
	
	@Override
	public void setStorage() {
		file = new File (fileName);
	}
	
	/**
	 * Метод обновляет файл, где хранится максимальный ПК.
	 * @param text - строка, которую необходимо записать в файл
	 * @throws IOException 
	 */
	private void updateIdFile (String text) throws IOException {
		idWriter = new FileWriter (maxIdFile, false);
		idWriter.write(text);
		idWriter.close();
	}
	
	/**
	 * Метод записывает значение "-1" в файл, где хранится максимальный ПК.
	 * @throws Exception 
	 */
	private void updateIdFile() throws IOException {
		updateIdFile("-1");
	}
	
	/**
	 * Метод записывает из файла file в список данные всех пользователей.
	 * @throws IOException 
	 */
	private void writeInList(File file) throws IOException {
		writer.close();
		User user;
		reader = new BufferedReader (new FileReader (file));
		reader.readLine();
		reader.readLine();
		String line = reader.readLine(), buffer;
		int i;
		int j = 0;
		while (line != null) {
			user = new User ();
			buffer = "";
			i = 0;
			while (i < line.length()) {
				if (((line.charAt(i) >= '0') && (line.charAt(i) <= '9')) || 
						((line.charAt(i) >= 'a') && (line.charAt(i) <= 'z')) || 
						((line.charAt(i) >= 'A') && (line.charAt(i) <= 'Z')) ||
						((line.charAt(i) >= 'а') && (line.charAt(i) <= 'я')) ||
						((line.charAt(i) >= 'А') && (line.charAt(i) <= 'Я'))) {
					buffer += line.charAt(i); 
					if (buffer.equals("true") || buffer.equals("false")) user.setIsActive(Boolean.parseBoolean(buffer));
				} else {
					if (!buffer.equals("")) {
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
			usersDataList.add(user);
			line = reader.readLine();
		}
		findMaxUserId();
	}
	
	@Override
	public void createStorageObject() throws IOException {
		maxIdFile = new File ("maxId.txt");
		writer = new FileWriter (file, true); 
		writer.close();
		reader = new BufferedReader (new FileReader (file));
		String temp = reader.readLine();
		temp = reader.readLine();
		temp = reader.readLine();
		
		if ((temp == null) || (temp.length()*temp.getBytes().length == temp.getBytes().length)) {
				
			writer = new FileWriter (file, false); 
			writer.write("Код:" + "\t" + "Имя:    " +  "\t\t\t\t\t\t\t" + "Фамилия:" + "\t\t\t\t\t\t\t" + "Возраст:" + "\t" + "Активен:" + "\r\n\r\n");
			writer.close();
				
			updateIdFile();
		} else 
			writeInList(file);
		if (!maxIdFile.exists()) {
			updateIdFile();
		}
		writer = new FileWriter (file, true);
	}
	
	@Override
	public void updateStorageObject() throws IOException { 
		updateIdFile ();
	}
	
	/**
	 * Метод рассчитывает необходимый отступ после имени и фамилии пользователя.
	 * @param userName - имя
	 * @param userSurname - фамилия
	 */
	private void calculateTabForUserData(String userName, String userSurname) {
		afterNameTab = "";
		afterSurnameTab = "";
		int userNameSize = userName.length();
		int userSurnameSize = userSurname.length();
		int tabCountAfterUserName = 8 - (int)(userNameSize/8),
		    tabCountAfterUserSurname = 8 - (int)(userSurnameSize/8);
		for (int i = 0; i < tabCountAfterUserName; i++) {
			afterNameTab += "\t";
		}
		for (int i = 0; i < tabCountAfterUserSurname; i++) {
			afterSurnameTab += "\t";
		}
	}
	
	/**
	 * Метод записывает пользователя в файл.
	 * @param user - объект класса User
	 * @param id - (код пользователя - 1)
	 * @param isUpdated - обновлять файл с максимальным ПК/не обновлять
	 * @throws IOException
	 */
	private void writeUserInFile(User user, int id, boolean isUpdated) throws IOException {
		calculateTabForUserData(user.getName(), user.getSurname());
		writer = new FileWriter (file, true); 
		String userLine = (id + 1) + "\t" + user.getName() + afterNameTab + user.getSurname() + afterSurnameTab + user.getAge() + "\t\t" + user.isActive() + "\r\n";
		writer.append(userLine); 
		writer.close();
			
		user.setId(id + 1);
		usersDataList.add(user);
			
		if (isUpdated) updateIdFile (Integer.toString(id + 1));
	}
	
	@Override
	public int addUser (User user) throws IOException{
		if (user.getId() == -1) {
			if (allUsersDeleted) {
				usersDataList.clear();
				allUsersDeleted = false;
			}
			String idLine;
			idReader = new BufferedReader (new FileReader (maxIdFile));
			idLine = idReader.readLine();
			id = Integer.parseInt(idLine);
			writeUserInFile (user, id, true);
			System.out.println("" + (id + 1));
			return (id + 1);
		} 
		else {
			writeUserInFile (user, user.getId() - 1, false);
			findMaxUserId();
			return user.getId();
		}
	}
	
	/**
	 * Метод находит максимальный ПК пользователей списка и записывает его в cоответствующий файл. Если список пуст, записывает "-1".
	 * @throws IOException
	 */
	private void findMaxUserId() throws IOException {
		if (usersDataList.size() != 0) {
			int[] indexes = new int[usersDataList.size()];
			for (int i = 0; i < usersDataList.size(); i++) {
				indexes[i] = usersDataList.get(i).getId();
			}
			
			int max = indexes[0], maxi = 0;
			for (int i = 0; i < indexes.length; i++) 
				if (indexes[i] > max) {
					max = indexes[i];
					maxi = i;
				}
			
			updateIdFile(Integer.toString(indexes[maxi]));
		} else updateIdFile ("-1");
	}
	
	/**
	 * Метод обновляет содержащиеся в файле данные, записанные до указанного пользователя.
	 * @param userId - код пользователя
	 * @throws IOException 
	 */
	private void updateTextBeforeUser(int userId) throws IOException {
		writer.close();
		reader = new BufferedReader (new FileReader (file));
		prevUsersDataList.add(reader.readLine());
		prevUsersDataList.add(reader.readLine());
		String line = reader.readLine(), idBuffer = "";
		prevUsersDataList.add(line);
		int i;
			
		while (line != null) {
			idBuffer = "";
			i = 0;
			while (i < line.length()) {
				if ((line.charAt(i) >= '0') && (line.charAt(i) <= '9')) {
					idBuffer += line.charAt(i);
				} else break;
				i++;
			}
			if (idBuffer.equals("" + userId)) break;
			line = reader.readLine();
			prevUsersDataList.add(line);
		}
		prevUsersDataList.remove(prevUsersDataList.size() - 1);
		line = reader.readLine();
		while (line != null) {
			nextUsersDataList.add(line);
			line = reader.readLine();
		}
			
		writer = new FileWriter (file, false); 
			
		for (i = 0; i < prevUsersDataList.size(); i++)
			writer.write(prevUsersDataList.get(i) + "\r\n");
	}
	
	/**
	 * Метод добавляет в файл содержавшиеся в нём до обновления/удаления пользователя данные, которые были расположены после данного пользователя.
	 * @throws IOException 
	 */
	private void updateTextAfterUser () throws IOException {
		for (int i = 0; i < nextUsersDataList.size(); i++)
			writer.write(nextUsersDataList.get(i) + "\r\n");
		writer.close();
		prevUsersDataList.clear();
		nextUsersDataList.clear();
	}
	
	@Override
	public void updateUser (User user) throws IOException {
		calculateTabForUserData(user.getName(), user.getSurname());
		updateTextBeforeUser (user.getId());
		writer.write(user.getId() + "\t" + user.getName() + afterNameTab + user.getSurname() + afterSurnameTab + user.getAge() + "\t\t" + user.isActive() + "\r\n");
		updateTextAfterUser ();
		
		int dataIndex = 0;
		for (User temp : usersDataList)
			if (temp.getId() == user.getId()) dataIndex = usersDataList.indexOf(temp);
		usersDataList.set(dataIndex, user);
	}
	
	@Override
	public void deleteUser(int id) throws IOException {
		updateTextBeforeUser (id);
		updateTextAfterUser ();
		
		int dataIndex = 0;
		for (User temp : usersDataList)
			if (temp.getId() == id) dataIndex = usersDataList.indexOf(temp);
		usersDataList.remove(dataIndex); 
		findMaxUserId();
	}
	
	@Override
	public void deleteAllUsers() throws IOException {
		writer = new FileWriter (file, false); 
		writer.write("Код:" + "\t" + "Имя:    " +  "\t\t\t\t\t\t\t" + "Фамилия:" + "\t\t\t\t\t\t\t" + "Возраст:" + "\t" + "Активен:" + "\r\n\r\n");
		reader = new BufferedReader (new FileReader (file));
		String line = reader.readLine();
		while (line != null) 
			writer.write("\r\n");
		writer.close();
		allUsersDeleted = true;
		deletedUsersDataList.clear();
		for (int i = 0; i < usersDataList.size(); i++) {
			deletedUsersDataList.add(usersDataList.get(i));
		}
		usersDataList.clear();
	}
	
	@Override
	public List <User> getUsersDataSet(boolean sorting, boolean usersIsDeleted) {
		if (usersIsDeleted) {
			if (sorting) Collections.sort(deletedUsersDataList, new UsersListSorter());
			return deletedUsersDataList;
		}
		if (sorting) Collections.sort(usersDataList, new UsersListSorter());
		return usersDataList;
	}
	
	@Override
	public void closeStorage() throws IOException {
		if (reader != null) reader.close();
		if (idReader != null) idReader.close();
		if (writer != null) writer.close();
	}
}
