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
	////////////////////////////////////////////////////////Возможно, поменять список
	private ArrayList<String> usersDataList; //Список для всех пользователей, занесённых в файл
	private ArrayList<String> prevUsersDataList; //Список для пользователей, занесённых в файл и расположенных до выбранного для удаления/изменения пользователя
	private ArrayList<String> nextUsersDataList; //Список для пользователей, занесённых в файл и расположенных после выбранного для удаления/изменения пользователя
	
	private String afterNameTab; //Строка, содержащая необходимый отступ после имени пользователя для текущей строки в файле
	private String afterSurnameTab; //Строка, содержащая необходимый отступ после фамилии пользователя для текущей строки в файле
	
	/**
	 * Конструктор класса FileStorage.
	 */
	public FileStorage() {
		errorMessage = "";
		id = 0;
		usersDataList = new ArrayList<String>();
		prevUsersDataList = new ArrayList<String>();
		nextUsersDataList = new ArrayList<String>();
	}
	
	@Override
	public void setStorage() {
		this.fileName = "file.txt";
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
			reader = new BufferedReader (new FileReader (file));
			reader.readLine();
			reader.readLine();
			String line = reader.readLine(), buffer;
			int i;
			while (line != null) {
				buffer = "";
				i = 0;
				while (i < line.length()) {
					if (((line.charAt(i) >= '0') && (line.charAt(i) <= '9')) || 
							((line.charAt(i) >= 'a') && (line.charAt(i) <= 'z')) || 
							((line.charAt(i) >= 'A') && (line.charAt(i) <= 'Z')) ||
							((line.charAt(i) >= 'а') && (line.charAt(i) <= 'я')) ||
							((line.charAt(i) >= 'А') && (line.charAt(i) <= 'Я'))) {
						buffer += line.charAt(i); 
						if (buffer.equals("true") || buffer.equals("false")) usersDataList.add(buffer);
					} else {
						if (!buffer.equals("")) usersDataList.add(buffer);
						buffer = "";  
					}
					i++;
				}
				line = reader.readLine();
			}
			
			errorMessage = "";
		} catch (Exception e) {
			errorMessage = e.getMessage();
		}
		
	}
	
	@Override
	public void createStorageObject() {
		
		file = new File (fileName);
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
			
			usersDataList.add(Integer.toString(id + 1));
			usersDataList.add(user.getName());
			usersDataList.add(user.getSurname());
			usersDataList.add(Integer.toString(user.getAge()));
			usersDataList.add(Boolean.toString(user.getIsActive()));
			
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
	
	@Override
	public void addUser (User user, int deletedId) {
		try {
			writeUserInFile (user, deletedId - 1, false);
			if (usersDataList.size() != 0) {
				
				int[] indexes = new int[usersDataList.size()/5];
				int t = 0;
				for (int i = 0; i < usersDataList.size() - 4; i+=5) {
					indexes[t] = Integer.parseInt(usersDataList.get(i));
					t++;
				}
				
				int max = indexes[0], maxi = 0;
				for (int i = 0; i < indexes.length; i++) 
					if (indexes[i] > max) {
						max = indexes[i];
						maxi = i;
					}
				
				updateIdFile(Integer.toString(indexes[maxi]));
			}
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
			
			int dataIndex = usersDataList.indexOf(Integer.toString(user.getId()));
			usersDataList.set(dataIndex + 1, user.getName());
			usersDataList.set(dataIndex + 2, user.getSurname());
			usersDataList.set(dataIndex + 3, Integer.toString(user.getAge()));
			usersDataList.set(dataIndex + 4, Boolean.toString(user.getIsActive()));
		
			errorMessage = "";
		} catch (Exception e) {
			errorMessage = e.getMessage();
		}
	}
	
	@Override
	public void deleteUser(int id) {
		
		updateTextBeforeUser (id);
		updateTextAfterUser ();

		int dataIndex = usersDataList.indexOf(Integer.toString(id)); 
		usersDataList.remove(dataIndex + 4); 
		usersDataList.remove(dataIndex + 3);
		usersDataList.remove(dataIndex + 2);
		usersDataList.remove(dataIndex + 1);
		usersDataList.remove(dataIndex);
		
		if (usersDataList.size() != 0) {
		
			int[] indexes = new int[usersDataList.size()/5];
			int t = 0;
			for (int i = 0; i < usersDataList.size() - 4; i+=5) {
				indexes[t] = Integer.parseInt(usersDataList.get(i));
				t++;
			}
			
			int max = indexes[0], maxi = 0;
			for (int i = 0; i < indexes.length; i++) 
				if (indexes[i] > max) {
					max = indexes[i];
					maxi = i;
				}
			
			updateIdFile(Integer.toString(indexes[maxi]));
		}
		
		
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
	public User getUsersDataSet(boolean isSorted) {
		User users = new User();
		int id, age;
		String name, surname;
		boolean isActive;
		try {
			for (int i = 0; i < usersDataList.size() - 4; i += 5) {
				id = Integer.parseInt(usersDataList.get(i));
				name = usersDataList.get(i + 1);
				surname = usersDataList.get(i + 2);
				age = Integer.parseInt(usersDataList.get(i + 3));
				isActive = Boolean.parseBoolean(usersDataList.get(i + 4));
				User user = new User();
				user.setId(id);
				user.setName(name);
				user.setSurname(surname);
				user.setAge(age);
				user.setIsActive(isActive);
				users.addUser(user);
			}
			if (isSorted) Collections.sort(users.getUsersList(), new UsersListSorter());
			errorMessage = "";
		} catch (Exception e) {
			errorMessage = e.getMessage();
		}
		return users;
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
