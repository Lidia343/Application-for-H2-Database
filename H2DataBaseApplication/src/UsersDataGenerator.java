import java.util.ArrayList;

public class UsersDataGenerator {
	
	private ArrayList<String> femaleNames;
	private ArrayList<String> maleNames;
	private ArrayList<String> femaleSurnames;
	private ArrayList<String> maleSurnames;
	private ArrayList<String> generatedUsersData;
	private int userNumbers;
	
	public UsersDataGenerator (int userNumbers) {
		this.userNumbers = userNumbers;
		femaleNames = new ArrayList<String>();
		maleNames = new ArrayList<String>();
		femaleSurnames = new ArrayList<String>();
		maleSurnames = new ArrayList<String>();
		generatedUsersData = new ArrayList<String>();
		setUsersData();
	}
	
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
	
	public ArrayList <String> generateUsersData(){
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
		for (int i = 0; i < generatedUsersData.size(); i++) {
			if (Math.floorMod(i, 2) == 0) System.out.println ();
		}
		return generatedUsersData;
	}
}
