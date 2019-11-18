package temp;

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
	private List<String> generatedUsersData;
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
		generatedUsersData = new ArrayList<>();
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
	public List <String> generateUsersData(){
		for (int i = 0; i < userNumbers; i++) {
			int isMale = (int)Math.round(Math.random());
			if (isMale == 0){
				generatedUsersData.add(maleNames.get((int)Math.round(Math.random()*(maleNames.size() - 1))));
				generatedUsersData.add (maleSurnames.get((int)Math.round(Math.random()*(maleNames.size() - 1))));
			} else {
				generatedUsersData.add(femaleNames.get((int)Math.round(Math.random()*(femaleNames.size() - 1))));
				generatedUsersData.add (femaleSurnames.get((int)Math.round(Math.random()*(femaleNames.size() - 1))));
			}
			generatedUsersData.add(Integer.toString(18 + (int)Math.round(Math.random()*(182))));
			int activity = (int)Math.round(Math.random());
			if (activity == 0) generatedUsersData.add(Boolean.toString(true)); else
			generatedUsersData.add(Boolean.toString(false));
		}
		return generatedUsersData;
	}
}
