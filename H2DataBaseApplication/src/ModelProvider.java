import java.util.List;

public class ModelProvider {

	private List<User> usersData;
	
	public ModelProvider(Storage storage) throws Exception {
		usersData = storage.getUsersDataSet(false, false);
	}
	
	public List<User> getUsersData(){
		return usersData;
	}
}
