package application.mongoDBInterface;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.mongodb.MongoClient;
import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoDatabase;

import application.mongoDBInterface.ReferenceClass.ItemHelper;
import application.mongoDBInterface.ReferenceClass.ItemSalesHelper;
import application.mongoDBInterface.ReferenceClass.ProductGroupHelper;
import application.mongoDBInterface.ReferenceClass.VendorHelper;

public class DatabaseManager {
	
	private static final String DB_NAME = "itemCatalogue";
	private static MongoClient mongoClient;
	public static MongoDatabase database;
	public static String connection;
	
	public static void initDatabase() {
		if (database == null) {
			
			String path = System.getenv("SystemRoot") + "/ItemCatalogue/";
			
			try (BufferedReader br = new BufferedReader(new FileReader(path + "settings.txt"))) {
			    String line = br.readLine();
			    String arr[] = line.split(":");

			    while (line != null) {
			    	switch (arr[0]) {
			    		case "connection":
			    			connection = arr[1];
			    			break;
			    			
			    		//add further settings here. Specify as "Key:Value" in settings.txt
			    	}
			        line = br.readLine();
			    }
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			mongoClient = new MongoClient(connection, 27017);
			database = mongoClient.getDatabase(DB_NAME);

			try {
				database.createCollection(ItemHelper.COLLECTION_NAME);
				database.createCollection(ItemSalesHelper.COLLECTION_NAME);
				database.createCollection(ProductGroupHelper.COLLECTION_NAME);
				database.createCollection(VendorHelper.COLLECTION_NAME);
			} catch (MongoCommandException e){
			}
		}
	}
}