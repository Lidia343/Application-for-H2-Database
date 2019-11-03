import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Класс предназначен для работы с файлом, в котором должна храниться информация о пользователях.
 */
public class FileStorage implements Storage {
	
	private String fileName;
	private File file;
	private File maxIdFile; //Файл для записи максимального значения первичного ключа 
	
	private String errorMessage;
	private int id;
	
	private FileWriter writer;
	private FileWriter idWriter;
	private BufferedReader reader;
	private BufferedReader idReader;

	private ArrayList<User> usersDataList; //Список для всех пользователей, занесённых в файл
	private ArrayList<String> prevUsersDataList; //Список для пользователей, занесённых в файл и расположенных до выбранного для удаления/изменения пользователя
	private ArrayList<String> nextUsersDataList; //Список для пользователей, занесённых в файл и расположенных после выбранного для удаления/изменения пользователя
	
	private String afterNameTab; //Строка, содержащая необходимый отступ после имени пользователя для текущей строки в файле
	private String afterSurnameTab; //Строка, содержащая необходимый отступ после фамилии пользователя для текущей строки в файле
	
	/**
	 * Конструктор класса FileStorage.
	 */
	public FileStorage(String fileName) {
		this.fileName = fileName;
		errorMessage = "";
		id = 0;
		usersDataList = new ArrayList<User>();
		prevUsersDataList = new ArrayList<String>();
		nextUsersDataList = new ArrayList<String>();
	}
	
	@Override
	public void setStorage() {
		//this.fileName = "file.txt";
		file = new File (fileName);
	}
	
	/*
	/**
	 * Установить/сбросить атрибут, отвечающий за скрытие файла.
	 */
	/*private void setHidden(String fileName, boolean isHidden) {
		try {
			idWriter = new FileWriter (fileName, false);
			if (isHidden)
			Runtime.getRuntime().exec("attrib +H " + fileName); else
				Runtime.getRuntime().exec("attrib -H " + fileName);
			idWriter.close();
			errorMessage  = "";
		} catch (Exception e) {
			errorMessage = e.getMessage();
		}
	}*/
	
	private void updateIdFile (String text) {
		try {
			idWriter = new FileWriter (maxIdFile, false);
			idWriter.write(text);
			idWriter.close();
			errorMessage = "";
		} catch (IOException e) {
			errorMessage = e.getMessage(); 
		}
	}
	
	/**
	 * Метод записывает значение "-1" в файл, где хранится максимальный ПК.
	 */
	private void updateIdFile() {
		updateIdFile("-1");
	}
	
	/**
	 * Метод записывает из файла в список данные всех пользователей.
	 */
	private void writeInList(File file) {
		
		try {
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
						if (buffer.equals("true") || buffer.equals("false")) {
							user.setIsActive(Boolean.parseBoolean(buffer));
						}
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
			errorMessage = "";
		} catch (Exception e) {
			errorMessage = e.getMessage();
		}
		
	}
	
	@Override
	public void createStorageObject() {
		
		//file = new File (fileName);
		maxIdFile = new File ("maxId.txt");
		//setHidden("maxId.txt", false);
		try {
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
			errorMessage = "";
		} catch (IOException e) {
			errorMessage = e.getMessage();
		}
	}
	
