package user;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс предназначен для генерации данных указанного количества пользователей.
 */
public class UsersDataGenerator 
{
	private List<String> m_femaleNames;
	private List<String> m_maleNames;
	private List<String> m_femaleSurnames;
	private List<String> m_maleSurnames;
	private List<User> m_generatedUsers;
	private int m_userNumbers;
	
	/**
	 * Конструктор класса UsersDataGenerator.
	 * @param a_userNumbers - количество пользователей для генерации
	 */
	public UsersDataGenerator (int a_userNumbers) 
	{
		m_userNumbers = a_userNumbers;
		m_femaleNames = new ArrayList<>();
		m_maleNames = new ArrayList<>();
		m_femaleSurnames = new ArrayList<>();
		m_maleSurnames = new ArrayList<>();
		m_generatedUsers = new ArrayList<>();
		setUsersData();
	}
	
	/**
	 * Метод устанавливает возможные имена и фамилии пользователей в списки для их хранения.
	 */
	private void setUsersData () 
	{
		m_femaleNames.add("Екатерина"); m_femaleNames.add("Наталья"); 
		m_femaleNames.add("Валентина"); m_femaleNames.add("Антонина");
		m_femaleNames.add("Оксана"); m_femaleNames.add("Анастасия");  
		m_femaleNames.add("Ксения"); m_femaleNames.add("Анна");
		m_maleNames.add("Алексей"); m_maleNames.add("Валерий"); 
		m_maleNames.add("Павел"); m_maleNames.add("Максим"); 
		m_maleNames.add("Антон"); m_maleNames.add("Виталий"); 
		m_maleNames.add("Андрей"); m_maleNames.add("Илья");
		m_femaleSurnames.add("Ильина"); m_femaleSurnames.add("Осипова"); 
		m_femaleSurnames.add("Волкова"); m_femaleSurnames.add("Калинина");
		m_femaleSurnames.add("Орлова"); m_femaleSurnames.add("Лисина"); 
		m_femaleSurnames.add("Орлова"); m_femaleSurnames.add("Лисина"); //
		m_maleSurnames.add("Преображенский"); m_maleSurnames.add("Котов"); 
		m_maleSurnames.add("Васильев"); m_maleSurnames.add("Павлов"); 
		m_maleSurnames.add("Петров"); m_maleSurnames.add("Морозов");	
		m_maleSurnames.add("Фролов"); m_maleSurnames.add("Дмитриев");	
	}
	
	/**
	 * Метод возвращает список сгенерированных пользовательских данных (каждый элемент списка - отдельное свойство пользователя).
	 */
	public List <User> generateUsers()
	{
		User user;
		for (int i = 0; i < m_userNumbers; i++) 
		{
			user = new User();
			int isMale = (int)Math.round(Math.random());
			if (isMale == 0)
			{
				user.setName(m_maleNames.get((int)Math.round(Math.random()*(m_maleNames.size() - 1))));
				user.setSurname(m_maleSurnames.get((int)Math.round(Math.random()*(m_maleSurnames.size() - 1))));
			} else 
				{
					user.setName(m_femaleNames.get((int)Math.round(Math.random()*(m_femaleNames.size() - 1))));
					user.setSurname(m_femaleSurnames.get((int)Math.round(Math.random()*(m_femaleNames.size() - 1))));
				}
			user.setAge(UserData.MIN_AGE + (int)Math.round(Math.random()*(UserData.MAX_AGE - UserData.MIN_AGE)));
			int activity = (int)Math.round(Math.random());
			if (activity == 0) user.setIsActive(true); else
				user.setIsActive(false);
			m_generatedUsers.add(user);
		}
		return m_generatedUsers;
	}
}