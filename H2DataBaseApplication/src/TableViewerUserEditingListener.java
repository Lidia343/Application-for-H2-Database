
public class TableViewerUserEditingListener {
	public void changeUserNameInStorage(User user, String name) {
		user.setName(name);
	}
	public void changeUserSurnameInStorage(User user, String surname) {
		user.setSurname(surname);
	}
	public void changeUserAgeInStorage(User user, int age) {
		user.setAge(age);
	}
	public void changeUserIsActiveInStorage(User user, boolean isActive) {
		user.setIsActive(isActive);
	}
}
