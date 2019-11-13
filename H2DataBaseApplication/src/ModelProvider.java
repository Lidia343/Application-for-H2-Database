import java.util.List;

public class ModelProvider {
	
	private Storage storage;
	
	public ModelProvider(Storage storage) {
		this.storage = storage;
	}
	
	public List<User> getUsersData() throws Exception {
		return storage.getUsersDataSet(false, false);
	}
}
