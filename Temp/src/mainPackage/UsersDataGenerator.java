package mainPackage;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс предназначен для генерации данных указанного количества пользователей.
 */
public class UsersDataGenerator {
	
	private List<String> femaleNames;
	private List<String> maleNames;
	private List<String> femaleSurnames;
	private List<String> maleSurnames;
	private List<User> generatedUsers;
	private int userNumbers;
	
	/**
	 * Конструктор класса UsersDataGenerator.
	 * @param userNumbers - количество пользователей для генерации
	 */
	public UsersDataGenerator (int userNumbers) {
		this.userNumbers = userNumbers;
		femaleNames = new ArrayList<>();
		maleNames = new ArrayList<>();
		femaleSurnames = new ArrayList<>();
		maleSurnames = new ArrayList<>();
		generatedUsers = new ArrayList<>();
		setUsersData();
	}
	
	/**
	 * Метод устанавливает возможные имена и фамилии пользователей в списки для их хранения.
	 */
	private void setUsersData () {
		femaleNames.add("Екатерина"); femaleNames.add("Наталья"); 
		femaleNames.add("Валентина"); femaleNames.add("Антонина");
		femaleNames.add("Оксана"); femaleNames.add("Анастасия");  
		femaleNames.add("Ксения"); femaleNames.add("Анна");
		maleNames.add("Алексей"); maleNames.add("Валерий"); 
		maleNames.add("Павел"); maleNames.add("Максим"); 
		maleNames.add("Антон"); maleNames.add("Виталий"); 
		maleNames.add("Андрей"); maleNames.add("Илья");
		femaleSurnames.add("Ильина"); femaleSurnames.add("Осипова"); 
		femaleSurnames.add("Волкова"); femaleSurnames.add("Калинина");
		femaleSurnames.add("Орлова"); femaleSurnames.add("Лисина"); 
		femaleSurnames.add("Орлова"); femaleSurnames.add("Лисина"); //
		maleSurnames.add("Преображенский"); maleSurnames.add("Котов"); 
		maleSurnames.add("Васильев"); maleSurnames.add("Павлов"); 
		maleSurnames.add("Петров"); maleSurnames.add("Морозов");	
		maleSurnames.add("Фролов"); maleSurnames.add("Дмитриев");	
	}
	
	/**
	 * Метод возвращает список сгенерированных пользовательских данных (каждый элемент списка - отдельное свойство пользователя).
	 */
	public List <User> generateUsers() {
		User user;
		for (int i = 0; i < userNumbers; i++) {
			user = new User();
			int isMale = (int)Math.round(Math.random());
			if (isMale == 0){
				user.setName(maleNames.get((int)Math.round(Math.random()*(maleNames.size() - 1))));
				user.setSurname(maleSurnames.get((int)Math.round(Math.random()*(maleSurnames.size() - 1))));
			} else {
				user.setName(femaleNames.get((int)Math.round(Math.random()*(femaleNames.size() - 1))));
				user.setSurname(femaleSurnames.get((int)Math.round(Math.random()*(femaleNames.size() - 1))));
			}
			user.setAge(18 + (int)Math.round(Math.random()*(182)));
			int activity = (int)Math.round(Math.random());
			if (activity == 0) user.setIsActive(true); else
				user.setIsActive(false);
			generatedUsers.add(user);
		}
		return generatedUsers;
	}
}