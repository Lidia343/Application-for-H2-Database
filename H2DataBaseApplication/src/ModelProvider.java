import java.util.List;

public class ModelProvider {
	
	private Storage storage;
	private List<User> usersData;
	
	public ModelProvider(Storage storage) throws Exception {
		this.storage = storage;
		usersData = storage.getUsersDataSet(false, false);
	}
	
	public List<User> getUsersData(){
		return usersData;
	}
	
	
}
