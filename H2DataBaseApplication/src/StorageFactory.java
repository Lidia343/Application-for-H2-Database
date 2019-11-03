
public class StorageFactory {
	
	public Storage getStorage(String storageName) {
		Storage storage = null;
		if (storageName.startsWith("jdbc:h2:")) storage = new DataBase(storageName); else 
			if (storageName.endsWith(".txt")) storage = new FileStorage(storageName);
		return storage;
	}
}