	@Override
	public void updateStorageObject(){ 
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
	
	private void writeUserInFile(User user, int id, boolean isUpdated) {
		try {
			calculateTabForUserData(user.getName(), user.getSurname());
			writer = new FileWriter (file, true); 
			String userLine = (id + 1) + "\t" + user.getName() + afterNameTab + user.getSurname() + afterSurnameTab + user.getAge() + "\t\t" + user.getIsActive() + "\r\n";
			writer.append(userLine); 
			writer.close();
			
			user.setId(id + 1);
			usersDataList.add(user);
			
			if (isUpdated) updateIdFile (Integer.toString(id + 1));
		} catch (Exception e) {
			errorMessage = e.getMessage();
		}
	}
	
	@Override
	public void addUser (User user) {
		try {
			String idLine;
			idReader = new BufferedReader (new FileReader (maxIdFile));
			idLine = idReader.readLine();
			id = Integer.parseInt(idLine);
			writeUserInFile (user, id, true);
		} catch (Exception e) {
			errorMessage = e.getMessage();
		}
	}
	
	private void findMaxUserId() {
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
	
	@Override
	public void addUser (User user, int deletedId) {
		try {
			writeUserInFile (user, deletedId - 1, false);
			findMaxUserId();
		} catch (Exception e) {
			errorMessage = e.getMessage();
		}
	}
	
	/**
	 * Метод обновляет содержащиеся в файле данные, записанные до указанного пользователя.
	 * @param userId - код пользователя
	 */
	private void updateTextBeforeUser(int userId) {
		try {
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
			
			errorMessage = "";
		} catch (Exception e) {
			errorMessage = e.getMessage();
		}
	}
	
	/**
	 * Метод добавляет в файл содержавшиеся в нём до обновления/удаления пользователя данные, которые были расположены после данного пользователя.
	 */
	private void updateTextAfterUser () {
		
		try {
			for (int i = 0; i < nextUsersDataList.size(); i++)
				writer.write(nextUsersDataList.get(i) + "\r\n");
			writer.close();
			
			prevUsersDataList.clear();
			nextUsersDataList.clear();
			errorMessage = "";
		} catch (Exception e) {
			errorMessage = e.getMessage();
		}
	}
	
	@Override
	public void updateUser (User user) {
		try {
			
			calculateTabForUserData(user.getName(), user.getSurname());
			
			updateTextBeforeUser (user.getId());
			writer.write(user.getId() + "\t" + user.getName() + afterNameTab + user.getSurname() + afterSurnameTab + user.getAge() + "\t\t" + user.getIsActive() + "\r\n");
			updateTextAfterUser ();
			
			int dataIndex = 0;
			for (User temp : usersDataList)
				if (temp.getId() == user.getId()) dataIndex = usersDataList.indexOf(temp);
			usersDataList.set(dataIndex, user);
			errorMessage = "";
		} catch (Exception e) {
			errorMessage = e.getMessage();
		}
	}
	
	@Override
	public void deleteUser(int id) {
		
		updateTextBeforeUser (id);
		updateTextAfterUser ();

		int dataIndex = 0;
		for (User temp : usersDataList)
			if (temp.getId() == id) dataIndex = usersDataList.indexOf(temp);
		usersDataList.remove(dataIndex); 

		findMaxUserId();
		//if (usersDataList.size() != 0)
		/*try {//////////////////////////////////////////////////////////////////
			/*reader = new BufferedReader (new FileReader (file));
			reader.readLine();
			reader.readLine();
			String line = reader.readLine(), idBuffer = "";
			int i;
			
			int[] indexes = new int[usersDataList.size()/5];
			
			while (line != null) {
				idBuffer = "";
				i = 0;
				while (i < line.length()) {
					if ((line.charAt(i) >= '0') && (line.charAt(i) <= '9')) {
						idBuffer += line.charAt(i);
					} else break;
					i++;
				}
				indexes[i] = Integer.parseInt(idBuffer);
				line = reader.readLine();
			}
			
			System.out.println();
			for (int j = 0; j < indexes.length; j++) {
				System.out.print(Integer.toString(indexes[j]));
			}
			System.out.println();
			
			int max = indexes[0];
			int maxi = 0;
			for (i = 1; i < indexes.length; i++)
				if (indexes[i] > max) {
					max = indexes[i];
					maxi = i;
				}
			
			updateIdFile(Integer.toString(indexes[maxi]));
			
		} catch (Exception e) {
			errorMessage = e.getMessage();
		}*//////////////////////////////////////////////////////////
		
		errorMessage = "";
	}
	
	@Override
	public ArrayList <User> getUsersDataSet(boolean isSorted) {
		
		if (isSorted) Collections.sort(usersDataList, new UsersListSorter());
		return usersDataList;
	}
	
	@Override
	public String getErrorMessage() {
		return errorMessage;
	}
	
	@Override
	public void closeStorage() {
		try {
			//setHidden("maxId.txt", true);
			reader.close();
			idReader.close();
			writer.close();
			errorMessage = "";
		} catch (Exception e) {
			errorMessage = e.getMessage();
		}
	}
}
