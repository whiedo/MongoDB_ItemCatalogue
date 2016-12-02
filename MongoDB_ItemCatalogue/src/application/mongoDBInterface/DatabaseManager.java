package application.mongoDBInterface;

import com.mongodb.MongoClient;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import application.model.Item;
import application.mongoDBInterface.ReferenceClass.ItemHelper;
import application.mongoDBInterface.ReferenceClass.Vendor;

import java.net.UnknownHostException;
import java.util.ArrayList;

import org.bson.Document;


public class DatabaseManager {
	
	private static final String DB_NAME = "itemCatalogue";
	private static MongoClient mongoClient;
	public static MongoDatabase database;
	
	public static void initDatabase() {
		if (database == null) {
			mongoClient = new MongoClient("localhost", 27017);
			database = mongoClient.getDatabase(DB_NAME);
		}
	}
	
	public static void insertItemIntoDatabase() throws UnknownHostException {
		initDatabase();
		
		Item item = new Item("12345","lol","lol2",0);
		ItemHelper.insertItem(item);
		ItemHelper.getItems();
	}
}