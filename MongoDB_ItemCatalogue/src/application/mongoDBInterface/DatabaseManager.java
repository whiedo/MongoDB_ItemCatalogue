package application.mongoDBInterface;

import com.mongodb.MongoClient;
import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoDatabase;

import application.mongoDBInterface.ReferenceClass.ItemHelper;
import application.mongoDBInterface.ReferenceClass.ProductGroupHelper;
import application.mongoDBInterface.ReferenceClass.VendorHelper;

public class DatabaseManager {
	
	private static final String DB_NAME = "itemCatalogue";
	private static MongoClient mongoClient;
	public static MongoDatabase database;
	
	public static void initDatabase() {
		if (database == null) {
			mongoClient = new MongoClient("localhost", 27017);
			database = mongoClient.getDatabase(DB_NAME);
			
			try {
				database.createCollection(ItemHelper.COLLECTION_NAME);
				database.createCollection(ProductGroupHelper.COLLECTION_NAME);
				database.createCollection(VendorHelper.COLLECTION_NAME);
			} catch (MongoCommandException e){
			}
		}
	}
}